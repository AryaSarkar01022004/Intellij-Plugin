package com.example.kotlintodo;

import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.LogicalPosition;
import com.intellij.openapi.editor.ScrollType;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.fileEditor.OpenFileDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.components.JBList;
import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextField;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class TodoToolWindow extends SimpleToolWindowPanel implements TodoService.TodoServiceListener {
    private final Project project;
    private final TodoService todoService;
    private final DefaultListModel<TodoItem> listModel;
    private final JBList<TodoItem> todoList;
    private final JBTextField filterField;
    private List<TodoItem> allTodos;

    public TodoToolWindow(Project project) {
        super(true, true);
        this.project = project;
        this.todoService = TodoService.getInstance(project);
        this.listModel = new DefaultListModel<>();
        this.todoList = new JBList<>(listModel);
        this.filterField = new JBTextField();
        this.allTodos = todoService.getAllTodos();

        initializeUI();
        setupListeners();
        refreshTodoList();
        
        todoService.addListener(this);
    }

    private void initializeUI() {
        // Create filter panel
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.add(new JLabel("Filter: "), BorderLayout.WEST);
        filterPanel.add(filterField, BorderLayout.CENTER);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Configure todo list
        todoList.setCellRenderer(new TodoListCellRenderer());
        todoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(new JBScrollPane(todoList), BorderLayout.CENTER);

        setContent(mainPanel);
    }

    private void setupListeners() {
        // Filter field listener
        filterField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { applyFilter(); }
            @Override
            public void removeUpdate(DocumentEvent e) { applyFilter(); }
            @Override
            public void changedUpdate(DocumentEvent e) { applyFilter(); }
        });

        // List click listener
        todoList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    navigateToSelectedTodo();
                }
            }
        });

        // Enter key listener
        todoList.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    navigateToSelectedTodo();
                }
            }
        });
    }

    private void applyFilter() {
        String filterText = filterField.getText();
        List<TodoItem> filteredTodos = allTodos.stream()
                .filter(todo -> TodoParser.containsTodoKeyword(todo, filterText))
                .collect(Collectors.toList());
        
        updateListModel(filteredTodos);
    }

    private void updateListModel(List<TodoItem> todos) {
        listModel.clear();
        for (TodoItem todo : todos) {
            listModel.addElement(todo);
        }
    }

    private void navigateToSelectedTodo() {
        TodoItem selectedTodo = todoList.getSelectedValue();
        if (selectedTodo != null && selectedTodo.getFile() != null) {
            navigateToTodo(selectedTodo);
        }
    }

    private void navigateToTodo(TodoItem todo) {
        try {
            OpenFileDescriptor descriptor = new OpenFileDescriptor(
                project, 
                todo.getFile(), 
                todo.getStartOffset()
            );
            
            Editor editor = FileEditorManager.getInstance(project).openTextEditor(descriptor, true);
            if (editor != null) {
                LogicalPosition position = new LogicalPosition(todo.getLineNumber(), 0);
                editor.getCaretModel().moveToLogicalPosition(position);
                editor.getScrollingModel().scrollToCaret(ScrollType.CENTER);
                
                // Highlight the line briefly
                highlightTodoInEditor(editor, todo);
            }
        } catch (Exception e) {
            System.err.println("Error navigating to TODO: " + e.getMessage());
        }
    }

    private void highlightTodoInEditor(Editor editor, TodoItem todo) {
        // This could be enhanced with temporary highlighting
        // For now, just ensure the caret is positioned correctly
        editor.getCaretModel().moveToOffset(todo.getStartOffset());
    }

    public void refreshTodoList() {
        allTodos = todoService.getAllTodos();
        applyFilter();
    }

    @Override
    public void todosUpdated() {
        SwingUtilities.invokeLater(this::refreshTodoList);
    }

    private static class TodoListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                    boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof TodoItem) {
                TodoItem todo = (TodoItem) value;
                setText(todo.getDisplayText());
                setToolTipText(todo.getFilePath() + ":" + (todo.getLineNumber() + 1));
            }
            
            return this;
        }
    }
}
