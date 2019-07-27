package com.drewteeter.pdfbomparser;

import com.drewteeter.pdfbomparser.model.BillOfMaterials;

import java.io.FileInputStream;
import java.io.IOException;

public interface Parser {
    BillOfMaterials from(FileInputStream file) throws IOException;
}
