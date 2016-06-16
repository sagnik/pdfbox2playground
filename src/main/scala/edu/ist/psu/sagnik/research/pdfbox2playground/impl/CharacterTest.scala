package edu.ist.psu.sagnik.research.pdfbox2playground.impl

import java.io.File

import edu.ist.psu.sagnik.research.pdfbox2playground.model.PDChar
import org.apache.pdfbox.pdmodel.font.PDFont
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage}
import org.apache.pdfbox.text.PDFTextStripper
import org.apache.pdfbox.util.{Matrix, Vector}

import scala.collection.JavaConverters._
/**
 * Created by sagnik on 4/4/16.
 */
class CharacterStripper extends PDFTextStripper{

  def apply(pdfLoc:String):List[PDChar]={
    val document=PDDocument.load(new File(pdfLoc))
    super.setSortByPosition(true)
    document.getDocumentCatalog.getPages.iterator.asScala.toList.flatMap(x=>pdCharsFromPage(x))
  }

  def pdCharsFromPage(page:PDPage):List[PDChar]={
    List.empty[PDChar]
  }

  override protected def showGlyph(textRenderingMatrix: Matrix, font: PDFont, code: Int, unicode: String, displacement: Vector):Unit={

  }

}
