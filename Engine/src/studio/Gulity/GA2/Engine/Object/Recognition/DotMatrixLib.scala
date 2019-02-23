package studio.Gulity.GA2.Engine.Object.Recognition

import scala.collection.mutable

abstract class DotMatrixLib {
  private val Map = new mutable.HashMap[String, (String, String, String)]

  def Dev(DevName: String, DevType: String, DevModel: String, DevID: String) = Map.put(DevName, (DevType, DevModel, DevID))

  def G(DevName: String) = Map.get(DevName)

}

final class DotMatrixGroup(val DotMatrixSamples: Array[DotMatrixSample]) {
  for (i <- 1 to DotMatrixSamples.length) {
    if (DotMatrixSamples(i).Height != DotMatrixSamples(0).Height || DotMatrixSamples(i).Width != DotMatrixSamples(0).Width)
      throw new Exception
  }

}

final class DotMatrixSample(val Name: String, Dots: Array[Array[Boolean]]) extends DotMatrix(Dots) {

}