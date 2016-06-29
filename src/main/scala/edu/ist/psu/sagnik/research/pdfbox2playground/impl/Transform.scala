package edu.ist.psu.sagnik.research.pdfbox2playground.impl

import java.awt.geom.Point2D

import edu.ist.psu.sagnik.research.pdfbox2playground.path.model.{PDCurve, PDLine, PDSegment}
import org.apache.pdfbox.util.Matrix

/**
  * Created by schoudhury on 6/23/16.
  */

//see page 208 in PDF spec 1.7

object Transform {
  /*
  def apply(pds:PDSegment):PDSegment= {
    val a=pds.ctm.getValue(0,0)
    val b=pds.ctm.getValue(0,1)
    val c=pds.ctm.getValue(1,0)
    val d=pds.ctm.getValue(1,1)
    val e=pds.ctm.getValue(2,1)
    val f=pds.ctm.getValue(2,2)
    pds match {
      case pds: PDLine => PDLine(
        startPoint = new Point2D.Float(a*pds.startPoint.x+c*pds.startPoint.y+e,b*pds.startPoint.x+d*pds.startPoint.y+f),
        endPoint =  new Point2D.Float(a*pds.endPoint.x+c*pds.endPoint.y+e,b*pds.endPoint.x+d*pds.endPoint.y+f),
        ctm=new Matrix() //this is now changed to a unit matrix because transformations are done
      )

      case pds: PDCurve => PDCurve(
        startPoint = new Point2D.Float(a*pds.startPoint.x+c*pds.startPoint.y+e,b*pds.startPoint.x+d*pds.startPoint.y+f),
        endPoint =  new Point2D.Float(a*pds.endPoint.x+c*pds.endPoint.y+e,b*pds.endPoint.x+d*pds.endPoint.y+f),
        controlPoint1 = new Point2D.Float(a*pds.controlPoint1.x+c*pds.controlPoint1.y+e,b*pds.controlPoint1.x+d*pds.controlPoint1.y+f),
        controlPoint2 = new Point2D.Float(a*pds.controlPoint2.x+c*pds.controlPoint2.y+e,b*pds.controlPoint2.x+d*pds.controlPoint2.y+f),
        ctm=new Matrix()
      )
    }
  }
*/
}
