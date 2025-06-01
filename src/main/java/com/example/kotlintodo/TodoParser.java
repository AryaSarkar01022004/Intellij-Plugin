package com.example.kotlintodo;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TodoParser {
    // Regex patterns for different TODO comment styles
    private static final Pattern LINE_COMMENT_TODO = Pattern.compile("//\\s*TODO:?\\s*(.*)");
    private static final Pattern BLOCK_COMMENT_TODO = Pattern.compile("/\\*\\s*TODO:?\\s*(.*?)\\s*\\*/");
    private static final Pattern MULTILINE_BLOCK_TODO = Pattern.compile("/\\*[\\s\\S]*?TODO:?\\s*(.*?)[\\s\\S]*?\\*/");

    public static List<TodoItem> parseTodos(VirtualFile file) {
        List<TodoItem> todos = new ArrayList<>();
        
        if (file == null || !file.isValid() || !isKotlinFile(file)) {
            return todos;
        }

        try {
            Document document = FileDocumentManager.getInstance().getDocument(file);
            if (document == null) {
                return todos;
            }

            String content = document.getText();
            String[] lines = content.split("\n");

            // Parse line by line for better accuracy
            for (int i = 0; i < lines.length; i++) {
                String line = lines[i];
                List<TodoItem> lineTodos = parseLineForTodos(line, i, file, document);
                todos.addAll(lineTodos);
            }

            // Also parse for block comments that span multiple lines
            todos.addAll(parseBlockCommentTodos(content, file, document));

        } catch (Exception e) {
            // Log error but don't crash
            System.err.println("Error parsing TODOs in file: " + file.getPath() + " - " + e.getMessage());
        }

        return todos;
    }

    private static List<TodoItem> parseLineForTodos(String line, int lineNumber, VirtualFile file, Document document) {
        List<TodoItem> todos = new ArrayList<>();
        
        // Check for line comments (// TODO)
        Matcher lineMatcher = LINE_COMMENT_TODO.matcher(line);
        if (lineMatcher.find()) {
            String todoText = lineMatcher.group(1);
            if (!todoText.trim().isEmpty()) {
                int startOffset = document.getLineStartOffset(lineNumber) + lineMatcher.start();
                int endOffset = document.getLineStartOffset(lineNumber) + lineMatcher.end();
                todos.add(new TodoItem(todoText, lineNumber, startOffset, endOffset, file));
            }
        }

        // Check for single-line block comments (/* TODO */)
        Matcher blockMatcher = BLOCK_COMMENT_TODO.matcher(line);
        while (blockMatcher.find()) {
            String todoText = blockMatcher.group(1);
            if (!todoText.trim().isEmpty()) {
                int startOffset = document.getLineStartOffset(lineNumber) + blockMatcher.start();
                int endOffset = document.getLineStartOffset(lineNumber) + blockMatcher.end();
                todos.add(new TodoItem(todoText, lineNumber, startOffset, endOffset, file));
            }
        }

        return todos;
    }

    private static List<TodoItem> parseBlockCommentTodos(String content, VirtualFile file, Document document) {
        List<TodoItem> todos = new ArrayList<>();
        
        Matcher matcher = MULTILINE_BLOCK_TODO.matcher(content);
        while (matcher.find()) {
            String todoText = matcher.group(1);
            if (!todoText.trim().isEmpty()) {
                int startOffset = matcher.start();
                int endOffset = matcher.end();
                int lineNumber = document.getLineNumber(startOffset);
                todos.add(new TodoItem(todoText, lineNumber, startOffset, endOffset, file));
            }
        }

        return todos;
    }

    private static boolean isKotlinFile(VirtualFile file) {
        return file.getName().endsWith(".kt") || file.getName().endsWith(".kts");
    }

    public static boolean containsTodoKeyword(TodoItem todo, String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return true;
        }
        return todo.getText().toLowerCase().contains(keyword.toLowerCase());
    }
}
