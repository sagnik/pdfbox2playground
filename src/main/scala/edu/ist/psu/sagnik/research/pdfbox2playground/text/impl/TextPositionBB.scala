package edu.ist.psu.sagnik.research.pdfbox2playground.text.impl

import edu.ist.psu.sagnik.research.pdfbox2playground.model.Rectangle
import org.apache.pdfbox.text.TextPosition

/**
  * Created by schoudhury on 6/29/16.
  */
object TextPositionBB {
  def approximate(tP:TextPosition)=Rectangle(
    tP.getXDirAdj,
    (tP.getYDirAdj - tP.getHeightDir),
    tP.getXDirAdj+tP.getWidthDirAdj,
    tP.getYDirAdj//(tP.getYDirAdj - tP.getHeightDir)+tP.getHeightDir
  )

}
