package edu.ist.psu.sagnik.research.pdfbox2playground.impl

import java.io.File

import edu.ist.psu.sagnik.research.pdfbox2playground.javatest.{ClipPathFinder, LinePathFinder}
import edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Segment
import org.apache.pdfbox.pdmodel.PDDocument

import scala.collection.JavaConverters._

/**
  * Created by schoudhury on 6/16/16.
  */
object ShowLinePaths {

  def main(args: Array[String]):Unit={
    //val loc="src/test/resources/pdf_reference_1-7.pdf"
    val loc="src/test/resources/test.pdf"
    val document = PDDocument.load(new File(loc));
    val page = document.getPage(5);
    val finder = new LinePathFinder(page);
    finder.findLinePaths();
    //finder.iterator.asScala.toList.foreach(x=>println(x))
    val segments=finder.iterator.asScala.toList.flatMap(x=>x.getSubPaths.asScala.toList)
    .flatMap(x=>x.iterator().asScala.toList)
    segments.foreach(x=>println(x.getStart,x.getEnd,x.getClass))
    CreateSVG(segments,"src/test/resources/test-page-6.svg",width=page.getMediaBox.getHeight,height=page.getMediaBox.getHeight)
    println("written SVG paths")
    document.close();
  }

}
