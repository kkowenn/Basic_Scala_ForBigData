import org.knowm.xchart.{BitmapEncoder, CategoryChartBuilder, SwingWrapper}
import scala.jdk.CollectionConverters._

object HistogramExample {

  // Define case class for the data
  case class Rating(userId: Int, movieId: Int, rating: Int, timestamp: Long)

  def main(args: Array[String]): Unit = {
    // Manually define the data
    val data = List(
      Rating(196, 242, 3, 881250949),
      Rating(186, 302, 3, 891717742),
      Rating(22, 377, 1, 878887116),
      Rating(244, 51, 2, 880606923),
      Rating(166, 346, 1, 886397596)
    )

    // Extract the ratings
    val ratings = data.map(_.rating)

    // Compute the max, min, and average values
    val maxRating = ratings.max
    val minRating = ratings.min
    val avgRating = ratings.sum.toDouble / ratings.size

    println(s"Max rating: $maxRating")
    println(s"Min rating: $minRating")
    println(s"Average rating: $avgRating")

    // Create histogram data
    val histogram = ratings.groupBy(identity).view.mapValues(_.size).toMap

    // Convert histogram data to Java lists
    val keys = histogram.keys.map(_.asInstanceOf[Integer]).toList.asJava
    val values = histogram.values.map(_.asInstanceOf[Integer]).toList.asJava

    // Create Chart
    val chart = new CategoryChartBuilder().width(800).height(600).title("Ratings Histogram").xAxisTitle("Rating").yAxisTitle("Count").build()

    // Add data to chart
    chart.addSeries("Ratings", keys, values)

    // Show the chart
    new SwingWrapper(chart).displayChart()

    // Save the chart as PNG
    BitmapEncoder.saveBitmap(chart, "./RatingsHistogram", BitmapEncoder.BitmapFormat.PNG)
  }
}
