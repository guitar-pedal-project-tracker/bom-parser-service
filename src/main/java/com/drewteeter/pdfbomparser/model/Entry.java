package com.drewteeter.pdfbomparser.model;

public class Entry {
    private Component component;
    private int quantity;

    public Entry(Component component, int quantity) {
        this.component = component;
        this.quantity = quantity;
    }

    public Component getComponent() {
        return component;
    }

    public int getQuantity() {
        return quantity;
    }
}
