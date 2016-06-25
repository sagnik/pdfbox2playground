package edu.ist.psu.sagnik.research.pdfbox2playground.path.impl

import java.awt.Color
import java.awt.geom.Point2D
import java.io.IOException

import edu.ist.psu.sagnik.research.pdfbox2playground.path.model._
import org.apache.pdfbox.contentstream.PDFGraphicsStreamEngine
import org.apache.pdfbox.cos.COSName
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.graphics.color.PDColor
import org.apache.pdfbox.pdmodel.graphics.image.PDImage
import org.apache.pdfbox.util.Matrix
import scala.collection.JavaConverters._

/**
  * Created by schoudhury on 6/22/16.
  */
class ProcessPaths(page:PDPage) extends PDFGraphicsStreamEngine(page:PDPage) {

  var paths:List[PDPath]=List.empty[PDPath]
  var currentPath:Option[PDPath]=None
  var currentSubPath:Option[PDShape]=None
  var currentPoint=new Point2D.Float(0f,0f)

  def getCTM:Matrix = this.getGraphicsState.getCurrentTransformationMatrix

  def getPaths():Unit=processPage(getPage)

  def fp(p:Point2D):Point2D.Float=new Point2D.Float(p.getX.toFloat,p.getY.toFloat)

  /*
  case class PathStyle(
                      fill:Option[String],
                      fillRule:Option[String],
                      fillOpacity:Option[String],
                      stroke:Option[String],
                      strokeWidth:Option[String],
                      strokeLineCap:Option[String],
                      strokeLineJoin:Option[String],
                      strokeMiterLimit:Option[String],
                      strokeDashArray:Option[String],
                      strokeDashOffset:Option[String],
                      strokeOpacity:Option[String]
                    )

   */

  //TODO:possible exception?
  def getHexRGB(color: PDColor):String={
    val rgb=color.toRGB //this is packed RGB
    //http://stackoverflow.com/questions/4801366/convert-rgb-values-into-integer-pixel
    val r = (rgb >> 16) & 0xFF
    val g = (rgb >> 8) & 0xFF
    val b = rgb & 0xFF

    f"#${r}%02x${g}%02x${b}%02x"
  }

  def getPathStyle():PathStyle={
    //See https://www.w3.org/TR/SVG/painting.html#FillOpacityProperty for fillRule.
    PathStyle(
      fill=Some(getHexRGB(getGraphicsState.getNonStrokingColor)),
      fillRule=None,
      fillOpacity=None,//Some("1"),
      stroke=Some(getHexRGB(getGraphicsState.getStrokingColor)),
      strokeWidth=Some((getGraphicsState.getLineWidth*getGraphicsState.getCurrentTransformationMatrix.getScaleX).toString), //TODO: this is kind of a hack. Need
     //to look more into how scaling affects line width
      strokeLineCap=getGraphicsState.getLineCap match{
        case 0 => Some("butt")
        case 1 => Some("round")
        case 2 => Some("square")
        case _ => None
      },
      strokeLineJoin=getGraphicsState.getLineJoin match{
        case 0 => Some("miter")
        case 1 => Some("round")
        case 2 => Some("bevel")
        case _ => None
      },

      strokeMiterLimit=Some(getGraphicsState.getMiterLimit.toString),
      strokeDashArray= if (getGraphicsState.getLineDashPattern.getDashArray().length==2)
        Some(getGraphicsState.getLineDashPattern.getDashArray().toList(0)+", "+getGraphicsState.getLineDashPattern.getDashArray().toList(1))
        else None,
      strokeDashOffset=Some(getGraphicsState.getLineDashPattern.getPhase.toString), //TODO:check
      strokeOpacity=None
    )

  }
  //***** path construction operators *********//

  //moveTo `m` and rectangle `re` can start a path, or appear inside an existing path

  @Override @throws[IOException]
  def appendRectangle(p0: Point2D, p1: Point2D, p2: Point2D, p3: Point2D):Unit={
    //numRects+=1
    subPathComplete()
    currentPoint=fp(p3)
    currentSubPath=Some(
      PDShape(
        segments = List(
          PDLine(fp(p0), fp(p1), getCTM),
          PDLine(fp(p1), fp(p2), getCTM),
          PDLine(fp(p2), fp(p3), getCTM),
          PDLine(fp(p3), fp(p0), getCTM)
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
          windingRule = -1,
          pathStyle=getPathStyle()
        )
      )
        currentSubPath=None
      case _ =>

    }
    // subPathComplete()
  }

  @Override @throws[IOException]
  def moveTo(x: Float, y: Float):Unit={
    //numMoves+=1
    subPathComplete()
    currentPoint = new Point2D.Float(x,y)
    //we will not create a subpath here. Just a path. Because move will actually not do anything than to
    // start a path and change the current point
    currentPath match{
      case Some (cp) => currentPath = Some(cp)
      case _ => currentPath = Some(//this is the beginning of a new path
        PDPath(
          subPaths = List.empty[PDShape],
          isClip = false,
          doPaint = true,
          windingRule = -1,
          getPathStyle()
        )
      )
    }

  }

  //if we have a `l` (lineto) operator, the path must have been started already
  //by a `m` or `re` command. We just need to check if the currentSubpath is empty
  //or not.

  @Override @throws[IOException]
  def lineTo(x: Float, y: Float):Unit= {
    //numLines+=1
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
    //numCurves+=1
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
    //numClosePaths+=1
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
  //TODO: see page 230 in PDF standards. Many painting commands close the path as well.

  @Override @throws[IOException]
  def strokePath():Unit  = {
    subPathComplete()
    currentPath match{
      case Some(cp) => paths=paths :+ cp.copy(pathStyle = cp.pathStyle.copy(fill=None))
      case _ => System.err.println("Stroke Path operator encountered for empty path")
    }
    currentPath=None
  }

  @Override @throws[IOException]
  def fillPath(windingRule:Int):Unit = {
    subPathComplete()
    currentPath match{
      case Some(cp) => paths= windingRule match {
        case 0 => paths :+ cp.copy (windingRule = windingRule, pathStyle = cp.pathStyle.copy (fillRule = Some ("evenodd"), stroke=None))
        case 1 => paths :+ cp.copy (windingRule = windingRule, pathStyle = cp.pathStyle.copy (fillRule = Some ("nonzero"), stroke =None))
      }
      case _ => System.err.println("Fill Path operator encountered for empty path")
    }
    currentPath=None
  }

  @Override @throws[IOException]
  def fillAndStrokePath(windingRule:Int):Unit = {
    subPathComplete()
    currentPath match{
      case Some(cp) => paths= windingRule match {
        case 0 => paths :+ cp.copy (windingRule = windingRule, pathStyle = cp.pathStyle.copy (fillRule = Some ("evenodd"), stroke=None))
        case 1 => paths :+ cp.copy (windingRule = windingRule, pathStyle = cp.pathStyle.copy (fillRule = Some ("nonzero"), stroke =None))
      }
      case _ => System.err.println("Fill and Stroke Path operator encountered for empty path")
    }
    currentPath=None
  }

  @Override @throws[IOException]
  def shadingFill(shadingName: COSName):Unit = {
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
    subPathComplete()
    currentPath match{
      case Some(cp) => paths=paths :+ cp.copy(windingRule=windingRule,isClip = true)
      case _ => System.err.println("Clip path operator encountered for empty path")
    }

  }



  @Override @throws[IOException]
  def drawImage(pdImage: PDImage):Unit= {}


}
