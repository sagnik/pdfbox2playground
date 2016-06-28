package edu.ist.psu.sagnik.research.pdfbox2playground.text.impl

import edu.ist.psu.sagnik.research.pdfbox2playground.model.{Rectangle}
import edu.ist.psu.sagnik.research.pdfbox2playground.text.model._

/**
  * Created by schoudhury on 6/27/16.
  */
object CalculateBB {

  def apply(texts:List[TextSegment]):Rectangle=
    if (texts.nonEmpty)
      Rectangle(
        texts.map(x=>x.bb.x1).min,
        texts.map(x=>x.bb.y1).min,
        texts.map(x=>x.bb.x2).max,
        texts.map(x=>x.bb.y2).max
      )
    else
      Rectangle(0f,0f,0f,0f)


}
