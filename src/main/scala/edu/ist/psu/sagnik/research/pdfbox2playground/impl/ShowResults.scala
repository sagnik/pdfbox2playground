package edu.ist.psu.sagnik.research.pdfbox2playground.impl

import java.io.File

import edu.ist.psu.sagnik.research.pdfbox2playground.model.Rectangle
import edu.ist.psu.sagnik.research.pdfbox2playground.path.impl.ProcessPaths
import edu.ist.psu.sagnik.research.pdfbox2playground.raster.impl.ProcessRaster
import edu.ist.psu.sagnik.research.pdfbox2playground.text.impl.ProcessText
import edu.ist.psu.sagnik.research.pdfbox2playground.writer.pdf.CreateMarkedPDF
import org.apache.pdfbox.pdmodel.PDDocument
import java.awt.Color

import edu.ist.psu.sagnik.research.pdfbox2playground.text.model.PDChar
import edu.ist.psu.sagnik.research.pdfbox2playground.writer.svg.CreateSVG
/**
 * Created by schoudhury on 6/27/16.
 */
object ShowResults {

  def printExtractionResult(pdLoc:String,pageNum:Int,bbs:List[Rectangle],c:Color,qualifier:String)={
    val document = PDDocument.load(new File(pdLoc))
    val page = document.getPage(pageNum)
    CreateMarkedPDF(pdLoc,document,pageNum,page,bbs,c,qualifier)
    println(s"created ${qualifier.substring(0,qualifier.length-1)} marked PDF")
  }

  def main(args:Array[String]):Unit={
    val DEFAULT_LOC="src/test/resources/LoremIpsum.pdf"
    val DEFAULT_PAGE_NUM=0

    val pdLoc=if (args.length >1 ) args(0) else DEFAULT_LOC
    val pageNum=if (args.length ==2 ) args(1).toInt else DEFAULT_PAGE_NUM


    val document = PDDocument.load(new File(pdLoc))
    val page = document.getPage(pageNum)

    val paragraphs=new ProcessText().stripPage(pageNum,document)

    val imFinder=new ProcessRaster(page)
    imFinder.getImages()

    val pathFinder=new ProcessPaths(page)
    pathFinder.getPaths()
    val segments=pathFinder.paths
      .filter(x=> x.doPaint)
      .flatMap(x=>x.subPaths)
      .flatMap(x=>x.segments)

    //TODO: check for comprehensions.
    val chars=paragraphs.flatMap(_.tLines).flatMap(_.tWords).flatMap(_.chars)
    val words=paragraphs.flatMap(_.tLines).flatMap(_.tWords)
    val lines=paragraphs.flatMap(_.tLines)

    //create SVG here
    new CreateSVG().apply(
      chars,
      pdLoc.substring(0,pdLoc.lastIndexOf("."))+"-page-chars-"+pageNum+".svg",
      page.getBBox.getWidth,
      page.getBBox.getHeight
    )
    println("created char SVG")

    printExtractionResult(pdLoc,pageNum,chars.map(_.bb),Color.BLUE,"chars")

    printExtractionResult(pdLoc,pageNum,words.map(_.bb),Color.GREEN,"words")

    printExtractionResult(pdLoc,pageNum,lines.map(_.bb),Color.RED,"lines")

    printExtractionResult(pdLoc,pageNum,paragraphs.map(_.bb),Color.CYAN,"paragraphs")

    printExtractionResult(pdLoc,pageNum,imFinder.rasterImages.map(_.bb),Color.MAGENTA,"rasters")

    printExtractionResult(pdLoc,pageNum,segments.map(_.bb),Color.ORANGE,"paths")



  }
}
