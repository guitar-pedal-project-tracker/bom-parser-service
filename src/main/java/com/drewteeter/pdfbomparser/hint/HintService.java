package com.drewteeter.pdfbomparser.hint;

import com.drewteeter.pdfbomparser.document.DocumentSection;

import java.util.List;
import java.util.Optional;

public interface HintService {
    List<Hint> getHintsForDocumentSection(DocumentSection section, Optional<String> knownDesigner);
}
