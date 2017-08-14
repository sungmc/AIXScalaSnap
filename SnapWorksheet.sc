object SnapWorksheet {
  println("Welcome to the Scala worksheet")
  
  
  
  val buf = scala.collection.mutable.ListBuffer[String]()
  
  buf += "hello"
  buf += ListBuffer("hello", "I", "am", "not", "robot")
  
  buf.foreach(println)
    
}