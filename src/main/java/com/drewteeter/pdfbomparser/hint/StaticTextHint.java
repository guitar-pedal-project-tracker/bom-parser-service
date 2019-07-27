package com.drewteeter.pdfbomparser.hint;

public class StaticTextHint implements Hint {
    private String text;
    private boolean caseSensitive;

    public StaticTextHint(String text) {
        this(text, false);
    }

    public StaticTextHint(String text, boolean caseSensitive) {
        this.text = text;
        this.caseSensitive = caseSensitive;
    }

    @Override
    public boolean matches(String textBlock) {
        return caseSensitive ? text.equals(textBlock) : text.equalsIgnoreCase(textBlock);
    }
}
