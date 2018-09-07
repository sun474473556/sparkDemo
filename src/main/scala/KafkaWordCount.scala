import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Minutes, StreamingContext}
import org.scalatest.time.Seconds

object KafkaWordCount {
  def main(args: Array[String]): Unit = {
    val sc = new SparkConf().setAppName("KafkaWordCount").setMaster("local[2]")
    val ssc = new StreamingContext(sc, Seconds(10))
    //ssc.checkpoint()
    val zkQuorum = "localhost:2181"
    val group = "1"
    val topics = "wordsender"
    val numThreads = 1
    val topicMap =topics.split(",").map((_,numThreads.toInt)).toMap
    val lineMap = KafkaUtils.createStream(ssc,zkQuorum,group,topicMap)
    val lines = lineMap.map(_._2)
    val words = lines.flatMap(_.split(" "))
    val pair = words.map(x => (x,1))
    val wordCounts = pair.reduceByKeyAndWindow(_ + _,_ - _,Minutes(2),Seconds(10),2) //这行代码的含义在下一节的窗口转换操作中会有介绍
    wordCounts.print
    ssc.start
    ssc.awaitTermination

  }
}
