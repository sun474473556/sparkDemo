
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

object NetworkWordCount {
  def main(args: Array[String]): Unit = {
//    if (args.length < 2) {
//      System.err.println("Usage: NetwordCount <hostname> <port>")
//      System.exit(1)
//    }

    //socket 流
    val sparkConf = new SparkConf().setAppName("NetworkWordCount").setMaster("local[2]");
    val ssc = new StreamingContext(sparkConf, Seconds(10))
    val lines = ssc.socketTextStream("localhost", 9999, StorageLevel.MEMORY_AND_DISK_SER)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x,1)).reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()
    ssc.awaitTermination()
  }
}
