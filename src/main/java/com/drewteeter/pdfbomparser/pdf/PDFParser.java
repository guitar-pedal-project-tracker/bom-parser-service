package com.drewteeter.pdfbomparser.pdf;

import com.drewteeter.pdfbomparser.Parser;
import com.drewteeter.pdfbomparser.model.BillOfMaterials;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PDFParser implements Parser {
    @Override
    public BillOfMaterials from(FileInputStream file) throws IOException {
        // 1. Attempt to identify PDF relative to known types -- Aion, PedalPCB, RullyWow
        // 2. Collect potential section identifiers based on known type or standard list
        // 3. Find BoM start
        // 4. Find BoM end
        // 5. Collect text entries within BoM section
        // 6. Attempt recognize table format from BoM section based on text entry positions
        // 7. If table format recognized, parse table to build BoM

        return new BillOfMaterials(new ArrayList<>());
    }
}
