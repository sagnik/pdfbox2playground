package edu.ist.psu.sagnik.research.pdfbox2playground.impl

import java.io.File

import edu.ist.psu.sagnik.research.pdfbox2playground.javatest.{ClipPathFinder, LinePathFinder}
import edu.ist.psu.sagnik.research.pdfbox2playground.javatest.Segment
import edu.ist.psu.sagnik.research.pdfbox2playground.path.impl.ProcessPaths
import edu.ist.psu.sagnik.research.pdfbox2playground.path.model
import edu.ist.psu.sagnik.research.pdfbox2playground.path.model.{PDCurve, PDLine}
import edu.ist.psu.sagnik.research.pdfbox2playground.writer.pdf.CreateMarkedPDF
import edu.ist.psu.sagnik.research.pdfbox2playground.writer.svg.CreateSVG
import org.apache.pdfbox.pdmodel.PDDocument
import java.awt.Color

import scala.collection.JavaConverters._

/**
  * Created by schoudhury on 6/16/16.
  */
object ShowLinePaths {

  def main(args: Array[String]):Unit={
    //val loc="src/test/resources/test1-p08.pdf"
    val loc="/home/sagnik/Documents/nitrodocs/Purchase_Order_Nitro_EMEA_USD_July_2015.pdf"
    //val loc="/home/sagnik/Documents/nitrodocs/cardholder_statement_of_dispute.pdf"

    val document = PDDocument.load(new File(loc));
    val pageNo=0
    val page = document.getPage(pageNo)//7
    //val page = document.getPage(5)

    val finder1=new ProcessPaths(page)
    finder1.getPaths()

    //val finder2 = new LinePathFinder(page);
    //finder2.findLinePaths();



    val segments1=finder1.paths
      .filter(x=> x.doPaint)
      .flatMap(x=>x.subPaths)
      .flatMap(x=>x.segments)
      //.map(x=>Transform(x))

    //segments1.foreach(x=>println(x.startPoint,x.endPoint,x.ctm))

    //val lines1=segments1.filter(x=>x.isInstanceOf[PDLine])
    //val curves1=segments1.filter(x=>x.isInstanceOf[PDCurve])

   //CreateSVG.fromPath(finder1.paths.filter(x=> x.doPaint),loc.substring(0,loc.length-4)+"-page-"+pageNo+"-paths.svg",width=page.getMediaBox.getWidth,height=page.getMediaBox.getHeight)
    CreateMarkedPDF(loc,document,pageNo,page,segments1.map(_.bb),Color.RED,"paths")
    println(s"marked PDF paths, ${segments1.length} line segments")

    document.close();
  }

}
