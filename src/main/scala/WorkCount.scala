import org.apache.spark.{SparkConf, SparkContext}

object WorkCount {
  def main(args: Array[String]): Unit = {
    val inputFile = "file:////Users/sunhao/word.txt"
    val conf = new SparkConf().setAppName("WorkCOunt").setMaster("local")
    val sc = new SparkContext(conf);
    val textFile = sc.textFile(inputFile);
    val wordCount = textFile.flatMap(line => line.split(" ")).map(word => (word,1)).reduceByKey((a,b) => a+b)
    wordCount.foreach(println);

  }
}
