
import SnapUtilies._

object SnapRead extends App {
  import scala.collection.mutable.{ ListBuffer }
  val allCmds = ListBuffer.empty[String]

  import java.nio._
  import java.nio.file._
  import collection.JavaConverters._
  import java.time._
  import scala.concurrent._
  import ExecutionContext.Implicits.global
  import scala.util.{ Failure, Success }

  val dir = FileSystems.getDefault.getPath("snap")

  val future1 = Future {
    for (f <- Files.walk(dir).iterator().asScala.filter(f => f.toFile().isFile())) { // No directory  
      //println("File is " + f)
      if (f.toString().endsWith("snap")) // Read in *.snap file only
        // To Do: Some big file takes a while. 
        Future {
          createCommands(FileToCommand_v2(f.toString()), dir.toString())
        }
    }
  }
  var complete: Boolean = false
  future1.onComplete {
    case Success(value) => complete = true
    case Failure(e)     => e.printStackTrace
  }

  print("Reading snap. Please wait .")
  while (!complete) {
    print("."); Thread.sleep(500)
  }

  println(welcomeMessage)
  var line: String = ""
  var cont: Boolean = true

  while (cont) {
    do {
      line = scala.io.StdIn.readLine("$ ")
    } while (line.isEmpty())
    line match {
      case "quit" => println("Terminating vmsnap"); cont = false
      case _ => println(SnapUtilies.cmdMap.get(line).getOrElse(None))
    }
  }
}