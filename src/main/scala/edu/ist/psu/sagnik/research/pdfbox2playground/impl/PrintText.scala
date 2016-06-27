package edu.ist.psu.sagnik.research.pdfbox2playground.impl

import java.io.File

import edu.ist.psu.sagnik.research.pdfbox2playground.text.impl.ProcessText
import org.apache.pdfbox.pdmodel.PDDocument

/**
  * Created by schoudhury on 6/27/16.
  */
object PrintText {

  def main(args:Array[String]):Unit={
    val loc="src/test/resources/test1.pdf"
    val document = PDDocument.load(new File(loc));
    val page = document.getPage(7)
    new ProcessText().stripPage(page)
  } 
}
