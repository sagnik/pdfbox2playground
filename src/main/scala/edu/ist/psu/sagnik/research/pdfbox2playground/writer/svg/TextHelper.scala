package edu.ist.psu.sagnik.research.pdfbox2playground.writer.svg

import edu.ist.psu.sagnik.research.pdfbox2playground.text.model.PDChar

/**
  * Created by schoudhury on 6/30/16.
  */
object TextHelper {

  def getStyleString(c:PDChar):String="font-variant:normal;font-weight:normal;font-size:" +
    fontSize + "px;" +
    "font-family:" +
    fontFamily + ";" +
    "-inkscape-font-specification:" +
    fontName + ";" +
    "writing-mode:lr-tb;" +
    "fill:" +
    fontFill + ";" +
    "fill-opacity:" +
    fillOpacity + ";" +
    "fill-rule:" +
    fillRule + ";" +
    "stroke:" +
    stroke

  def getLocationString(c:PDChar,h:Float):String="y=\"" +
    (h-c.bb.y1) +
    "\" x=\"" +
    c.bb.x1 +
    "\">"


}
