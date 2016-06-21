package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by schoudhury on 6/20/16.
 */
public class ParsePDPaths {

    public static void main(String[] args) throws IOException{
        String loc="src/test/resources/pg_0005.pdf";
        PDDocument document = PDDocument.load(new File(loc));
        PDPage page = document.getPage(0);
        LinePathFinder finder = new LinePathFinder(page);
        finder.findLinePaths();
        int linePaths=0;
        int curvePaths=0;
        int rectangles=0;
        for (Path p:finder.paths){
            for (SubPath sp: p.subPaths){
                if (sp instanceof Rectangle)
                    rectangles+=1;
                else {
                    for (Segment s : sp.segments) {
                        //System.out.println("processing segment: ");
                        if (s instanceof Line) {
                            linePaths += 1;
                        } else if (s instanceof Curve) {
                            curvePaths += 1;
                        }
                        else {
                            System.out.println(s.getClass());
                        }
                    }
                }

            }
        }
        System.out.println(linePaths+" "+curvePaths+" "+rectangles);

    }
}
