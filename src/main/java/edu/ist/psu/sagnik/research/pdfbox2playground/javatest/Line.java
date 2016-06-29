package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import org.apache.pdfbox.util.Matrix;

import java.awt.geom.Point2D;

/**
 * Created by schoudhury on 6/16/16.
 */
public class Line extends Segment {
    Line(Point2D.Float start, Point2D.Float end, Matrix ctm) {
        super(start, end, ctm);
    }

    //
    // Object override
    //

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("    Line from: ")
                .append(start.getX())
                .append(", ")
                .append(start.getY())
                .append("   to: ")
                .append(end.getX())
                .append(", ")
                .append(end.getY())
                .append('\n');

        return builder.toString();
    }
}