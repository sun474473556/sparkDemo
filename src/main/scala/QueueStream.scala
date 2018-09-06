import org.apache.spark.SparkConf
import org.apache.spark.rdd.RDD
import org.apache.spark.streaming.{Seconds, StreamingContext}

import scala.collection.mutable

object QueueStream {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setAppName("TestRDDQueue").setMaster("local[2]")
    val scc = new StreamingContext(sparkConf, Seconds(2))
    val rddQueue = new mutable.SynchronizedQueue[RDD[Int]]()
    val queueStream = scc.queueStream(rddQueue)
    val mappedStream = queueStream.map(r => (r % 10,1))
    val reduceStream = mappedStream.reduceByKey(_ + _)
    reduceStream.print();
    scc.start()

    for (i <- 1 to 10) {
      rddQueue += scc.sparkContext.makeRDD(1 to 100,2)
      scc.awaitTermination()
    }
    scc.stop()

  }

}
