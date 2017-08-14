
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
  cmd match {
    case _ => println("Build dev tree")
  }
}
  


class Adapter {
  
}

class DeviceBuilder {
  
}
