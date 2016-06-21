package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by schoudhury on 6/16/16.
 */
public class SubPath implements Iterable<Segment>
{
    SubPath(Point2D.Float start)
    {
        this.start = start;
        currentPoint = start;
    }

    public Point2D getStart()
    {
        return start;
    }

    void lineTo(float x, float y)
    {
        Point2D.Float end = new Point2D.Float(x, y);
        segments.add(new Line(currentPoint, end));
        //System.out.println("Got a line, "+new Line(currentPoint, end));
        currentPoint = end;
    }

    void curveTo(float x1, float y1, float x2, float y2, float x3, float y3)
    {
        Point2D.Float control1 = new Point2D.Float(x1, y1);
        Point2D.Float control2 = new Point2D.Float(x2, y2);
        Point2D.Float end = new Point2D.Float(x3, y3);
        segments.add(new Curve(currentPoint, control1, control2, end));
        //System.out.println("Got a curve, "+new Curve(currentPoint, control1, control2, end));
        currentPoint = end;
    }

    void closePath()
    {
        closed = true;
        segments.add(new Line(currentPoint,start));
        currentPoint = start;
    }

    //
    // Iterable<Segment> implementation
    //
    public Iterator<Segment> iterator()
    {
        return segments.iterator();
    }

    //
    // Object override
    //
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("  {\n    Start at: ")
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

    boolean closed = false;
    final Point2D.Float start;
    final List<Segment> segments = new ArrayList<Segment>();

    Point2D.Float currentPoint = null;
}
