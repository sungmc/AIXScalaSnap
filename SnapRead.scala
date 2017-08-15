
import SnapUtilies._

object SnapRead extends App {
  import scala.collection.mutable.{ ListBuffer }
  val allCmds = ListBuffer.empty[String]

  import java.nio._
  import java.nio.file._
  import collection.JavaConverters._

  val dir = FileSystems.getDefault.getPath("snap")

  for (f <- Files.walk(dir).iterator().asScala.filter(f => f.toFile().isFile())) { // No directory  
    //println("File is " + f)
    if (f.toString().endsWith("snap"))  // Read in *.snap file only
      createCommands(FileToCommand_v2(f.toString()), dir.toString())
  }

  println(welcomeMessage)
  var line:String = ""
  while (!line.equals("quit")) {
    // readLine lets you prompt the user and read their input as a String
    do {
      line = scala.io.StdIn.readLine("$ ")
    } while (line.isEmpty())
    println(SnapUtilies.cmdMap.get(line).getOrElse(None))
  }
}