package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import java.awt.geom.Point2D;

/**
 * Created by schoudhury on 6/16/16.
 */
public class Segment
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


