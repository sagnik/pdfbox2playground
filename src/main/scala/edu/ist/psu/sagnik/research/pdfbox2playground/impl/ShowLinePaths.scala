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
    val loc="src/test/resources/test1.pdf"
    val document = PDDocument.load(new File(loc));
    val page = document.getPage(7)
    //val page = document.getPage(5)

    val finder1=new ProcessPaths(page)
    finder1.getPaths()

    //val finder2 = new LinePathFinder(page);
    //finder2.findLinePaths();



    val segments1=finder1.paths
      .filter(x=> !x.isClip)
      .flatMap(x=>x.subPaths)
      .flatMap(x=>x.segments)
      //.map(x=>Transform(x))

    //segments1.foreach(x=>println(x.startPoint,x.endPoint,x.ctm))

    val lines1=segments1.filter(x=>x.isInstanceOf[PDLine])
    val curves1=segments1.filter(x=>x.isInstanceOf[PDCurve])

   CreateSVG(segments1,"src/test/resources/test-page-5.svg",width=page.getMediaBox.getWidth,height=page.getMediaBox.getHeight,fromScala=true)
    println("written SVG paths")

    document.close();
  }

}
