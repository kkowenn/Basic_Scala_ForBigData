import org.knowm.xchart.{BitmapEncoder, XYChart, XYChartBuilder}
import org.knowm.xchart.style.markers.SeriesMarkers

import scala.io.Source
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object TimeSeriesPlotExmaple extends App {
  // Function to read CSV file
  def readCsv(file: String): List[(LocalDateTime, Double)] = {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
    val bufferedSource = Source.fromFile(file)
    val data = for {
      line <- bufferedSource.getLines().drop(1).toList
      cols = line.split(",").map(_.trim)
    } yield (LocalDateTime.parse(cols(0), formatter), cols(6).toDouble)
    bufferedSource.close()
    data
  }

  // Read the data
  val data = readCsv("data/Real_Estate.csv")

  // Debug: Print the data to ensure it's read correctly
  data.foreach { case (date, value) => println(s"Date: $date, Value: $value") }

  // Convert data to arrays for plotting
  val xData = data.map(_._1.toEpochSecond(java.time.ZoneOffset.UTC).toDouble).toArray
  val yData = data.map(_._2).toArray

  // Create a chart
  val chart: XYChart = new XYChartBuilder()
    .width(800)
    .height(600)
    .title("House Price of Unit Area Over Time")
    .xAxisTitle("Date")
    .yAxisTitle("House Price of Unit Area")
    .build()

  // Add data to the chart
  val series = chart.addSeries("House Price", xData, yData)
  series.setMarker(SeriesMarkers.NONE)

  // Save the chart as a PNG image
  BitmapEncoder.saveBitmap(chart, "./House_Price_TimeSeries", BitmapEncoder.BitmapFormat.PNG)
}
