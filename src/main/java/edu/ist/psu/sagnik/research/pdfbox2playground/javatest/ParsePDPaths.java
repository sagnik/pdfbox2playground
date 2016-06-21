package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.File;
import java.io.IOException;

/**
 * Created by schoudhury on 6/20/16.
 */
public class ParsePDPaths {

    public static void main(String[] args) throws IOException{
        String loc= new DataLocation().pdLoc;
        PDDocument document = PDDocument.load(new File(loc));
        PDPage page = document.getPage(new DataLocation().pdPageNumber);
        LinePathFinder finder = new LinePathFinder(page);
        finder.findLinePaths();

//        ClipPathFinder finder = new ClipPathFinder(page);
//        finder.findClipPaths();

//        for (Path p: finder.paths){
//            System.out.println(p);
//        }

        int linePaths=0;
        int curvePaths=0;
        int rectangles=0;
        for (Path p:finder.paths){
            for (SubPath sp: p.subPaths){
                if (sp instanceof Rectangle)
                    rectangles+=1;
                else {
                    for (Segment s : sp.segments) {
                        if (s instanceof Line) {
                            linePaths += 1;
                            Line l=(Line) s;
                            //System.out.println("Got a line, "+l.toString());
                        } else if (s instanceof Curve) {
                            curvePaths += 1;
                            Curve c=(Curve)s;
                            System.out.println(c.toString());
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
