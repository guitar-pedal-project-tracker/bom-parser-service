package com.drewteeter.pdfbomparser.pdf.model;

public class NormalizedPosition {
    private float x;
    private float y;
    private float endX;
    private float endY;
    private float height;
    private float width;

    public NormalizedPosition(float x, float y, float endX, float height) {
        this(x, y, endX, y + height, height, endX - x);
    }

    public NormalizedPosition(float x, float y, float endX, float endY, float height, float width) {
        this.x = x;
        this.y = y;
        this.endX = endX;
        this.endY = endY;
        this.height = height;
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getEndX() {
        return endX;
    }

    public float getEndY() {
        return endY;
    }
}
