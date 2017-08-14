

import scala.io.Source


object SnapScala4 extends App {
  //To Do: Move Regex over to a companion class
  val hdiskRE = """(hdisk\d+)\s+([a-z0-9]\w+)\s+(\w+)\s+(\w*)""".r.unanchored
  val lsvgRE = """.+(?<=lsvg -o)\n+.+\s+([a-zA-Z]\w+)""".r.unanchored
  //To Do: Device without maj number is not captpured.
  //e.g. srwxrwxrwx    1 root     system            0 Jul  4 00:13 SRC
  val deviceRE = """([-bcds]r[-rwxT]*)\s+(\d)\s+(\w+)\s+(\w+)\s+(\d*),\s+(\d+)\s+(\w+\s+\d+\s+\d+:\d+)\s+(\w+)\s*""".r.unanchored


  // Collections
  // http://docs.scala-lang.org/overviews/collections/concrete-mutable-collection-classes.html
  import scala.collection.mutable.{ HashMap, MultiMap, Map, Set, ListBuffer }
  
  val mm = new HashMap[String, Set[String]] with MultiMap[String, String]

  // ListBuffer
  val buf = scala.collection.mutable.ListBuffer[Device]()

  
  var deviceMap = Map[String, Device]()
  
  val lbMap = List()
  
  /*
  def addMap(h:String, item:Device) {
      deviceMap += Map(h, item)  
  }
  */

  //File open
  val filename = "/Users/sungmc/eclipse_workspace/AIXSnapScalaProject/lvm.snap"
  val source = Source.fromFile(filename, "UTF-8")
  val lineIterator = source.getLines

  for (line <- lineIterator) line match {
    case hdiskRE(h, p, v, s) => {
      println(s"$h, $p, $v, $s") //To Do: Put in tuple, or Set, Map??  
      deviceMap += (h -> Device(h))
      
    }
    //case lsvgRE(x) => println("found")
    case deviceRE(a, b, c, d, e, f, g, h) => {
      deviceMap += (h -> Device(h))
    }
    case _ => {} //Do we really need these?
    //1. lsvg -o
    //2. some devices without major number
  }

  source.close()

  // Print the result
  deviceMap.foreach(println)

}