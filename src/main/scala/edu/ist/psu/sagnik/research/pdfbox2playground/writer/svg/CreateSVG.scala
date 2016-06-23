package edu.ist.psu.sagnik.research.pdfbox2playground.writer.svg

import edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Segment
import edu.ist.psu.sagnik.research.pdfbox2playground.path.model.{PDCurve, PDLine, PDSegment}

import scala.reflect.io.File

/**
  * Created by schoudhury on 6/17/16.
  */
object CreateSVG {

  def svgLineString(s:PDSegment,w:Float,h:Float):String=
    s match {
        case s: PDLine =>
      "<path d=\"M " +
      (w -s.startPoint.x).toString + "," + (h - s.startPoint.y).toString +
      " L " +
      (w - s.endPoint.x).toString + "," + (h - s.endPoint.y).toString +
      "\" style=\"fill:none;stroke:#000000;stroke-width:0.39300001;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:10;stroke-dasharray:none;stroke-opacity:1;\" " +
      " xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"/>"

        case s: PDCurve =>
          "<path d=\"M " +
            (w -s.startPoint.x).toString + "," + (h - s.startPoint.y).toString +
            " C " +
            (w - s.controlPoint1.x).toString + "," + (h - s.controlPoint1.y).toString + " "+
            (w - s.controlPoint2.x).toString + "," + (h - s.controlPoint2.y).toString + " "+
            (w - s.endPoint.x).toString + "," + (h - s.endPoint.y).toString + " "+
            "\" style=\"fill:none;stroke:#000000;stroke-width:0.39300001;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:10;stroke-dasharray:none;stroke-opacity:1;\" " +
            " xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"/>"

        case _ => ""
    }

  def svgLineString(s:Segment,w:Float,h:Float):String=
    "<path d=\"M " +
      (w-s.getStart.x).toString+","+(h-s.getStart.y).toString+
      " L " +
      (w-s.getEnd.x).toString+","+(h-s.getEnd.y).toString +
      "\" style=\"fill:none;stroke:#000000;stroke-width:0.39300001;stroke-linecap:butt;stroke-linejoin:miter;stroke-miterlimit:10;stroke-dasharray:none;stroke-opacity:1;\" " +
      " xmlns=\"http://www.w3.org/2000/svg\" xmlns:svg=\"http://www.w3.org/2000/svg\"/>"

  def svgLineString(s:Segment,w:Float,h:Float,curveAware:Boolean):String=
    if (s.isInstanceOf[edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Line])
      "<path d=\"M " +
        (w-s.getStart.x).toString+","+(h-s.getStart.y).toString+
        " L " +
        (w-s.getEnd.x).toString+","+(h-s.getEnd.y).toString +
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

  def apply(segments:List[PDSegment], svgLoc:String, width:Float, height:Float, fromScala:Boolean):Unit={
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

}
