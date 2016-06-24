package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;


import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by schoudhury on 6/20/16.
 */
public class ParsePDPaths {

    private static void drawRect(PDPageContentStream content, Color color, java.awt.Rectangle rect, boolean fill) throws IOException{
        content.addRect(rect.x, rect.y, rect.width, rect.height);
        if (fill) {
            content.setNonStrokingColor(color);
            content.fill();
        } else {
            content.setStrokingColor(color);
            content.stroke();
        }
    }

    private static float[] lineBB(Line line){
        float x1= min(((float) line.getStart().getX()),((float) line.getStart().getX()));
        float y1= min(((float) line.getStart().getY()),((float) line.getStart().getY()));
        float x2= max(((float) line.getEnd().getX()), ((float) line.getEnd().getX()));
        float y2= max(((float) line.getEnd().getY()), ((float) line.getEnd().getY()));
        return new float[] {x1,y1,x2,y2};
        //return new java.awt.Rectangle((int) x1, (int) y1, (int) (x2-x1), (int) (y2-y1));
    }

    private static float min(float x, float y) {
        if (x>y) return y; else return x;
    }

    private static float min(ArrayList<Float> vals) {
        float min=100000f;
        for (float val:vals){
            if (val<min)
                min=val;
        }
        return min;
    }

    private static float max(ArrayList<Float> vals) {
        float max=0f;
        for (float val:vals){
            if (val>max)
                max=val;
        }
        return max;
    }


    private static float max(float x, float y) {
        if (x<y) return y; else return x;
    }

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
        float minX=page.getMediaBox().getWidth();
        float minY=page.getMediaBox().getHeight();
        float maxX=0;
        float maxY=0;
        ArrayList<float[]> bbs= new ArrayList<>();
        for (Path p:finder.paths){
            for (SubPath sp: p.subPaths){
                if (sp instanceof Rectangle)
                    rectangles+=1;
                else {
                    for (Segment s : sp.segments) {
                        if (s instanceof Line) {
                            linePaths += 1;
                            Line l=(Line) s;
                            bbs.add(lineBB(l));
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

        java.awt.Rectangle wholeBB=getWholeBB(bbs);
        PDPageContentStream content= new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,false);
        drawRect(content, Color.BLUE, wholeBB, false);
        content.close();

        document.save(loc.substring(0,loc.length()-4)+"-rect.pdf");
        document.close();

        System.out.println(linePaths+" "+curvePaths+" "+rectangles);

    }

    private static java.awt.Rectangle getWholeBB(ArrayList<float[]> bbs) {
        ArrayList<Float> x1s= new ArrayList<Float>();
        ArrayList<Float> y1s= new ArrayList<Float>();
        ArrayList<Float> x2s= new ArrayList<Float>();
        ArrayList<Float> y2s= new ArrayList<Float>();

        for (float[] bb:bbs){
            x1s.add(bb[0]);
            y1s.add(bb[1]);
            x2s.add(bb[2]);
            y2s.add(bb[3]);
        }

        int startX=(int) min(x1s);
        int startY=(int) min(y1s);
        int width= (int) (max(x1s)-startX);
        int height= (int) (max(y1s)-startY);

        return new java.awt.Rectangle(startX,startY,width,height);
    }
}
