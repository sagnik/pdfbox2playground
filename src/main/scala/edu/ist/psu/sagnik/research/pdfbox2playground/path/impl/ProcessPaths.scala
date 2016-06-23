package edu.ist.psu.sagnik.research.pdfbox2playground.path.impl

import java.awt.geom.Point2D
import java.io.IOException

import edu.ist.psu.sagnik.research.pdfbox2playground.path.model._
import org.apache.pdfbox.contentstream.PDFGraphicsStreamEngine
import org.apache.pdfbox.cos.COSName
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.graphics.image.PDImage
import org.apache.pdfbox.util.Matrix

/**
  * Created by schoudhury on 6/22/16.
  */
class ProcessPaths(page:PDPage) extends PDFGraphicsStreamEngine(page:PDPage) {

  var paths:List[PDPath]=List.empty[PDPath]
  var currentPath:Option[PDPath]=None
  var currentSubPath:Option[PDShape]=None
  var currentPoint=new Point2D.Float(0f,0f)
  var numMoves=0
  var numRects=0
  var numLines=0
  var numCurves=0
  var numClosePaths=0

  //def this(page:PDPage)= this(page)

  def getCTM:Matrix = getGraphicsState.getCurrentTransformationMatrix

  def getPaths():Unit=processPage(getPage)

  def fp(p:Point2D):Point2D.Float=new Point2D.Float(p.getX.toFloat,p.getY.toFloat)

  //***** path construction operators *********//

  //moveTo `m` and rectangle `re` can start a path, or appear inside an existing path

  @Override @throws[IOException]
  def appendRectangle(p0: Point2D, p1: Point2D, p2: Point2D, p3: Point2D):Unit={
    numRects+=1
    subPathComplete()
    currentPoint=fp(p3)
    currentSubPath=Some(
      PDShape(
        segments = List(
          PDLine(fp(p0), fp(p1), getCTM),
          PDLine(fp(p1), fp(p2), getCTM),
          PDLine(fp(p2), fp(p3), getCTM),
          PDLine(fp(p3), fp(p1), getCTM)
        )
      )
    )
    (currentPath, currentSubPath) match{
      case (Some(cp),Some(csp)) => currentPath= Some(//a current path exists, add this whole subpath to the subpaths.
        cp.copy(
          subPaths = cp.subPaths :+ csp
        )
      )
        currentSubPath=None
      case (None,Some(csp)) =>currentPath = Some(//this is the beginning of a new path
        PDPath(
          subPaths = List(csp),
          isClip = false,
          doPaint = true,
          windingRule = -1
        )
      )
        currentSubPath=None
      case _ =>

    }
    // subPathComplete()
  }

  @Override @throws[IOException]
  def moveTo(x: Float, y: Float):Unit={
    numMoves+=1
    subPathComplete()
    currentPoint = new Point2D.Float(x,y)
    //we will not create a subpath here. Just a path. Because move will actually not do anything than to
    // start a path and change the current point
    currentPath match{
      case Some (cp) => currentPath = Some(cp)
      case _ => currentPath = Some(//this is the beginning of a new path
        PDPath(
          subPaths = List.empty[PDSubPath],
          isClip = false,
          doPaint = true,
          windingRule = -1
        )
      )

    }

  }

  //if we have a `l` (lineto) operator, the path must have been started already
  //by a `m` or `re` command. We just need to check if the currentSubpath is empty
  //or not.

  @Override @throws[IOException]
  def lineTo(x: Float, y: Float):Unit= {
    numLines+=1
    currentSubPath match{
      case Some(csp) => currentSubPath = Some(
        csp.copy(
          segments = csp.segments :+
            PDLine(currentPoint,new Point2D.Float(x,y),getCTM)
        )
      )
      case _ => currentSubPath = Some(//current sub path is empty. We need to start a new PDShape i.e. subpath
        PDShape(
          segments = List(
            PDLine(currentPoint,new Point2D.Float(x,y),getCTM)
          )
        )
      )
    }
    currentPoint=new Point2D.Float(x,y)
  }

