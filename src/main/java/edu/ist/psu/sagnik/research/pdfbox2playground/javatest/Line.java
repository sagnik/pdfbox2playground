package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import java.awt.geom.Point2D;

/**
 * Created by schoudhury on 6/16/16.
 */
public class Line extends Segment {
    Line(Point2D.Float start, Point2D.Float end) {
        super(start, end);
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
                .append(end.getY());
//                .append('\n');

        return builder.toString();
    }
}