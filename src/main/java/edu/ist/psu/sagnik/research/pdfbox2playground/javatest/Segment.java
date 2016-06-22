package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import org.apache.pdfbox.util.Matrix;

import java.awt.geom.Point2D;

/**
 * Created by schoudhury on 6/16/16.
 */
public class Segment
{
    Segment(Point2D.Float start, Point2D.Float end, Matrix ctm)
    {
        this.start = start;
        this.end = end;
        this.ctm = ctm;
    }

    public Point2D.Float getStart()
    {
        return start;
    }

    public Point2D.Float getEnd()
    {
        return end;
    }

    public Matrix getCTM() { return ctm; }

    final Point2D.Float start, end;
    Matrix ctm;

}


