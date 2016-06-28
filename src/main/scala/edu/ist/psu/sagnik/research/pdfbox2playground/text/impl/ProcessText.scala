package edu.ist.psu.sagnik.research.pdfbox2playground.text.impl

import java.io.{ByteArrayOutputStream, IOException, OutputStreamWriter}
import java.util

import edu.ist.psu.sagnik.research.pdfbox2playground.model.Rectangle
import edu.ist.psu.sagnik.research.pdfbox2playground.text.model.{PDChar, PDParagraph, PDTextLine, PDWord}
import org.apache.pdfbox.pdmodel.{PDDocument, PDPage}
import org.apache.pdfbox.text.{PDFTextStripper, TextPosition}

import scala.collection.JavaConverters._
import scala.xml.Document

/**
  * Created by schoudhury on 6/27/16.
  */
class ProcessText extends PDFTextStripper {

  var currentParagraphs = List.empty[PDParagraph]
  var currentTextLines=List.empty[PDTextLine]
  var currentWords=List.empty[PDWord]
  var currentChars=List.empty[PDChar]

  @Override
  @throws[IOException]
  override protected def writeWordSeparator(): Unit = {
    //println("word: "+currentChars.map(x=>(x.content,Rectangle.asCoordinatesStr(x.bb))))
    currentWords=currentWords :+ PDWord(
      content = currentChars.foldLeft("")((a,b)=>a+b.content),
      chars = currentChars,
      bb=CalculateBB(currentChars)
    )
    //println("word bb: "+CalculateBB(currentChars))
    currentChars=List.empty[PDChar]
    super.writeWordSeparator()
  }

  @Override
  @throws[IOException]
  override protected def writeLineSeparator(): Unit = {
    this.writeWordSeparator()
    //println("line: "+currentWords.map(a=>(a.bb,a.content)))
    currentTextLines=currentTextLines :+ PDTextLine(
      content = currentWords.foldLeft("")((a,b)=>a+b.content+" "),
      tWords = currentWords,
      bb=CalculateBB(currentWords)
    )
    //println("line bb: "+CalculateBB(currentWords))
    currentWords=List.empty[PDWord]
    super.writeLineSeparator()
  }

  @Override
  @throws[IOException]
  override protected def writeParagraphEnd(): Unit = {
    //this.writeLineSeparator()
    //println("paragraph: "+currentTextLines.map(a=>(a.bb,a.content)))
    currentParagraphs=currentParagraphs :+ PDParagraph(
      content = currentTextLines.foldLeft("")((a,b)=>a+b.content+"\n"),
      tLines = currentTextLines,
      bb=CalculateBB(currentTextLines)
    )
    println("paragraph bb: "+CalculateBB(currentTextLines))
    currentTextLines=List.empty[PDTextLine]
    super.writeParagraphEnd()
  }

  @Override
  @throws[IOException]
  override protected def writeString(s: String, textPositions: util.List[TextPosition]): Unit = {
    val tPs=textPositions.asScala.toList
    tPs.foreach(tP=>{
      currentChars=currentChars :+ PDChar(
        content = tP.getUnicode,
        bb = Rectangle(
          tP.getXDirAdj,
          (tP.getYDirAdj - tP.getHeightDir),
          tP.getXDirAdj+tP.getWidthDirAdj,
          tP.getYDirAdj//(tP.getYDirAdj - tP.getHeightDir)+tP.getHeightDir
        ),
        font = tP.getFont
      )
    })
    /*
    tPs.foreach(text => println("String[" + text.getXDirAdj + "," + text.getYDirAdj +
      " fs=" + text.getFontSize + " xscale=" + text.getXScale + " height=" + text.getHeightDir +
      " space=" + text.getWidthOfSpace + " width=" + text.getWidthDirAdj + "]" + text.getUnicode))
      */

  }

  def stripPage(pdPageNum: Int, document: PDDocument): List[PDParagraph] = {
    setStartPage(pdPageNum + 1);
    setEndPage(pdPageNum + 1);
    val dummyOutput = new OutputStreamWriter(new ByteArrayOutputStream())
    writeText(document, dummyOutput)
    currentParagraphs
  }
}
