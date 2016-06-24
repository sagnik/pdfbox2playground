package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

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
        System.out.println(page.getMediaBox()+" , "+page.getCropBox());
        int lines=0;
        int curves=0;
        int rectangles=0;
        int doOps=0;
        int clipPaths=0;
        int noOps=0;
        int closeLines=0;
        int index=0;
        for (Object token:tokens){
            if (token instanceof Operator) {
                Operator op=(Operator) token;
                System.out.println(op.getName());
                if ("cm".equals(op.getName())){
                    System.out.println("<index>: "+index);
                    System.out.println("-------------------------");
                    for (int i=6; i>0; i--){
                        System.out.println(tokens.get(index-i));
                    }
                    System.out.println("-------------------------");
                }
                if ("h".equals(op.getName()))
                    closeLines+=1;
                else if ("n".equals(op.getName()))
                    noOps+=1;
                else if ("Do".equals(op.getName()))
                    doOps+=1;
                else if ("W".equals(op.getName())|| "W*".equals(op.getName()))
                    clipPaths+=1;
                else if ("l".equals(op.getName()))
                    lines+=1;
                else if ("c".equals(op.getName())||"y".equals(op.getName()) ||"v".equals(op.getName())){
                    System.out.println("[index]: "+index);
                    curves+=1;
                }
                else if ("re".equals(op.getName()))
                    rectangles+=1;
            }
            index++;
        }

        System.out.println(
                "total "+index+
                ", lines: "+lines+", closeLines: "+closeLines+", curves: "+
                curves+", rectangles: "+rectangles+", doops: "+doOps+
                        ", clipPaths: "+clipPaths+", noOps: "+noOps);

    }



    public static void main(String[] args) throws Exception{
        String loc=new DataLocation().pdLoc;
        System.out.println(loc+", "+ new DataLocation().pdPageNumber);
        printContent(loc,new DataLocation().pdPageNumber);
    }


}
