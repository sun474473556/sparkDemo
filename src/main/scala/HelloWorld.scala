package scala

import java.io.{File, PrintWriter}

import com.sun.prism.PixelFormat.DataType

import scala.util.matching.Regex


object HelloWorld {
  def main(args: Array[String]): Unit = {
    val s = "加油";
    var VariableName : Int = 1
    println(s)
    println("Hello\tworld\n\n"+(1+1)+0x777L)
    a()
    println(addInt(10,2))
    var factor = 3
    //闭包 multiplier看成一个方法
    val multiplier = (i:Int) => i* factor
    println(multiplier(1))
    for (x <- 1 to 10) {
      println(x)
    }
    var pt = new Point(10,20)
    pt.move(10,10)
    val pattern = new Regex("(S|s)cala")  // 首字母可以是大写 S 或小写 s
    val str = "Scala is scalable and cool"

    println((pattern findAllIn str).mkString(","))   // 使用逗号 , 连接返回结果
    var writer = new PrintWriter(new File("test.txt"))
    writer.write("sunhao")
    writer.close()
  }
  def a(){
    var a = 10;
    var b = 20;
    var c = 25;
    var d = 25;
    println("a + b = " + (a + b) );
    println("a - b = " + (a - b) );
    println("a * b = " + (a * b) );
    println("b / a = " + (b / a) );
    println("b % a = " + (b % a) );
    println("c % a = " + (c / a) );

  }

  def addInt(a:Int,b:Int) : Int = {
    var sum:Int = 0
    sum = a+b
    return sum
  }

  def runSpark(): Unit = {

  }

}
class Point(xc: Int, yc: Int) {
  var x: Int = xc
  var y: Int = yc

  def move(dx: Int, dy: Int) {
    x = x + dx
    y = y + dy
    println ("x 的坐标点: " + x);
    println ("y 的坐标点: " + y);
  }
}

class Location(val xc: Int, val yc: Int,
               val zc :Int) extends Point(xc, yc){
  var z: Int = zc

  def move(dx: Int, dy: Int, dz: Int) {
    x = x + dx
    y = y + dy
    z = z + dz
    println ("x 的坐标点 : " + x);
    println ("y 的坐标点 : " + y);
    println ("z 的坐标点 : " + z);
  }
}