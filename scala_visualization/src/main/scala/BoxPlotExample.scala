import org.apache.commons.math3.random.{RandomDataGenerator, Well19937c}
import org.jfree.chart.{ChartFactory, ChartUtils}
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset
import java.io.File
import scala.jdk.CollectionConverters._

object BoxPlotExample extends App {

  // Function to generate random data
  def generateRandomData(numGroups: Int, numValues: Int): DefaultBoxAndWhiskerCategoryDataset = {
    val randomDataGenerator = new RandomDataGenerator(new Well19937c())
    val dataset = new DefaultBoxAndWhiskerCategoryDataset()

    for (group <- 1 to numGroups) {
      val values = (1 to numValues).map(_ => randomDataGenerator.nextGaussian(0, 1)).toList
      dataset.add(values.asJava, "Group " + group, "")
    }

    dataset
  }

  // Generate dataset
  val dataset = generateRandomData(numGroups = 5, numValues = 100)

  // Create the boxplot chart
  val chart = ChartFactory.createBoxAndWhiskerChart(
    "Boxplot Example",
    "Category",
    "Value",
    dataset,
    false
  )

  // Save the chart as an image
  val chartFile = new File("BoxPlotExample.png")
  ChartUtils.saveChartAsPNG(chartFile, chart, 800, 600)
  println(s"Boxplot saved as ${chartFile.getAbsolutePath}")
}
