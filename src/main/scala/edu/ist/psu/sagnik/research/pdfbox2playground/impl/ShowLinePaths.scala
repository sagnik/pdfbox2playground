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
    val page = document.getPage(4)
    //val page = document.getPage(5)


//    val finder = new ClipPathFinder(page);
//    finder.findClipPaths();
//    finder.iterator.asScala.toList.foreach(x=>{println(x.getClass);println(x)})


    val finder = new LinePathFinder(page);
    finder.findLinePaths();
    //finder.iterator.asScala.toList.foreach(x=>{println(x.getClass);println(x)})


    val segments=finder.iterator.asScala.toList.flatMap(x=>x.getSubPaths.asScala.toList)
    .flatMap(x=>x.iterator().asScala.toList)

    segments.foreach(x=>println(x.getStart,x.getEnd,x.getClass))
    val lines=segments.filter(x=>x.isInstanceOf[edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Line])
    val curves=segments.filter(x=>x.isInstanceOf[edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Curve])

    println(s"Segments length: ${segments.length}, lines: ${lines.length}, curves: ${curves.length}")
    CreateSVG(segments,"src/test/resources/test-page-5.svg",width=page.getMediaBox.getHeight,height=page.getMediaBox.getHeight)
    println("written SVG paths")


    document.close();
  }

}
