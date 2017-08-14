

object SnapFileExtract extends App {

  //File open
  import scala.io.Source
  val filename = "lvm.snap"
  val source = Source.fromFile(filename, "UTF-8")

  // Collection
  import scala.collection.mutable.{ ListBuffer, Map }
  val cmdsGroup = ListBuffer.empty[String]

  val lines = source.getLines()
  var blockStart = false
  var blockEnd = false
  val cmdDelimiter = ".....   "
  var cmd = ""
  var prevcmd = ""
  var block = ""

  for (i <- lines) {

    if (!i.equals(".....") && i.length() != 0) { // Skip ..... and newline

      // Controls mark
      if (i.contains(cmdDelimiter)) {
        println("Command is" + i.substring(cmdDelimiter.length()))
        cmd = i.substring(cmdDelimiter.length())
        if (!blockStart) { // First command appears
          blockStart = true // Block begins
        } else if (blockStart && !blockEnd) { // Block ends & another command starts
          //println(block)
          cmdsGroup += block
          block = ""  // Clear for next block
          blockEnd = false  // Reset mark
        }
      }
      // Group lines based on mark
      block += "\n" + i

    }
  }

  source.close() // Close file resource

  //println(block)
  cmdsGroup += block // Add last found to ListBuffer


  for (k <- cmdsGroup)
    println(k)

  println("\nnumber of commands = " + cmdsGroup.length)
}
