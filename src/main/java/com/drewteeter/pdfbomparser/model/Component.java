package com.drewteeter.pdfbomparser.model;

public class Component {
    private ComponentType type;
    private String name;
    private String details;
    private String description;

    public Component(ComponentType type, String name, String details, String description) {
        this.type = type;
        this.name = name;
        this.details = details;
        this.description = description;
    }

    public ComponentType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public String getDescription() {
        return description;
    }
}
