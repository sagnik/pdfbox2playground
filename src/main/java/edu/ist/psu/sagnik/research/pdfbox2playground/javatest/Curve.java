package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import org.apache.pdfbox.util.Matrix;

import java.awt.geom.Point2D;

/**
 * Created by schoudhury on 6/16/16.
 */
public class Curve extends Segment
{
    Curve(Point2D.Float start, Point2D.Float control1, Point2D.Float control2, Point2D.Float end, Matrix ctm)
    {
        super(start, end, ctm);
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
        builder.append("    Curve from: ")
                .append(start.getX())
                .append(", ")
                .append(start.getY())
                .append("    to: ")
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
                .append(control2.getY());
//                .append('\n');
        return builder.toString();
    }

    final Point2D control1, control2;
}
