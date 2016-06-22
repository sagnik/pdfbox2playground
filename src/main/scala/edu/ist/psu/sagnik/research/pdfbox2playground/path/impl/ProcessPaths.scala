package edu.ist.psu.sagnik.research.pdfbox2playground.path.impl

import java.awt.geom.Point2D
import java.io.IOException

import edu.ist.psu.sagnik.research.pdfbox2playground.model.Rectangle
import edu.ist.psu.sagnik.research.pdfbox2playground.path.model._
import org.apache.pdfbox.contentstream.PDFGraphicsStreamEngine
import org.apache.pdfbox.pdmodel.PDPage
import org.apache.pdfbox.pdmodel.graphics.image.PDImage

/**
  * Created by schoudhury on 6/22/16.
  */
class ProcessPaths(page:PDPage) extends PDFGraphicsStreamEngine(page:PDPage) {

  var paths=List.empty[Path]
  var currentPath:Option[Path]=None
  /*
      Path(
      currentPoint=new Point2D.Float(0f,0f),
      subPaths = List.empty[SubPath],
      isClip = ???,
      doPaint = ???,
      windingRule = ???
    )
  */
  def this(page:PDPage)=this(page)

  def getPaths():Unit=processPage(getPage)

  def fp(p:Point2D):Point2D.Float=new Point2D.Float(p.getX.toFloat,p.getY.toFloat)

  @Override @throws[IOException]
  def appendRectangle(p0: Point2D, p1: Point2D, p2: Point2D, p3: Point2D):Unit={
    currentPath match{
      case Some(cp) => currentPath=Some( //a current path exists, add this rectangle to the subpaths.
        cp.copy(
          subPaths = cp.subPaths :+
            PDRect(
              segments= List(
                Line(fp(p0),fp(p1),getGraphicsState.getCurrentTransformationMatrix),
                Line(fp(p1),fp(p2),getGraphicsState.getCurrentTransformationMatrix),
                Line(fp(p2),fp(p3),getGraphicsState.getCurrentTransformationMatrix),
                Line(fp(p3),fp(p1),getGraphicsState.getCurrentTransformationMatrix)
              )
            ),
          currentPoint = fp(p3)
        )
      )
      case _ => currentPath = Some(   //this is the beginning of a new path
        Path(
          currentPoint=fp(p3),
          subPaths=List(
            PDRect(
              segments= List(
                Line(fp(p0),fp(p1),getGraphicsState.getCurrentTransformationMatrix),
                Line(fp(p1),fp(p2),getGraphicsState.getCurrentTransformationMatrix),
                Line(fp(p2),fp(p3),getGraphicsState.getCurrentTransformationMatrix),
                Line(fp(p3),fp(p1),getGraphicsState.getCurrentTransformationMatrix)
              )
            )
          ),
          isClip = false,
          doPaint = true,
          windingRule = -1

        )
      )
    }
  }

  @Override @throws[IOException]
  def moveTo(x: Float, y: Float)={
    currentPath match{
      case Some (cp) => currentPath = Some(
        cp.copy(
          currentPoint = new Point2D.Float(x,y)
        )
      )
      case _ => currentPath = Some(   //this is the beginning of a new path
        Path(
          currentPoint=new Point2D.Float(x,y),
          subPaths=List.empty[SubPath],
          isClip = false,
          doPaint = true,
          windingRule = -1

        )
      )
    }
  }

  @Override @throws[IOException]
  def lineTo(x: Float, y: Float) {
    currentPath match{
      case Some(cp) => currentPath = Some(
        cp.copy(
          subPaths =
            if (cp.subPaths.isEmpty)
              List(
                PDShape(
                  segments= List(Line(cp.currentPoint,new Point2D.Float(x,y),getGraphicsState.getCurrentTransformationMatrix))
                )
              )
            else
              cp.subPaths:+(cp.subPaths.last.segments :+
        )
      )
    }
  }

  @throws[IOException]
  def curveTo(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float) {
    currentPath.curveTo(x1, y1, x2, y2, x3, y3, getGraphicsState.getCurrentTransformationMatrix)
  }

  @Override @throws[IOException]
  def clip(windingRule: Int) { currentPath=currentPath.copy(windingRule=windingRule)}



  @Override @throws[IOException]
  def drawImage(pdImage: PDImage)= {}

  @Override @throws[IOException]
  def fillAndStrokePath(windingRule: Int):Unit= {paths=paths:+currentPath}


}
