package edu.ist.psu.sagnik.research.pdfbox2playground.impl

import java.io.File

import edu.ist.psu.sagnik.research.pdfbox2playground.javatest.{ClipPathFinder, LinePathFinder}
import org.apache.pdfbox.pdmodel.PDDocument

import scala.collection.JavaConverters._

/**
  * Created by schoudhury on 6/16/16.
  */
object ShowClipPaths {

  def main(args: Array[String]):Unit={
    val loc="src/test/resources/pdf_reference_1-7.pdf"
    val document = PDDocument.load(new File(loc));
    val page = document.getPage(6);
    val finder = new LinePathFinder(page);
    finder.findLinePaths();
    finder.iterator.asScala.toList.foreach(x=>println(x))
    document.close();
  }

}
