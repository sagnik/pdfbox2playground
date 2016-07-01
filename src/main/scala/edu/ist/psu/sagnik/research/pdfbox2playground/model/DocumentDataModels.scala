package edu.ist.psu.sagnik.research.pdfbox2playground.model

import edu.ist.psu.sagnik.research.pdfbox2playground.path.model.PDPath
import edu.ist.psu.sagnik.research.pdfbox2playground.raster.model.PDRasterImage
import edu.ist.psu.sagnik.research.pdfbox2playground.text.model.PDParagraph

/**
  * Created by schoudhury on 7/1/16.
  */

case class PDPageSimple(pNum:Int,
                        paragraphs:List[PDParagraph],
                        gPaths:List[PDPath],
                        rasters:List[PDRasterImage],
                        bb:Rectangle
                       )

case class PDDocumentSimple(pages:List[PDPageSimple])