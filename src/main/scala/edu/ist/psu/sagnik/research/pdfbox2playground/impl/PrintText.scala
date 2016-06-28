package edu.ist.psu.sagnik.research.pdfbox2playground.impl

import java.io.File

import edu.ist.psu.sagnik.research.pdfbox2playground.text.impl.ProcessText
import edu.ist.psu.sagnik.research.pdfbox2playground.writer.pdf.CreateMarkedPDF
import org.apache.pdfbox.pdmodel.PDDocument
import java.awt.Color
/**
  * Created by schoudhury on 6/27/16.
  */
object PrintText {

  def main(args:Array[String]):Unit={
    val loc="src/test/resources/pg_0002.pdf"
    var document = PDDocument.load(new File(loc))
    var page = document.getPage(0)
    val pageNum=0
    val paragraphs=new ProcessText().stripPage(pageNum,document)
    //paragraphs.foreach(x=>println(s"content: ${x.content} bb: ${x.bb}"))
    //paragraphs.flatMap(_.tLines).flatMap(_.tWords).flatMap(_.chars).foreach(println)
    CreateMarkedPDF(loc,document,page,paragraphs.flatMap(_.tLines).flatMap(_.tWords).flatMap(_.chars).map(_.bb),Color.BLUE,"chars")
    println("created char marked PDF")
    document = PDDocument.load(new File(loc))
    page = document.getPage(0)
    CreateMarkedPDF(loc,document,page,paragraphs.flatMap(_.tLines).flatMap(_.tWords).map(_.bb),Color.GREEN,"words")
    println("created word marked PDF")
    document = PDDocument.load(new File(loc))
    page = document.getPage(0)
    CreateMarkedPDF(loc,document,page,paragraphs.flatMap(_.tLines).map(_.bb),Color.RED,"lines")
    println("created line marked PDF")
    document = PDDocument.load(new File(loc))
    page = document.getPage(0)
    CreateMarkedPDF(loc,document,page,paragraphs.map(_.bb),Color.CYAN,"paragraphs")
    println("created paragraph marked PDF")
  }
}
