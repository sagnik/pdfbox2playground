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
        /*
        public class Line extends Segment
        {
            Line(Point2D.Float start, Point2D.Float end)
            {
                super(start, end);
            }

            //
            // Object override
            //
            @Override
            public String toString()
            {
                StringBuilder builder = new StringBuilder();
                builder.append("    Line to: ")
                        .append(end.getX())
                        .append(", ")
                        .append(end.getY())
                        .append('\n');
                return builder.toString();
            }
        }
        */

        /*
        public class Curve extends Segment
        {
            Curve(Point2D.Float start, Point2D.Float control1, Point2D.Float control2, Point2D.Float end)
            {
                super(start, end);
                this.control1 = control1;
                this.control2 = control2;
            }

            public Point2D getControl1()
            {
                return control1;
            }

            public Point2D getControl2()
            {
                return control2;
            }

            //
            // Object override
            //
            @Override
            public String toString()
            {
                StringBuilder builder = new StringBuilder();
                builder.append("    Curve to: ")
                        .append(end.getX())
                        .append(", ")
                        .append(end.getY())
                        .append(" with Control1: ")
                        .append(control1.getX())
                        .append(", ")
                        .append(control1.getY())
                        .append(" and Control2: ")
                        .append(control2.getX())
                        .append(", ")
                        .append(control2.getY())
                        .append('\n');
                return builder.toString();
            }

            final Point2D control1, control2;
        }
        */

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
        currentPoint = end;
    }

    void curveTo(float x1, float y1, float x2, float y2, float x3, float y3)
    {
        Point2D.Float control1 = new Point2D.Float(x1, y1);
        Point2D.Float control2 = new Point2D.Float(x2, y2);
        Point2D.Float end = new Point2D.Float(x3, y3);
        segments.add(new Curve(currentPoint, control1, control2, end));
        currentPoint = end;
    }

    void closePath()
    {
        closed = true;
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
