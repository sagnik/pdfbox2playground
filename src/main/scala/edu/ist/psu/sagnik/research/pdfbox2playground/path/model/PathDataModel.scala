package edu.ist.psu.sagnik.research.pdfbox2playground.path.model

import java.awt.geom.Point2D

import edu.ist.psu.sagnik.research.pdfbox2playground.model.Rectangle
import org.apache.pdfbox.util.Matrix

/**
  * Created by schoudhury on 6/15/16.
  */

/*
A path is composed of straight and curved line segments, which may connect to
one another or may be disconnected. A pair of segments are said to connect only
if they are defined consecutively, with the second segment starting where the first
one ends. Thus, the order in which the segments of a path are defined is significant.
Nonconsecutive segments that meet or intersect fortuitously are not considered
to connect.
A path is made up of one or more disconnected subpaths, each comprising a sequence
of connected segments. The topology of the path is unrestricted: it may be
concave or convex, may contain multiple subpaths representing disjoint areas,
and may intersect itself in arbitrary ways. The h operator explicitly connects the
end of a subpath back to its starting point; such a subpath is said to be closed. A
subpath that has not been explicitly closed is open.
As discussed in Section 4.1, “Graphics Objects,” a path object is defined by a sequence
of operators to construct the path, followed by one or more operators to
paint the path or to use it as a clipping boundary.
 */

trait Segment{
  def startPoint:Point2D.Float
  def endPoint:Point2D.Float
  def ctm: Matrix
  //def getBoundingBox[A](lastEndPoint:Point):Rectangle
  //def getEndPoint[A](lastEndPoint:Point):Point
}

trait SubPath{
  def segments:List[Segment]
}


case class Line(startPoint:Point2D.Float,endPoint:Point2D.Float, ctm: Matrix) extends Segment
case class Curve(startPoint:Point2D.Float,endPoint:Point2D.Float, controlPoint1:
Point2D.Float, controlPoint2: Point2D.Float, ctm: Matrix) extends Segment
case class PDRect(segments:List[Line]) extends SubPath
case class PDShape(segments:List[Segment]) extends SubPath

case class Path(currentPoint:Point2D.Float,subPaths:List[SubPath],isClip:Boolean,doPaint:Boolean, windingRule:Int)

//this comes from the graphics state
case class PathStyle(
                      fill:Option[String],
                      fillRule:Option[String],
                      fillOpacity:Option[String],
                      stroke:Option[String],
                      strokeWidth:Option[String],
                      strokeLineCap:Option[String],
                      strokeLineJoin:Option[String],
                      strokeMiterLimit:Option[String],
                      strokeDashArray:Option[String],
                      strokeDashOffset:Option[String],
                      strokeOpacity:Option[String]
                    )

