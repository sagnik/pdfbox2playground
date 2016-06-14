package edu.ist.psu.sagnik.research.pdfbox2playground.impl

import java.io.File

import edu.ist.psu.sagnik.research.pdfbox2playground.model.PDChar
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage}

import scala.collection.JavaConverters._
/**
 * Created by sagnik on 4/4/16.
 */
object CharacterExtraction{
  def apply(pdfLoc:String):List[PDChar]={
    val document=PDDocument.load(new File(pdfLoc))
    document.getDocumentCatalog.getPages.iterator.asScala.toList.flatMap(x=>pdCharsFromPage(x))
  }

  def pdCharsFromPage(page:PDPage):List[PDChar]={
    List.empty[PDChar]


  }

}
