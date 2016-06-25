package edu.ist.psu.sagnik.research.pdfbox2playground.writer.svg

import edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Segment
import edu.ist.psu.sagnik.research.pdfbox2playground.path.model._

import scala.reflect.io.File

/**
 * Created by schoudhury on 6/17/16.
 */
object CreateSVG {

  def svgLineString(s:PDSegment,w:Float,h:Float):String=
    s match {
      case s: PDLine =>
        "<path d=\"M " +
          (s.startPoint.x).toString + "," + (h - s.startPoint.y).toString +
          " L " +
          (s.endPoint.x).toString + "," + (h - s.endPoint.y).toString +
          "\" style=\"fill:none;stroke:#000000;stroke-width:3.76389003;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:10;stroke-dasharray:none;stroke-opacity:1;\" " +
          " xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"/>"

      case s: PDCurve =>
        "<path d=\"M " +
          (s.startPoint.x).toString + "," + (h - s.startPoint.y).toString +
          " C " +
          (s.controlPoint1.x).toString + "," + (h - s.controlPoint1.y).toString + " "+
          (s.controlPoint2.x).toString + "," + (h - s.controlPoint2.y).toString + " "+
          (s.endPoint.x).toString + "," + (h - s.endPoint.y).toString + " "+
          "\" style=\"fill:none;stroke:#000000;stroke-width:3.76389003;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:10;stroke-dasharray:none;stroke-opacity:1;\" " +
          " xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"/>"

      case _ => ""
    }

  def svgLineString(s:Segment,w:Float,h:Float):String=
    "<path d=\"M " +
      (s.getStart.x).toString+","+(h-s.getStart.y).toString+
      " L " +
      (s.getEnd.x).toString+","+(h-s.getEnd.y).toString +
      "\" style=\"fill:none;stroke:#000000;stroke-width:0.39300001;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:10;stroke-dasharray:none;stroke-opacity:1;\" " +
      " xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"/>"

  def svgLineString(s:Segment,w:Float,h:Float,curveAware:Boolean):String=
    if (s.isInstanceOf[edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Line])
      "<path d=\"M " +
        (s.getStart.x).toString+","+(h-s.getStart.y).toString+
        " L " +
        (s.getEnd.x).toString+","+(h-s.getEnd.y).toString +
        "\" style=\"fill:none;stroke:#000000;stroke-width:0.39300001;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:10;stroke-dasharray:none;stroke-opacity:1;\" " +
        " xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"/>"
    else
      ""

  def apply(segments:List[Segment],svgLoc:String,width:Float,height:Float):Unit={
    val svgStart="<?xml version=\"1.0\" standalone=\"no\"?>\n\n<svg height=\"" +
      height +
      "\" width=\"" +
      width +
      "\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">"+
      "\n"

    val svgString= segments.map(x=>svgLineString(x,width,height)).foldLeft("")((a,b)=>a+"\n"+b)+
      "\n"

    val svgEnd="\n</svg>"

    File(svgLoc).writeAll(svgStart+svgString+svgEnd)

  }

  def fromSegments(segments:List[PDSegment], svgLoc:String, width:Float, height:Float):Unit={
    val svgStart="<?xml version=\"1.0\" standalone=\"no\"?>\n\n<svg height=\"" +
      height +
      "\" width=\"" +
      width +
      "\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">"+
      "\n"

    val svgString= segments.map(x=>svgLineString(x,width,height)).foldLeft("")((a,b)=>a+"\n"+b)+
      "\n"

    val svgEnd="\n</svg>"

    File(svgLoc).writeAll(svgStart+svgString+svgEnd)

  }

  def segmentToString(s:PDSegment,h:Float):String=s match{
    case s: PDLine =>
      "M " +
        s.startPoint.x.toString + "," + (h - s.startPoint.y).toString +
        " L " +
        s.endPoint.x.toString + "," + (h - s.endPoint.y).toString+
      " "
    case s: PDCurve =>
      "M " +
        s.startPoint.x.toString + "," + (h - s.startPoint.y).toString +
        " C " +
        s.controlPoint1.x.toString + "," + (h - s.controlPoint1.y).toString + " "+
        s.controlPoint2.x.toString + "," + (h - s.controlPoint2.y).toString + " "+
        s.endPoint.x.toString + "," + (h - s.endPoint.y).toString+
        " "
    case _ => ""

  }

  def getStyleString(p:PathStyle): String ={
    val styleStart=" style=\""
    val styles=List(
      p.fill match {case Some(f) => "fill:"+f; case _ => "fill:none"},
      p.fillRule match {case Some(f) => "fill-rule:"+f; case _ => "fill-rule:nonzero"},
      p.fillOpacity match {case Some(f) => "fill-opacity:"+f; case _ => "fill-opacity:1"},
      p.stroke match {case Some(f) => "stroke:"+f; case _ => "stroke:none"},
      p.strokeWidth match {case Some(f) => "stroke-width:"+f; case _ => "stroke-width:1"},
      p.strokeLineCap match {case Some(f) => "stroke-linecap:"+f; case _ => "stroke-linecap:butt"},
      p.strokeLineJoin match {case Some(f) => "stroke-linejoin:"+f; case _ => "stroke-linejoin:miter"},
      p.strokeMiterLimit match {case Some(f) => "stroke-miterlimit:"+f; case _ => "stroke-miterlimit:4"},
      p.strokeDashArray match {case Some(f) => "stroke-dasharray:"+f; case _ => "stroke-dasharray:none"},
      p.strokeDashOffset match {case Some(f) => "stroke-dashoffset:"+f; case _ => "stroke-dashoffset:0"},
      p.strokeOpacity match {case Some(f) => "stroke-opacity:"+f; case _ => "stroke-opacity:1"}
    ).mkString(";")
    val styleEnd="\""
    styleStart+styles+styleEnd
  }

  def getPathDString(p:PDPath,h:Float):String={
    val dStringStart=" d=\""
    val segmentStrings=p.subPaths.flatMap(x=>x.segments).foldLeft(" ")((a,b)=>a+segmentToString(b,h))
    val dStringEnd="\""
    dStringStart+segmentStrings+dStringEnd
  }

  def svgPathString(p:PDPath,w:Float,h:Float):String={
    val pathStart= "<path"
    val pathEnd="/>"
    pathStart+getPathDString(p,h)+getStyleString(p.pathStyle)+pathEnd

  }
  def fromPath(paths:List[PDPath], svgLoc:String, width:Float, height:Float):Unit={
    val svgStart="<?xml version=\"1.0\" standalone=\"no\"?>\n\n<svg height=\"" +
      height +
      "\" width=\"" +
      width +
      "\" version=\"1.1\" xmlns=\"http://www.w3.org/2000/svg\">"+
      "\n"

    val svgString= paths.map(x=>svgPathString(x,width,height)).foldLeft("")((a,b)=>a+"\n"+b)+
      "\n"

    val svgEnd="\n</svg>"

    File(svgLoc).writeAll(svgStart+svgString+svgEnd)

  }


}
