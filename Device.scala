
// Companion object
object Device {
  def apply(devname:String) = new Device(devname)
}

// Superclass of all devices
class Device(devname:String)  {
  val major = 0
  val minor = 0
  
}

class Disk(name:String) extends Device(name) {
  
}


class BuildDeviceTree (cmd:Command) {
  cmd.cmdType match {
    case (Command.LVM) => { cmd.printCmd
                            
    }
    case _ => // println("Unknown command type")
  }
}
  


class Adapter {
  
}

class DeviceBuilder {
  
}
