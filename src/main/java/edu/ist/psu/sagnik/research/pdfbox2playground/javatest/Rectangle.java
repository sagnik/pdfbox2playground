package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import java.awt.geom.Point2D;

/**
 * Created by schoudhury on 6/16/16.
 */
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
