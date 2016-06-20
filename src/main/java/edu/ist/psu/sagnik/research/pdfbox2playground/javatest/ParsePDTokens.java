package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import org.apache.pdfbox.contentstream.PDContentStream;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by schoudhury on 6/20/16.
 */
public class ParsePDTokens {
    public static void printContent(String pdfFile, int pageNumber) throws Exception {

        PDDocument doc = PDDocument.load(new File(pdfFile));
        PDPage page = doc.getPage(pageNumber);
        PDFStreamParser parser = new PDFStreamParser(page);
        parser.parse();
        List<Object> tokens = parser.getTokens();
        for (Object token:tokens){
            if (token instanceof Operator) {
                Operator op=(Operator) token;
                System.out.println(op.toString());
            }
        }

    }



    public static void main(String[] args) throws Exception{
        String loc="src/test/resources/test.pdf";
        printContent(loc,4);
    }


}
