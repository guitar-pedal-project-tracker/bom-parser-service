package com.drewteeter.pdfbomparser.model;

import java.util.List;

public class BillOfMaterials {
    private List<Component> components;

    public BillOfMaterials(List<Component> components) {
        this.components = components;
    }

    public List<Component> getComponents() {
        return components;
    }
}