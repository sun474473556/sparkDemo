import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.scalatest.time.Second

object StreamingDemo {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("TestDStream").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(10))
    //文件夹 文件流
    val lines = ssc.textFileStream("file:////Users/sunhao/")
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.print()
    ssc.start();
    ssc.awaitTermination()
  }
}
