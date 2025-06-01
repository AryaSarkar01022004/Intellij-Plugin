package com.example.kotlintodo;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.colors.EditorColorsManager;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.List;

public class TodoHighlightAnnotator implements Annotator {
    private static final TextAttributesKey TODO_HIGHLIGHT = TextAttributesKey.createTextAttributesKey(
        "TODO_HIGHLIGHT",
        new TextAttributes(null, new Color(255, 255, 0, 50), null, null, Font.PLAIN)
    );

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        PsiFile file = element.getContainingFile();
        if (file == null || !file.getName().endsWith(".kt")) {
            return;
        }

        // Only annotate at file level to avoid redundant processing
        if (element != file) {
            return;
        }

        try {
            List<TodoItem> todos = TodoParser.parseTodos(file.getVirtualFile());
            
            for (TodoItem todo : todos) {
                if (todo.getStartOffset() >= 0 && todo.getEndOffset() <= file.getTextLength()) {
                    TextRange range = new TextRange(todo.getStartOffset(), todo.getEndOffset());
                    
                    holder.newAnnotation(HighlightSeverity.INFORMATION, "TODO: " + todo.getText())
                          .range(range)
                          .textAttributes(TODO_HIGHLIGHT)
                          .create();
                }
            }
        } catch (Exception e) {
            // Silently handle errors to avoid disrupting the editor
        }
    }
}