  @Override @throws[IOException]
  def curveTo(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float):Unit={
    numCurves+=1
    currentSubPath match{
      case Some(csp) => currentSubPath = Some(
        csp.copy(
          segments = csp.segments :+
            PDCurve(
              startPoint = currentPoint,
              endPoint = new Point2D.Float(x3,y3),
              controlPoint1 = new Point2D.Float(x1,y1),
              controlPoint2 = new Point2D.Float(x2,y2),
              ctm=getCTM
            )
        )
      )
      case _ => currentSubPath = Some(//current sub path is empty. We need to start a new PDShape i.e. subpath
        PDShape(
          segments = List(
            PDCurve(
              startPoint = currentPoint,
              endPoint = new Point2D.Float(x3,y3),
              controlPoint1 = new Point2D.Float(x1,y1),
              controlPoint2 = new Point2D.Float(x2,y2),
              ctm=getCTM
            )
          )
        )
      )
    }
    currentPoint=new Point2D.Float(x3,y3)
  }

  @Override @throws[IOException]
  def getCurrentPoint: Point2D.Float = currentPoint

  //close path (`h`) closes a **sub**path by appending a line from the current point to the start point.
  //Obviously, currentSubPath.segments should not be empty if we see this operator.
  //TODO: Handle case where currentSubPath.segments is empty
  @Override @throws[IOException]
  def closePath():Unit = {
    numClosePaths+=1
    currentSubPath match{
      case Some(csp) => {
        val startPoint = csp.segments.head.startPoint
        currentSubPath= Some(
          csp.copy(
            segments = csp.segments :+ PDLine(currentPoint, startPoint, getCTM)
          )
        )
        currentPoint = startPoint
        subPathComplete()
      }
      case _ => System.err.println("A path encountered a close operator before it even started. " +
        "It will henceforth be known as Rickon Stark Blvd.") //should never reach here

    }

  }

  //this method will `complete` the current sub path, i.e., will add it to the current path and
  // mark the current subpath as None
  def subPathComplete():Unit= {
    (currentPath, currentSubPath) match {
      case (Some(cp),Some(csp)) =>
        currentPath =
          Some(
            cp.copy(
              subPaths = cp.subPaths :+ csp
            )
          )
      case _ =>
    }
    currentSubPath=None
  }



  @Override @throws[IOException]
  //TODO: Revisit, this might be important from the clipping perspective
  def endPath():Unit= {currentPath = None}

  //***** path painting operators *********//
  //TODO: figure out what to do with different kinds of winding rules and shading patterns

  @Override @throws[IOException]
  def strokePath():Unit  = {
    //println("in strokepath")
    subPathComplete()
    currentPath match{
      case Some(cp) => paths=paths :+ cp
      case _ => System.err.println("Stroke Path operator encountered for empty path")
    }
    currentPath=None
  }

  @Override @throws[IOException]
  def fillPath(windingRule:Int):Unit = {
    //println("in fillpath")
    subPathComplete()
    currentPath match{
      case Some(cp) => paths=paths :+ cp.copy(windingRule=windingRule)
      case _ => System.err.println("Fill Path operator encountered for empty path")
    }
    currentPath=None
  }

  @Override @throws[IOException]
  def fillAndStrokePath(windingRule:Int):Unit = {
    //println("in fill and strokepath")
    subPathComplete()
    currentPath match{
      case Some(cp) => paths=paths :+ cp.copy(windingRule=windingRule)
      case _ => System.err.println("Fill and Stroke Path operator encountered for empty path")
    }
    currentPath=None
  }

  @Override @throws[IOException]
  def shadingFill(shadingName: COSName):Unit = {
    //println("in shading fill")
    subPathComplete()
    currentPath match{
      case Some(cp) => paths=paths :+ cp
      case _ => System.err.println("Shading Fill Path operator encountered for empty path")
    }
    currentPath=None
  }

  //***** path clipping operators *********//
  @Override @throws[IOException]
  def clip(windingRule:Int):Unit = {
    //println("in clip")
    subPathComplete()
    currentPath match{
      case Some(cp) => paths=paths :+ cp.copy(windingRule=windingRule,isClip = true)
      case _ => System.err.println("Clip path operator encountered for empty path")
    }

  }



  @Override @throws[IOException]
  def drawImage(pdImage: PDImage):Unit= {}


}
