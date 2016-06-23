package edu.ist.psu.sagnik.research.pdfbox2playground.impl

import java.io.File

import edu.ist.psu.sagnik.research.pdfbox2playground.javatest.{ClipPathFinder, LinePathFinder}
import edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Segment
import edu.ist.psu.sagnik.research.pdfbox2playground.path.impl.ProcessPaths
import edu.ist.psu.sagnik.research.pdfbox2playground.path.model.{PDCurve, PDLine}
import edu.ist.psu.sagnik.research.pdfbox2playground.writer.svg.CreateSVG
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


/*
    val finder = new LinePathFinder(page);
    finder.findLinePaths();

    val segments=finder.iterator.asScala.toList.flatMap(x=>x.getSubPaths.asScala.toList)
    .flatMap(x=>x.iterator().asScala.toList)

    segments.foreach(x=>println(x.getStart,x.getEnd,x.getCTM))

    val lines=segments.filter(x=>x.isInstanceOf[edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Line])
    val curves=segments.filter(x=>x.isInstanceOf[edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Curve])

    println(s"Segments length: ${segments.length}, lines: ${lines.length}, curves: ${curves.length}")
    println(s"number of moves: ${finder.numMoves}, rects: ${finder.numRects}, lines: ${finder.numLines}, curves: ${finder.numCurves}  ")

    CreateSVG(segments,"src/test/resources/test-page-5.svg",width=page.getMediaBox.getHeight,height=page.getMediaBox.getHeight)
    println("written SVG paths")
*/

    val finder1=new ProcessPaths(page)
    finder1.getPaths()

    val finder2 = new LinePathFinder(page);
    finder2.findLinePaths();



    val segments1=finder1.paths
      .filter(x=> !x.isClip)
      .flatMap(x=>x.subPaths)
      .flatMap(x=>x.segments)
      //.map(x=>Transform(x))

    //segments1.foreach(x=>println(x.startPoint,x.endPoint,x.ctm))

    val lines1=segments1.filter(x=>x.isInstanceOf[PDLine])
    val curves1=segments1.filter(x=>x.isInstanceOf[PDCurve])

    segments1.foreach(x=>println(x.startPoint,x.endPoint,x.ctm))

//    val segments2=finder2.iterator.asScala.toList.flatMap(x=>x.getSubPaths.asScala.toList)
//      .flatMap(x=>x.iterator().asScala.toList)
//
//    val lines2=segments2.filter(x=>x.isInstanceOf[edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Line])
//    val curves2=segments2.filter(x=>x.isInstanceOf[edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Curve])
//
//
//    println(s"1[Scala]: Segments length: ${segments1.length}, lines: ${lines1.length}, curves: ${curves1.length}")
//    println(s"2[Java]: Segments length: ${segments2.length}, lines: ${lines2.length}, curves: ${curves2.length}")
//
//
//    println(finder1.paths.filter(x=> !x.isClip).length,finder2.iterator.asScala.toList.length)

    CreateSVG(segments1,"src/test/resources/test-page-5.svg",width=page.getMediaBox.getHeight,height=page.getMediaBox.getHeight,fromScala=true)
    println("written SVG paths")

    document.close();
  }

}
