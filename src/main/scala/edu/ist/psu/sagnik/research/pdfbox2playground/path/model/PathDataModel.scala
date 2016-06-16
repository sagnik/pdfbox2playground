package edu.ist.psu.sagnik.research.pdfbox2playground.path.model

/**
  * Created by schoudhury on 6/15/16.
  */

case class Point(x:Float,y:Float)

trait PDPath{
  def isAbsolute:Boolean
  def args:Seq[Any]
  def getBoundingBox[A](lastEndPoint:CordPair, isAbs:Boolean, paths:Seq[Any]):Rectangle
  def getEndPoint[A](lastEndPoint:CordPair, isAbs:Boolean, paths:Seq[Any]):CordPair
}

case class MovePath(eP:Point)
case class LinePath(eP:Point)
case class BCurveCPath(cP1:Point,cP2:Point,eP:Point)
case class BCurveVPath(cP:Point,eP:Point)
case class BCurveYPath(cP:Point,eP:Point)
case class ClosePath()
case class RectPath(sP:Point,width:Float,height:Float)

