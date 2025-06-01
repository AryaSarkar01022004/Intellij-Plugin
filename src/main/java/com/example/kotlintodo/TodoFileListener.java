package com.example.kotlintodo;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.project.ProjectManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileEvent;
import com.intellij.openapi.vfs.VirtualFileListener;
import org.jetbrains.annotations.NotNull;

public class TodoFileListener implements VirtualFileListener {
    @Override
    public void contentsChanged(@NotNull VirtualFileEvent event) {
        VirtualFile file = event.getFile();
        if (isKotlinFile(file)) {
            updateTodosForFile(file);
        }
    }

    @Override
    public void fileDeleted(@NotNull VirtualFileEvent event) {
        VirtualFile file = event.getFile();
        if (isKotlinFile(file)) {
            removeTodosForFile(file);
        }
    }

    @Override
    public void fileCreated(@NotNull VirtualFileEvent event) {
        VirtualFile file = event.getFile();
        if (isKotlinFile(file)) {
            updateTodosForFile(file);
        }
    }

    private boolean isKotlinFile(VirtualFile file) {
        return file != null && (file.getName().endsWith(".kt") || file.getName().endsWith(".kts"));
    }

    private void updateTodosForFile(VirtualFile file) {
        for (Project project : ProjectManager.getInstance().getOpenProjects()) {
            TodoService todoService = TodoService.getInstance(project);
            todoService.updateTodos(file);
        }
    }

    private void removeTodosForFile(VirtualFile file) {
        for (Project project : ProjectManager.getInstance().getOpenProjects()) {
            TodoService todoService = TodoService.getInstance(project);
            todoService.removeTodos(file);
        }
    }
}
