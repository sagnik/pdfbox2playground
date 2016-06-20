package edu.ist.psu.sagnik.research.pdfbox2playground.javatest;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by schoudhury on 6/16/16.
 */


public class Path implements Iterable<SubPath>
{
    public int getWindingRule()
    {
        return windingRule;
    }

    void complete(int windingRule)
    {
        System.out.println("Came from clip");
        finishSubPath();
        this.windingRule = windingRule;
    }

    void appendRectangle(Point2D.Float p0, Point2D.Float p1, Point2D.Float p2, Point2D.Float p3) throws IOException
    {
        finishSubPath();
        //System.out.println("Adding rectangle");
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
        return currentSubPath.currentPoint;
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

    public List<SubPath> getSubPaths()
    {
        return subPaths;
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
        System.out.println("printing path");
        for (SubPath subPath : subPaths)
            builder.append(subPath);
        builder.append("}\n");
        return builder.toString();
    }

    //Point2D.Float currentPoint = null;
    SubPath currentSubPath = null;
    int windingRule = -1;
    final List<SubPath> subPaths = new ArrayList<>();
}
