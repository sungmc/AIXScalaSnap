

import scala.io.Source


object SnapScala5 extends App {
  //To Do: Move Regex over to a companion class
  val lspvRE = """lspv\s*.+\s*([\w\s]*)""".r.unanchored
  val lsvgRE = """lsvg -o""".r.unanchored
  //To Do: Device without maj number is not captpured.
  //e.g. srwxrwxrwx    1 root     system            0 Jul  4 00:13 SRC
  val deviceRE = """([-bcds]r[-rwxT]*)\s+(\d)\s+(\w+)\s+(\w+)\s+(\d*),\s+(\d+)\s+(\w+\s+\d+\s+\d+:\d+)\s+(\w+)\s*""".r.unanchored
  val opsRE = """(lspv|lsvg -o|ls -l /dev)""".r.unanchored

  // Collections
  // http://docs.scala-lang.org/overviews/collections/concrete-mutable-collection-classes.html
  import scala.collection.mutable.{ HashMap, MultiMap, Map, Set, ListBuffer }
  
  val mm = new HashMap[String, Set[String]] with MultiMap[String, String]

  // ListBuffer
  val buf = scala.collection.mutable.ListBuffer[Device]()

  // To Do: Based on type, 
  def CaptureBlock(t:Int, r:String) {
 
  }
  
  //val lineIterator = source.getLines
  def splitBlocks(fileName:String) {
    
    /* Hint:
def allMatches(s: String): (String, List[String]) = {
  val bracketMatcher = """\[(\w+)\]""".r
  val startMatcher = """^(\w+)\[""".r

  val first = startMatcher.findFirstMatchIn(s).get.group(1)
  val matches = bracketMatcher.findAllMatchIn(s)
  val indexes = matches.map(_.group(1)).toList
  (first, indexes)
}

allMatches("sometextHere[a][b][c]")

https://stackoverflow.com/questions/10673892/regular-expression-to-match-single-dot-but-not-two-dots

(lspv|lsvg -o|ls -l \/dev)\s+.+\s+([\w-_:,\s]+)


(lspv|lsvg -o|ls -l \/dev)\s+.+\s+([\w-_:,\s]+|[.][a-zA-Z_-]+)

match .SRC
-- [.][0-9a-zA-Z_-]*

works but dot(.) is killing me
-- (lspv|lsvg -o|ls -l \/dev)\s+.+\s+([-:_\w\s]*)



     */
    val source = Source.fromFile(filename, "UTF-8")
    val lineIterator = source.getLines
    
    for (line <- lineIterator) line match {
      case opsRE(t) => println(line)
      case _ => {}
    }
    source.close()
  }

  //File open
  val filename = "/Users/sungmc/eclipse_workspace/AIXSnapScalaProject/lvm.snap"
  splitBlocks(filename)
  
/*
  for (line <- lineIterator) line match {
    case lspvRE(r) => {
      println("found lspv")  
      CaptureBlock(1, r)
    }
    case lsvgRE() => {
      println("found lsvg")
    }
    case _ => {} //Do we really need these?
    //1. lsvg -o
    //2. some devices without major number
  }
*/
}