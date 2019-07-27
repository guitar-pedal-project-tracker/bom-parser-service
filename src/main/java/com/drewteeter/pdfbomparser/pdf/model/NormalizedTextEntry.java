package com.drewteeter.pdfbomparser.pdf.model;

public class NormalizedTextEntry {
    private String text;
    private int page;
    private NormalizedPosition position;

    public NormalizedTextEntry(String text, int page, NormalizedPosition position) {
        this.text = text;
        this.page = page;
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public int getPage() {
        return page;
    }

    public NormalizedPosition getPosition() {
        return position;
    }
}
