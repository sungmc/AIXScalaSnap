

import scala.io.Source

object SnapScala extends App {
  //To Do: Move Regex over to a companion class
  val hdiskRE = """(hdisk\d+)\s+([a-z0-9]\w+)\s+(\w+)\s+(\w*)""".r.unanchored
  val lsvgRE = """.+(?<=lsvg -o)\n+.+\s+([a-zA-Z]\w+)""".r.unanchored
  //To Do: Device without maj number is not captpured.
  //e.g. srwxrwxrwx    1 root     system            0 Jul  4 00:13 SRC
  val deviceRE = """([-bcds]r[-rwxT]*)\s+(\d)\s+(\w+)\s+(\w+)\s+(\d*),\s+(\d+)\s+(\w+\s+\d+\s+\d+:\d+)\s+(\w+)\s*""".r.unanchored

  //val lvmSet = scala.collection.mutable.Set("")    // overload operator += isn't working

  //This function works.
  def getMatchAsSet[A](a: A): A = {
    println("============")
    a
  }

  //case class definition
  case class hdisk(diskname: String, pvid: String, vgname: String, status: String)
  case class device(mode: String, somenumber: Int, owner: String, group: String, major: Int, minor: Int, date: String, name: String)

  //Collection
  //https://alvinalexander.com/scala/how-to-create-mutable-list-in-scala-listbuffer-cookbook
  import scala.collection.mutable.ListBuffer
  val alldisks = new ListBuffer[hdisk]()
  val alldevices = new ListBuffer[device]()

  // MultiMap
  // first import all necessary types from package `collection.mutable`
  import collection.mutable.{ HashMap, MultiMap, Set, SortedSet }

  // to create a `MultiMap` the easiest way is to mixin it into a normal
  // `Map` instance
  val mm = new HashMap[String, Set[String]] with MultiMap[String, String]
  
  // http://blog.xebia.com/multimap-in-scala/
implicit class ListMultiMap[A,B](val map: Map[A, List[B]]) extends AnyVal {
  def addBinding(key: A, value: B): Map[A, List[B]] = 
    map + (key -> { value :: map.getOrElse(key, Nil) })
}
var result = Map(1 -> List(2, 3))
  .addBinding(2, 5)
  .addBinding(2, 6)
  .addBinding(3, 7)


println(result.toString())



  
  // End of // http://blog.xebia.com/multimap-in-scala/
  
  
  
  
  
  
    
  // Disadvantage of Set is it's unordered.
  // Or use LinkedHashSet???

  // Map(h, List(,,,))
  // https://alvinalexander.com/scala/how-to-add-update-remove-mutable-map-elements-scala-cookbook
  import scala.collection.mutable.Map
  val newmap = scala.collection.mutable.Map[String, List[String]]()

  val newset = scala.collection.mutable.SortedSet[String]()

  // to add key-value pairs to a multimap it is important to use
  // the method `addBinding` because standard methods like `+` will
  // overwrite the complete key-value pair instead of adding the
  // value to the existing key

  //File open
  val filename = "lvm.snap"
  val source = Source.fromFile(filename, "UTF-8")
  val lineIterator = source.getLines

  // Expensive - flatMap reads all lines till end and thus file needs to be reopened for next flatMap. 
  /* Omitted.
    val devicemap = source.getLines.flatMap { deviceRE.findAllIn _ }.toList
    devicemap.foreach(println)
    
    val diskmap = source.getLines.flatMap { hdiskRE.findAllIn _ }.toList  
    diskmap.foreach(println)
    */

  for (line <- lineIterator) line match {
    case hdiskRE(h, p, v, s) => {
      println(s"$h, $p, $v, $s") //To Do: Put in tuple, or Set, Map??
      //val a = scala.collection.mutable.Set(h, p, v, s) //Working
      //getMatchAsSet(Set(h, p, v, s)) //Working

      // Add to a mutable Map
      alldisks += hdisk(h, p, v, s)
      mm.addBinding(h, p)
      mm.addBinding(h, v)
      mm.addBinding(h, s)

      // Add to mutable Map
      newmap += (h -> List(p, v, s))

    }
    //case lsvgRE(x) => println("found")
    case deviceRE(a, b, c, d, e, f, g, h) => {
      println(s"$a,$b,$c,$d,$e,$f,$g,$h")
      // Add to a mutable Map
      alldevices += device(a, b.toInt, c, d, e.toInt, f.toInt, g, h)
      // Or, we start adding (addBinding) to a MultiMap/

      mm.addBinding(h, a)
      mm.addBinding(h, b)
      mm.addBinding(h, c)
      mm.addBinding(h, d)
      mm.addBinding(h, e)
      mm.addBinding(h, f)
      mm.addBinding(h, g)

      // Add to mutable Map
      //newmap += (h -> List(a,b,c,d,e,f,g))

    }
    case _ => {} //Do we really need these?
    //1. lsvg -o
    //2. some devices without major number
  }

  source.close()
  println("====================\n\n")
  // Display the result
  mm.foreach(x => println(x))
  println("====================================")
  println("MultiMap Keys")
  println("====================================")
  println(mm.keySet)

  // mutable Map
  println("\n\n\n\n====================================")
  println("mutable Map")
  println("====================================")
  newmap.foreach(println)
}