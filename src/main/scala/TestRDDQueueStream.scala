import org.apache.spark.SparkConf

object TestRDDQueueStream {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("TestRddQueue").setMaster("local[2]")
  }
}
