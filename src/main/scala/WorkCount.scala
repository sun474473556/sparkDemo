import org.apache.spark.{SparkConf, SparkContext}

//  * filter(func)：筛选出满足函数func的元素，并返回一个新的数据集
//  * map(func)：将每个元素传递到函数func中，并将结果返回为一个新的数据集
//  * flatMap(func)：与map()相似，但每个输入元素都可以映射到0或多个输出结果
//  * groupByKey()：应用于(K,V)键值对的数据集时，返回一个新的(K, Iterable)形式的数据集
//  * reduceByKey(func)：应用于(K,V)键值对的数据集时，返回一个新的(K, V)形式的数据集，其中的每个值是将每个key传递到函数func中进行聚合

//  * count() 返回数据集中的元素个数
//  * collect() 以数组的形式返回数据集中的所有元素
//  * first() 返回数据集中的第一个元素
//  * take(n) 以数组的形式返回数据集中的前n个元素
//  * reduce(func) 通过函数func（输入两个参数并返回一个值）聚合数据集中的元素
//  * foreach(func) 将数据集中的每个元素传递到函数func中运行*


//map 和 reduce的区别 reduce是聚合，map是做
object WorkCount {
  def main(args: Array[String]): Unit = {
    val inputFile = "file:////Users/sunhao/word.txt"
    val conf = new SparkConf().setAppName("WorkCOunt").setMaster("local")
    val sc = new SparkContext(conf);
    // textFile 创建RDD 方式1
    val textFile = sc.textFile(inputFile);
    val wordCount = textFile.flatMap(line => line.split(" ")).map(word => (word,1)).reduceByKey((a,b) => a+b)
    wordCount.foreach(println);
    val lines = sc.textFile("file:////Users/sunhao/word.txt")
    val lineLengths = lines.map(s => s.length)
    val totalLength = lineLengths.reduce((a, b) => a + b)
    val count = lines.filter(s => s.contains("tom")).count();
    println(count)
    println(totalLength)
    val list = List("Hadoop","Spark","Hive");
    // parallelize 方式二
    val rdd = sc.parallelize(list);
    // 避面每次都从头生成rdd
    rdd.cache()
    //行动操作
    println(rdd.count())
    //使用的是rdd.count行动操作的rdd缓存
    println(rdd.collect().mkString(","))

    val rdd1 = sc.parallelize(Array(("spark",2),("hadoop",6),("hadoop",4),("spark",6)))
    val result = rdd1.mapValues(x => (x,1)).reduceByKey((x,y) => (x._1+y._1,x._2+y._2)).mapValues(x => (x._1 / x._2));
    result.foreach(println);

    //广播变量
    val broadcastVar = sc.broadcast(Array(1, 2, 3))
    broadcastVar.value

    //累加器
    val accum = sc.longAccumulator("My Accumulator")
    sc.parallelize(Array(1, 2, 3, 4)).foreach(x => accum.add(x))
    accum.value
  }
}
