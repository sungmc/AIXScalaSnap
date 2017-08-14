
// https://www.safaribooksonline.com/blog/2013/05/28/scala-type-classes-demystified/

class Root {
  var counts = 0
}


trait Cmd extends Comparable[String] {

  def show() {
    print()
  }

}

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
}
// Superclass of all commands
class Command(cmdOutput: String) {
  var lsCmd = false
  val cmdType = ""
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
    case ("lspv") => "LVM"
    case _        => ""
  }
  
  val DevT = new BuildDeviceTree(this)
  
  if (cmdType == "LVM") {
    println("LVM")
    if (cmdName.equals("lspv")) {
      println("lspv")
             
    }
      
  }
  
  printCmd
}

class LVMCommand(cmd:String, output:String) extends AIXCommand(cmd, output) {
}

