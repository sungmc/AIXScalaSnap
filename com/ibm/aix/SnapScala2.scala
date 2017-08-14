package com.ibm.aix

import scala.io.Source

object SnapScala2 extends App {
    val hdiskRE = """(hdisk\d+)\s+([a-z0-9]\w+)\s+(\w+)\s+(\w*)""".r.unanchored
    val lsvgRE = """.+(?<=lsvg -o)\n+.+\s+([a-zA-Z]\w+)""".r.unanchored
    
    val source = Source.fromFile("lvm.snap", "UTF-8")
    
    val lines = source.mkString 
    
    
    
    
//    val lineIterator = source.getLines.mkString
//    println(lineIterator)
}


//Array