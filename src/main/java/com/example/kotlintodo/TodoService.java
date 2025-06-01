package com.example.kotlintodo;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service(Service.Level.PROJECT)
public final class TodoService {
    private final Map<String, List<TodoItem>> todoCache = new ConcurrentHashMap<>();
    private final Set<TodoServiceListener> listeners = ConcurrentHashMap.newKeySet();
    private final Project project;

    public TodoService(Project project) {
        this.project = project;
    }

    public static TodoService getInstance(Project project) {
        return project.getService(TodoService.class);
    }

    public void addListener(TodoServiceListener listener) {
        listeners.add(listener);
    }

    public void removeListener(TodoServiceListener listener) {
        listeners.remove(listener);
    }

    public void updateTodos(VirtualFile file) {
        ApplicationManager.getApplication().runReadAction(() -> {
            List<TodoItem> todos = TodoParser.parseTodos(file);
            String filePath = file.getPath();
            
            if (todos.isEmpty()) {
                todoCache.remove(filePath);
            } else {
                todoCache.put(filePath, todos);
            }
            
            notifyListeners();
        });
    }

    public void removeTodos(VirtualFile file) {
        todoCache.remove(file.getPath());
        notifyListeners();
    }

    public List<TodoItem> getAllTodos() {
        List<TodoItem> allTodos = new ArrayList<>();
        for (List<TodoItem> fileTodos : todoCache.values()) {
            allTodos.addAll(fileTodos);
        }
        return allTodos;
    }

    public List<TodoItem> getTodosForFile(VirtualFile file) {
        return todoCache.getOrDefault(file.getPath(), new ArrayList<>());
    }

    public void clearCache() {
        todoCache.clear();
        notifyListeners();
    }

    private void notifyListeners() {
        for (TodoServiceListener listener : listeners) {
            listener.todosUpdated();
        }
    }

    public interface TodoServiceListener {
        void todosUpdated();
    }
}
