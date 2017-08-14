
import SnapUtilies._

object SnapRead extends App {
  import scala.collection.mutable.{ ListBuffer }
  val allCmds = ListBuffer.empty[String]

  import java.nio._
  import java.nio.file._
  import collection.JavaConverters._

  val dir = FileSystems.getDefault.getPath("snap/lvm")

  for (f <- Files.walk(dir).iterator().asScala.filter(f => f.toFile().isFile())) { // No directory  
    //allCmds ++= FileToCommand(f.toString())
    println("File is " + f)
    createCommands(FileToCommand_v2(f.toString()))
  }

  println("""
*******************************************************************************
*                                                                             *
*                                                                             *
*  Welcome to AIX Version 6.1!                                                *
*                                                                             *
*                                                                             *
*  Please see the README file in /usr/lpp/bos for information pertinent to    *
*  this release of the AIX Operating System.                                  *
*                                                                             *
*                                                                             *
*******************************************************************************""")
  var name: String = ""
  while (!name.equals("quit")) {
    // readLine lets you prompt the user and read their input as a String
    name = readLine("$ ")
    println(SnapUtilies.cmdMap.get(name).getOrElse(None))
  }
}