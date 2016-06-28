package edu.ist.psu.sagnik.research.pdfbox2playground.text.model

import edu.ist.psu.sagnik.research.pdfbox2playground.model.Rectangle
import org.apache.pdfbox.pdmodel.font.PDFont
import edu.ist.psu.sagnik.research.pdfbox2playground.text.impl.CalculateBB

/**
  * Created by schoudhury on 6/27/16.
  */
//why not PDChar.content is a String and not a char? PDChar is created by overriding the writeText method
//of the TextStripper, which processes the TextPosition object. TextPosition might
//contain String instead of chars.

sealed trait TextSegment{
  def content:String
  def bb:Rectangle
}
case class PDChar(content:String,bb:Rectangle,font:PDFont) extends TextSegment

case class PDWord(content:String,bb:Rectangle,chars:List[PDChar]) extends TextSegment

case class PDTextLine(content:String,bb:Rectangle,tWords:List[PDWord]) extends TextSegment

case class PDParagraph(content:String,bb:Rectangle,tLines:List[PDTextLine]) extends TextSegment

