package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by schoudhury on 6/16/16.
 */


public class Path implements Iterable<Path.SubPath>
{
    public static class Segment
    {
        Segment(Point2D.Float start, Point2D.Float end)
        {
            this.start = start;
            this.end = end;
        }

        public Point2D.Float getStart()
        {
            return start;
        }

        public Point2D.Float getEnd()
        {
            return end;
        }

        final Point2D.Float start, end;
    }

    public class SubPath implements Iterable<Segment>
    {
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
        final List<Segment> segments = new ArrayList<Path.Segment>();
    }

    public class Rectangle extends SubPath
    {
        Rectangle(Point2D.Float p0, Point2D.Float p1, Point2D.Float p2, Point2D.Float p3)
        {
            super(p0);
            lineTo((float)p1.getX(), (float)p1.getY());
            lineTo((float)p2.getX(), (float)p2.getY());
            lineTo((float)p3.getX(), (float)p3.getY());
            closePath();
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

    public int getWindingRule()
    {
        return windingRule;
    }

    void complete(int windingRule)
    {
        finishSubPath();
        this.windingRule = windingRule;
    }

    void appendRectangle(Point2D.Float p0, Point2D.Float p1, Point2D.Float p2, Point2D.Float p3) throws IOException
    {
        finishSubPath();
        currentSubPath = new Rectangle(p0, p1, p2, p3);
        finishSubPath();
    }

    void moveTo(float x, float y) throws IOException
    {
        finishSubPath();
        currentSubPath = new SubPath(new Point2D.Float(x, y));
    }

    void lineTo(float x, float y) throws IOException
    {
        currentSubPath.lineTo(x, y);
    }

    void curveTo(float x1, float y1, float x2, float y2, float x3, float y3) throws IOException
    {
        currentSubPath.curveTo(x1, y1, x2, y2, x3, y3);
    }

    Point2D.Float getCurrentPoint() throws IOException
    {
        return currentPoint;
    }

    void closePath() throws IOException
    {
        currentSubPath.closePath();
        finishSubPath();
    }

    void finishSubPath()
    {
        if (currentSubPath != null)
        {
            subPaths.add(currentSubPath);
            currentSubPath = null;
        }
    }

    //
    // Iterable<Path.SubPath> implementation
    //
    public Iterator<SubPath> iterator()
    {
        return subPaths.iterator();
    }

    //
    // Object override
    //
    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n  Winding: ")
                .append(windingRule)
                .append('\n');
        for (SubPath subPath : subPaths)
            builder.append(subPath);
        builder.append("}\n");
        return builder.toString();
    }

    Point2D.Float currentPoint = null;
    SubPath currentSubPath = null;
    int windingRule = -1;
    final List<SubPath> subPaths = new ArrayList<Path.SubPath>();
}
