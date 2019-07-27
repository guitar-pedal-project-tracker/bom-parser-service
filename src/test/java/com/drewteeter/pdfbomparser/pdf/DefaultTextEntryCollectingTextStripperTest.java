package com.drewteeter.pdfbomparser.pdf;

import com.drewteeter.pdfbomparser.document.DocumentSection;
import com.drewteeter.pdfbomparser.hint.impl.StaticHintService;
import com.drewteeter.pdfbomparser.model.Table;
import com.drewteeter.pdfbomparser.pdf.impl.DefaultTextEntryNormalizer;
import com.drewteeter.pdfbomparser.pdf.impl.DefaultSectionExtractor;
import com.drewteeter.pdfbomparser.pdf.impl.DefaultTableExtractor;
import com.drewteeter.pdfbomparser.pdf.impl.DefaultTextEntryCollectingTextStripper;
import com.drewteeter.pdfbomparser.pdf.model.NormalizedTextEntry;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class DefaultTextEntryCollectingTextStripperTest {
    @Test
    public void testTextEntryCollection() throws IOException {
        try (PDDocument pdDocument = PDDocument.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(
                "build-doc-pdfs/pedalpcb/monarch.pdf"
        ))) {
            List<NormalizedTextEntry> textEntries = new DefaultTextEntryNormalizer()
                    .normalize(new DefaultTextEntryCollectingTextStripper().collectEntries(pdDocument));

            List<NormalizedTextEntry> partsEntries = new DefaultSectionExtractor()
                    .entriesInSection(new StaticHintService().getHintsForDocumentSection(DocumentSection.BILL_OF_MATERIALS,
                            Optional.empty()), textEntries);

            List<Table> tables = new DefaultTableExtractor().tablesInEntries(partsEntries);
        }
    }
}