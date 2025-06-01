package com.example.kotlintodo;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.NotNull;

public class TodoStartupActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        TodoService todoService = TodoService.getInstance(project);
        
        // Scan all Kotlin files in the project on startup
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
