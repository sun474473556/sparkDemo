import java.io.PrintWriter
import java.net.ServerSocket

import scala.io.Source

object DataSourceSocket {

  def random(length : Int) = {
    val rdm = new java.util.Random()
    rdm.nextInt(length)
  }

  def main(args: Array[String]): Unit = {
//    if (args.length != 3) {
//      System.err.println("Usage: <filename> <port> <millisecond>")
//      System.exit(1)
//    }


    val fileName = "/Users/sunhao/word.txt"
    val lines = Source.fromFile(fileName).getLines().toList
    val rowCount = lines.length
    val listener = new ServerSocket(9999)
    while (true) {
      val socket = listener.accept()
      new Thread() {
        override def run = {
          println("Got client connected from: " + socket.getInetAddress)
          val out = new PrintWriter(socket.getOutputStream, true)
          while(true) {
            Thread.sleep(1000)
            val content = lines(random(rowCount))
            println(content)
            out.write(content + '\n')
            out.flush()
          }
          socket.close()
        }
      }.start()
    }
  }

}
