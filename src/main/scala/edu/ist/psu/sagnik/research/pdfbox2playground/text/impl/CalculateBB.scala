package edu.ist.psu.sagnik.research.pdfbox2playground.text.impl

import edu.ist.psu.sagnik.research.pdfbox2playground.model.{Rectangle}
import edu.ist.psu.sagnik.research.pdfbox2playground.text.model._

/**
  * Created by schoudhury on 6/27/16.
  */
object CalculateBB {

  //def apply(t:TextSegment):Rectangle=Rectangle(0f,0f,0f,0f)

  def apply(text:TextSegment):Rectangle=text match{
    case text:PDWord => Rectangle(
      text.chars.map(x=>x.bb.x1).min,
      text.chars.map(x=>x.bb.y1).min,
      text.chars.map(x=>x.bb.x2).max,
      text.chars.map(x=>x.bb.y2).max
    )
    case text:PDTextLine => Rectangle(
      text.tWords.map(x=>CalculateBB(x).x1).min,
      text.tWords.map(x=>CalculateBB(x).y1).min,
      text.tWords.map(x=>CalculateBB(x).x2).max,
      text.tWords.map(x=>CalculateBB(x).y2).max
    )
    case text:PDParagraph => Rectangle(
      text.tLines.map(x=>CalculateBB(x).x1).min,
      text.tLines.map(x=>CalculateBB(x).y1).min,
      text.tLines.map(x=>CalculateBB(x).x2).max,
      text.tLines.map(x=>CalculateBB(x).y2).max
    )
    case _ => Rectangle(0f,0f,0f,0f)

  }


}
