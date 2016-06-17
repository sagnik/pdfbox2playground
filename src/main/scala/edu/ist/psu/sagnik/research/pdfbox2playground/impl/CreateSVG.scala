package edu.ist.psu.sagnik.research.pdfbox2playground.impl

import edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Segment

import scala.reflect.io.File

/**
  * Created by schoudhury on 6/17/16.
  */
object CreateSVG {

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

}
