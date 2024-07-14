import org.jfree.chart.ChartFactory
import org.jfree.chart.ChartUtils
import org.jfree.chart.plot.PlotOrientation
import org.jfree.data.general.DefaultPieDataset
import org.jfree.data.general.PieDataset
import java.io.File

object PieChartExample extends App {

  // Function to create a dataset
  def createDataset(): DefaultPieDataset = {
    val dataset = new DefaultPieDataset()
    dataset.setValue("Category A", 40.0)
    dataset.setValue("Category B", 30.0)
    dataset.setValue("Category C", 20.0)
    dataset.setValue("Category D", 10.0)
    dataset
  }

  // Create dataset
  val dataset: DefaultPieDataset = createDataset()

  // Create the pie chart
  val chart = ChartFactory.createPieChart(
    "Pie Chart Example",  // chart title
    dataset,              // dataset
    true,                 // include legend
    true,
    false
  )

  // Save the chart as an image
  val chartFile = new File("PieChartExample.png")
  ChartUtils.saveChartAsPNG(chartFile, chart, 800, 600)
  println(s"Pie chart saved as ${chartFile.getAbsolutePath}")
}
