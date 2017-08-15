
// https://www.safaribooksonline.com/blog/2013/05/28/scala-type-classes-demystified/

class Root {
  var counts = 0
}

// http://www.scala-lang.org/old/node/115
// Is inner class a solution?

class Cmd (val cstr: String, val ostr:String) {
  val cmds = cstr.split("-")  // Splitting options 
  val command = cmds.head
  val length = cmds.length
  val options = if (cmds.length > 1) cmds.last
                else ""
  
  val category = command match {
    case ("lspv") => Command.LVM             
    case _        => -1
  }
 
  def show() {
    print("Command is " + command)
    if (length > 1) println("-" + options)
    else println
  }
}


class aixcmd(cmdString: String, cmdOutput: String) extends Cmd(cmdString, cmdOutput) {
  show()
}





// ===================================== 
//
//

class DevName(name:String = "") {
}

class Attr(c:String) {
  val cc = c.split("-")
  val command = cc.head
  val length = cc.length
  val options = {
    if (length > 2)
      cc.last
    else
      None
  }
  
  val cmdType = ""
  
}


// ========================================

// Companion object
object Command {
  def apply(cmdOutput: String) {
    new Command(cmdOutput)
  }
  val LVM = 1
  val FS  = 2
}
// Superclass of all commands
class Command(cmdOutput: String) {
  var lsCmd = false
  val cmdType = -1
  val cmdName = ""
  val options = ""
  def stripOptions(opt: Array[String]) = {
    if (opt.length > 1)
      opt.last
    else
      ""
  }
  
  def printCmd {
    println("Command is " + cmdName)
  }
  
}


class AIXCommand(cmdString: String, cmdOutput: String) extends Command(cmdOutput) {
  val cmds = cmdString.split("-")
  override val cmdName = cmds.head
  override val options = stripOptions(cmds)

  override val cmdType = cmdName match {
    case ("lspv") => Command.LVM              
    case _        => -1
  }
  
  val DevT = new BuildDeviceTree(this)
  
  //printCmd
}

