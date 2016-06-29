package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import org.apache.pdfbox.util.Matrix;

import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by schoudhury on 6/16/16.
 */
public class Rectangle extends SubPath
{
    float x1;
    float y1;
    float x2;
    float y2;

    Rectangle(Point2D.Float p0, Point2D.Float p1, Point2D.Float p2, Point2D.Float p3, Matrix ctm)
    {
        super(p0);
        lineTo((float)p1.getX(), (float)p1.getY(),ctm);
        lineTo((float)p2.getX(), (float)p2.getY(),ctm);
        lineTo((float)p3.getX(), (float)p3.getY(),ctm);
        closePath(ctm);
        //lineTo((float)p1.getX(), (float)p1.getY());
        x1=setX1(p0,p1,p2,p3);
        y1=setY1(p0,p1,p2,p3);
        x2=setX2(p0,p1,p2,p3);
        y2=setY2(p0,p1,p2,p3);
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

    private float setX1(Point2D.Float p0, Point2D.Float p1, Point2D.Float p2, Point2D.Float p3){
        ArrayList<Float> xs= new ArrayList<>();
        xs.add(p0.x);
        xs.add(p1.x);
        xs.add(p2.x);
        xs.add(p3.x);
        return min(xs);
    }

    private float setY1(Point2D.Float p0, Point2D.Float p1, Point2D.Float p2, Point2D.Float p3){
        ArrayList<Float> ys= new ArrayList<>();
        ys.add(p0.y);
        ys.add(p1.y);
        ys.add(p2.y);
        ys.add(p3.y);
        return min(ys);
    }

    private float setX2(Point2D.Float p0, Point2D.Float p1, Point2D.Float p2, Point2D.Float p3){
        ArrayList<Float> x1s= new ArrayList<>();
        x1s.add(p0.x);
        x1s.add(p1.x);
        x1s.add(p2.x);
        x1s.add(p3.x);
        return max(x1s);
    }

    private float setY2(Point2D.Float p0, Point2D.Float p1, Point2D.Float p2, Point2D.Float p3){
        ArrayList<Float> ys= new ArrayList<>();
        ys.add(p0.y);
        ys.add(p1.y);
        ys.add(p2.y);
        ys.add(p3.y);
        return max(ys);
    }

    java.awt.Rectangle getAwtRectangle(){
        return new java.awt.Rectangle(
                (int) this.x1,
                (int) this.y1,
                (int) (this.x2-this.x1),
                (int) (this.y2-this.y1)

        );
    }

    //
    // Object override
    //
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("  {\n    Rectangle\n    Start at: ")
                .append(start.getX())
                .append(", ")
                .append(start.getY())
                .append('\n');
        for (Segment segment : segments)
            builder.append(segment);
        if (closed)
            builder.append("    Closed\n");
        builder.append("  }\n");
        return builder.toString();
    }
}
