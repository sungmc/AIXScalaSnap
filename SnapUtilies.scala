

object SnapUtilies {
  import scala.collection.mutable.{ ListBuffer, Map }
  
  val cmdMap = MapMaker.makeMap
  
  def createCommands(x:Map[String,String]) {
     x.foreach {
       case (k, m) => { 
         //new AIXCommand(k, m)
         new aixcmd(k, m)
         cmdMap ++= List(k -> m)
       }
     }
  }
  
  
  def FileToCommand_v2(txtName: String): Map[String, String] = {
    //File open
    import scala.io.Source
    //val filename = "lvm.snap"
    val source = Source.fromFile(txtName, "UTF-8")

    // Collection

    val cmdsGroup = ListBuffer.empty[String]
    val cmdsMap = Map.empty[String, String]

    val lines = source.getLines()
    var blockStart = false
    var blockEnd = false
    val cmdDelimiter = ".....   "
    var cmd = ""
    var nextcmd = ""
    var block = ""

    for (i <- lines) {
      if (!i.equals(".....") && i.length() != 0) { // Skip ..... and newline

        // Controls mark in a file
        if (i.contains(cmdDelimiter)) {
          cmd = nextcmd
          nextcmd = i.substring(cmdDelimiter.length()+1) // Extract command
          if (!blockStart) {
            blockStart = true // Block begins        
          } else if (blockStart && !blockEnd) { // Block ends & another command starts
            cmdsMap += (cmd -> block)
            block = "" // Clear for next block
            blockEnd = false // Reset mark
          }
        } // Group lines based on mark
        else
          block += "\n" + i
      }
    }
    source.close() // Close file resource
    
    cmdsMap += (nextcmd -> block)
    cmdsMap
  }

  def FileToCommand(txtName: String): List[String] = {
    import scala.io.Source
    //e.g. filename = "lvm.snap"
    val source = Source.fromFile(txtName, "UTF-8")

    // Collection
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

        // Controls mark in a file
        if (i.contains(cmdDelimiter)) {
          cmd = i.substring(cmdDelimiter.length()) // Extract command
          if (!blockStart) { // First command appears
            blockStart = true // Block begins
          } else if (blockStart && !blockEnd) { // Block ends & another command starts
            cmdsGroup += block
            block = "" // Clear for next block
            blockEnd = false // Reset mark
          }
        }
        // Group lines based on mark
        block += "\n" + i

      }
    }

    source.close() // Close file resource

    cmdsGroup += block // Add last found to ListBuffer
    cmdsGroup.toList
  }
}