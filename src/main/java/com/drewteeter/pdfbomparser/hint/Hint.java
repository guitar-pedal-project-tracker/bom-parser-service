package com.drewteeter.pdfbomparser.hint;

public interface Hint {
    boolean matches(String textBlock);
}
