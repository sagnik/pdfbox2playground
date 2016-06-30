package edu.ist.psu.sagnik.research.pdfbox2playground.text.impl


import java.awt.geom.{AffineTransform, Rectangle2D}

import edu.ist.psu.sagnik.research.pdfbox2playground.model.Rectangle
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.font.{PDFont, PDType3Font}
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

  //see method writeString @org.apache.pdfbox.examples.util.DrawPrintTextLocations
  //TODO: check correctness
  def fontBased(tP:TextPosition,pdPage:PDPage):Rectangle={
    val font = tP.getFont
    val bbox = font.getBoundingBox

    val flipAT = new AffineTransform
    flipAT.translate(0, pdPage.getBBox.getHeight)
    flipAT.scale(1, -1)

    // page may be rotated
    val rotateAT = new AffineTransform
    val rotation = pdPage.getRotation
    val mediaBox = pdPage.getMediaBox

    if (rotation != 0) {
      rotation match {
        case 90 =>
          rotateAT.translate(mediaBox.getHeight, 0)
        case 270 =>
          rotateAT.translate(0, mediaBox.getWidth)
        case 180 =>
          rotateAT.translate(mediaBox.getWidth, mediaBox.getHeight)
        case _ =>
      }
      rotateAT.rotate(Math.toRadians(rotation))
    }

    // advance width, bbox height (glyph space)
    val xadvance = font.getWidth(tP.getCharacterCodes()(0))
    val rect = new Rectangle2D.Float(0, bbox.getLowerLeftY, xadvance, bbox.getHeight)

    // glyph space -> user space
    // note: text.getTextMatrix() is *not* the Text Matrix, it's the Text Rendering Matrix
    val at = tP.getTextMatrix.createAffineTransform
    if (font.isInstanceOf[PDType3Font]) {
      at.concatenate(font.getFontMatrix.createAffineTransform)
    }
    else {
      at.scale(1 / 1000f, 1 / 1000f)
    }
    val s = rotateAT.createTransformedShape(flipAT.createTransformedShape(at.createTransformedShape(rect)))
    Rectangle(
      s.getBounds2D.getMinX.toFloat,
      s.getBounds2D.getMinY.toFloat,
      s.getBounds2D.getMaxX.toFloat,
      s.getBounds2D.getMaxY.toFloat
    )
  }

}
