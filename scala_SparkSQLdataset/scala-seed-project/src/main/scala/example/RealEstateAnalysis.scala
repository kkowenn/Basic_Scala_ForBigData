import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.types._
import org.apache.spark.sql.functions._

object RealEstateAnalysis {
  def main(args: Array[String]): Unit = {
    // Initialize Spark Session
    val spark = SparkSession.builder
      .appName("Advanced Real Estate Analysis")
      .config("spark.master", "local")
      .getOrCreate()

    // Define schema for the dataset
    val schema = StructType(Array(
      StructField("Transaction_date", StringType, true),
      StructField("House_age", DoubleType, true),
      StructField("Distance_to_MRT", DoubleType, true),
      StructField("Num_of_convenience_stores", IntegerType, true),
      StructField("Latitude", DoubleType, true),
      StructField("Longitude", DoubleType, true),
      StructField("House_price_per_unit", DoubleType, true)
    ))

    // Load the dataset
    val realEstateDF: DataFrame = spark.read
      .option("header", "true")
      .schema(schema)
      .csv("data/Real_Estate.csv")

    // Register the DataFrame as a SQL temporary view
    realEstateDF.createOrReplaceTempView("real_estate")

    // Perform SQL query to find average house price per unit area
    val avgPriceDF = spark.sql("""
      SELECT AVG(House_price_per_unit) AS Avg_Price_Per_Unit
      FROM real_estate
    """)
    avgPriceDF.show()

    // Filter properties with house price per unit greater than 50
    val highPriceDF = realEstateDF.filter("House_price_per_unit > 50")
    highPriceDF.show()

    // Group by number of convenience stores and calculate average house price per unit
    val avgPriceByStoresDF = realEstateDF.groupBy("Num_of_convenience_stores")
      .agg(avg("House_price_per_unit").alias("Avg_Price_Per_Unit"))
      .orderBy("Num_of_convenience_stores")
    avgPriceByStoresDF.show()

    // Correlation between house price and distance to the nearest MRT station
    val correlation = realEstateDF.stat.corr("House_price_per_unit", "Distance_to_MRT")
    println(s"Correlation between House Price and Distance to MRT: $correlation")

    // Save the average price by stores to a CSV file
    avgPriceByStoresDF.coalesce(1).write.option("header", "true").csv("data/AvgPriceByStores.csv")

    // Stop the Spark session
    spark.stop()
  }
}
