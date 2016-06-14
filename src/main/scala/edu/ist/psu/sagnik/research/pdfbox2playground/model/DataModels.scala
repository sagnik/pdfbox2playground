package edu.ist.psu.sagnik.research.pdfbox2playground.model

import org.apache.pdfbox.pdmodel.font.PDFont


/**
  * Created by schoudhury on 6/14/16.
  */

case class PDChar(content:Char,bb:Rectangle,font:PDFont)
case class PDWord(pdChars:List[PDChar],bb:Rectangle)
case class PDTextLine(pdWords:List[PDWord],bb:Rectangle)
case class PDParagraph(pdTLines:List[PDTextLine],bb:Rectangle)