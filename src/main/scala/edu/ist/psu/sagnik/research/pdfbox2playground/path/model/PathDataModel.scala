package edu.ist.psu.sagnik.research.pdfbox2playground.path.model

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
case class Point(x:Float,y:Float)

trait PDOperator{
  def getBoundingBox[A](lEP:Point, ctm:Matrix, paths:Seq[Any]):Rectangle
  def getEndPoint[A](lEP:Point, paths:Seq[Any]):Point
}

trait Segment{
  def startPoint:Point
  def endPoint:Point
  //def getBoundingBox[A](endPoint:Point, ctm:Matrix, paths:Seq[Any]):Rectangle
}

case class MoveOperator(startPoint:Point,endPoint:Point) extends Segment
case class LineOperator(startPoint:Point,endPoint:Point) extends Segment
case class BCurveCOperator(startPoint:Point,endPoint:Point) extends Segment
case class BCurveVOperator(startPoint:Point,endPoint:Point) extends Segment
case class BCurveYOperator(startPoint:Point,endPoint:Point) extends Segment
case class CloseOperator(startPoint:Point,endPoint:Point) extends Segment
case class RectOperator(startPoint:Point,endPoint:Point) extends Segment

case class SubPath(segments:IndexedSeq[Segment])

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

