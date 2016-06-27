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
  def getBoundingBox:Rectangle
}
case class PDChar(content:String,bb:Rectangle,font:PDFont) extends TextSegment{
  def getBoundingBox=this.bb
}

case class PDWord(content:String,chars:Seq[PDChar]) extends TextSegment{
  def getBoundingBox=CalculateBB(this)
}

case class PDTextLine(content:String,tWords:Seq[PDWord]) extends TextSegment{
  def getBoundingBox=CalculateBB(this)
}

case class PDParagraph(content:String,tLines:Seq[PDTextLine]) extends TextSegment{
  def getBoundingBox=CalculateBB(this)
}

