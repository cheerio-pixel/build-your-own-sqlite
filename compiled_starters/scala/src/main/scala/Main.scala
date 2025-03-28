import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import scala.util.Using

object Main extends App {
  if args.length < 2
  then {
    println("Missing <database path> and <command>")
    System.exit(0);
  }
  val databaseFilePath = args(0);
  val command = args(1);
  command match {
    case ".dbinfo" => {
      Using(new FileInputStream(new File(databaseFilePath))) { databaseFile =>
      databaseFile.skip(16)
      val pageSizeBytes = new Array[Byte](2)
      databaseFile.read(pageSizeBytes)
      val pageSizeSigned = ByteBuffer.wrap(pageSizeBytes).getShort()
      val pageSize = pageSizeSigned & 0xFFFF

      // You can use print statements as follows for debugging, they'll be visible when running tests.
      System.err.println("Logs from your program will appear here!")

      // Uncomment this block to pass the first stage
      // println("database page size: " + pageSize)
      }.recover {
        case e: IOException =>
          println(s"Error reading database file: ${e.getMessage}")
          System.exit(1)
      }
    }
    case _ => println("Missing or invalid command passed: " + command)
  }
}
