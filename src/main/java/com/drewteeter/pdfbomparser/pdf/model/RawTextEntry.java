package com.drewteeter.pdfbomparser.pdf.model;

import org.apache.pdfbox.text.TextPosition;

import java.util.List;

public class RawTextEntry {
    private String text;
    private int page;
    private List<TextPosition> textPositions;

    public RawTextEntry(String text, int page, List<TextPosition> textPositions) {
        this.text = text;
        this.page = page;
        this.textPositions = textPositions;
    }

    public String getText() {
        return text;
    }

    public int getPage() {
        return page;
    }

    public List<TextPosition> getTextPositions() {
        return textPositions;
    }
}
