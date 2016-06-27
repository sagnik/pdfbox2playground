package edu.ist.psu.sagnik.research.pdfbox2playground.text.model

import javafx.geometry.BoundingBox

import org.apache.pdfbox.pdmodel.font.PDFont

/**
  * Created by schoudhury on 6/27/16.
  */
case class PDChar(content:Char,bb:BoundingBox,font:PDFont)