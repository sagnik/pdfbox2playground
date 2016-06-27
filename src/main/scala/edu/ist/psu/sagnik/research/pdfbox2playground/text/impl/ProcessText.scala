package edu.ist.psu.sagnik.research.pdfbox2playground.text.impl

import java.io.{ByteArrayOutputStream, IOException, OutputStreamWriter}

import edu.ist.psu.sagnik.research.pdfbox2playground.text.model.{PDParagraph, PDTextLine, PDWord}
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.text.{PDFTextStripper, TextPosition}

import scala.collection.JavaConverters._

/**
  * Created by schoudhury on 6/27/16.
  */
class ProcessText extends PDFTextStripper {

  //var allParagraphs = Seq.empty[PDParagraph]
  //var currentParagraph: PDParagraph
  //var currentTextLine: PDTextLine
  //var currentWord: PDWord

  @Override
  @throws[IOException]
  override protected def writeLineSeparator(): Unit = {
    System.out.println("we got a new line")
    super.writeLineSeparator()
  }

  @Override
  @throws[IOException]
  override protected def writeWordSeparator(): Unit = {
    System.out.println("we got a new word")
    super.writeWordSeparator()
  }

  @Override
  @throws[IOException]
  override protected def writeParagraphStart(): Unit = {
    System.out.println("new paragraph started")
    super.writeParagraphStart()
  }

  @Override
  @throws[IOException]
  override protected def writeParagraphEnd(): Unit = {
    System.out.println("last paragraphs ends")
    super.writeParagraphEnd()
  }

  @Override
  @throws[IOException]
  protected def writeString(s: String, tPs: List[TextPosition]): Unit = {
    tPs.foreach(text => println("String[" + text.getXDirAdj + "," + text.getYDirAdj +
      " fs=" + text.getFontSize + " xscale=" + text.getXScale + " height=" + text.getHeightDir +
      " space=" + text.getWidthOfSpace + " width=" + text.getWidthDirAdj + "]" + text.getUnicode))
  }

  def stripPage(pdPage: PDPage): Unit = {
    /*

    PDRectangle cropBox = pdPage.getCropBox();

    // flip y-axis
    flipAT = new AffineTransform();
    flipAT.translate(0, pdPage.getBBox().getHeight());
    flipAT.scale(1, -1);

    // page may be rotated
    rotateAT = new AffineTransform();
    int rotation = pdPage.getRotation();
    if (rotation != 0) {
      PDRectangle mediaBox = pdPage.getMediaBox();
      switch(rotation) {
        case 90:
        rotateAT.translate(mediaBox.getHeight(), 0);
        break;
        case 270:
          rotateAT.translate (0, mediaBox.getWidth());
        break;
        case 180:
          rotateAT.translate (mediaBox.getWidth(), mediaBox.getHeight());
        break;
        default:
          break;
      }
      rotateAT.rotate(Math.toRadians(rotation));
    }

    g2d = image.createGraphics();
    g2d.setStroke(new BasicStroke(0.1f));
    g2d.scale(SCALE, SCALE);

    setStartPage(page + 1);
    setEndPage(page + 1);
*/
    val dummyOutput = new OutputStreamWriter(new ByteArrayOutputStream())
    writeText(document, dummyOutput)
  }
}
