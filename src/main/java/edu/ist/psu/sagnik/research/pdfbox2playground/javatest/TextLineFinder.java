package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.IOException;

/**
 * Created by schoudhury on 6/27/16.
 */
public class TextLineFinder extends PDFTextStripper{

    public TextLineFinder(PDDocument document, String filename) throws IOException
    {
        this.document = document;
    }



}
