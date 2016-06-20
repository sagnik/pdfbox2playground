package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
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
        int lines=0;
        int curves=0;
        for (Object token:tokens){
            if (token instanceof Operator) {
                Operator op=(Operator) token;
                if ("l".equals(op.getName()))
                    lines+=1;
                else if ("c".equals(op.getName())||"y".equals(op.getName()) ||"v".equals(op.getName())){
                    System.out.println(op);
                    curves+=1;
                }


            }
        }
        System.out.println(lines+","+curves);

    }



    public static void main(String[] args) throws Exception{
        String loc="src/test/resources/test.pdf";
        printContent(loc,4);
    }


}
