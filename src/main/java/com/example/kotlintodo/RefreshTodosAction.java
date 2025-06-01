package com.example.kotlintodo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class RefreshTodosAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        TodoService todoService = TodoService.getInstance(project);
        todoService.clearCache();

        // Re-scan the project
        VirtualFile projectDir = project.getBaseDir();
        if (projectDir != null) {
            scanDirectoryForTodos(projectDir, todoService);
        }
    }

    private void scanDirectoryForTodos(VirtualFile directory, TodoService todoService) {
        if (directory == null || !directory.isDirectory()) {
            return;
        }

        for (VirtualFile child : directory.getChildren()) {
            if (child.isDirectory()) {
                scanDirectoryForTodos(child, todoService);
            } else if (child.getName().endsWith(".kt") || child.getName().endsWith(".kts")) {
                todoService.updateTodos(child);
            }
        }
    }
}
