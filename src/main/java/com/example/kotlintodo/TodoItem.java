package com.example.kotlintodo;

import com.intellij.openapi.vfs.VirtualFile;

public class TodoItem {
    private final String text;
    private final int lineNumber;
    private final int startOffset;
    private final int endOffset;
    private final VirtualFile file;
    private final String filePath;

    public TodoItem(String text, int lineNumber, int startOffset, int endOffset, VirtualFile file) {
        this.text = text.trim();
        this.lineNumber = lineNumber;
        this.startOffset = startOffset;
        this.endOffset = endOffset;
        this.file = file;
        this.filePath = file != null ? file.getPath() : "";
    }

    public String getText() {
        return text;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public int getStartOffset() {
        return startOffset;
    }

    public int getEndOffset() {
        return endOffset;
    }

    public VirtualFile getFile() {
        return file;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getDisplayText() {
        String fileName = file != null ? file.getName() : "Unknown";
        return String.format("[%s:%d] %s", fileName, lineNumber + 1, text);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        TodoItem todoItem = (TodoItem) obj;
        return lineNumber == todoItem.lineNumber &&
               startOffset == todoItem.startOffset &&
               text.equals(todoItem.text) &&
               filePath.equals(todoItem.filePath);
    }

    @Override
    public int hashCode() {
        return text.hashCode() + lineNumber + startOffset + filePath.hashCode();
    }
}
