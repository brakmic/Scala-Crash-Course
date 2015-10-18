package brakmic.demo1

object scala_crash {
    
  def show_some_basics() = {
    val cantChangeMe : String = "Immutable Value"
    var changeMe : String = "Mutable Stuff"
  
    //cantChangeMe = "Trying to change!"
    changeMe = "Changing you"
  
    val youHaveToFindOutMyDataType = 200
  
    val superString = """ A am I very cool String,
                        because I'm allowed to have multiple lines,
                        carriage-returns, line-feeds, tabs....
                      """
    
 
  }                                               //> show_some_basics: ()Unit
  
  def saySomething(name: String) : Unit   = {
    println(s"Hello $name")
  }                                               //> saySomething: (name: String)Unit
  
  val aName = "Harris"                            //> aName  : String = Harris
  
  saySomething(aName)                             //> Hello Harris
  
  def returnStringByDefault() : String = {
    "You will get me!"
  }                                               //> returnStringByDefault: ()String
  
  returnStringByDefault()                         //> res0: String = You will get me!
  
  def useNamedParameters(param1: String, param2: Int, param3: Double) = {
    println(s"param1: $param1, param2: $param2, param3: $param3")
  }                                               //> useNamedParameters: (param1: String, param2: Int, param3: Double)Unit
  //endregion
  useNamedParameters(param3=0.001, param1="Demo Demo", param2=123)
                                                  //> param1: Demo Demo, param2: 123, param3: 0.001
  
  def using_standardClasses() = {
    val t800 = new Cyborg("Arnie","T-800","Skynet")
		t800.name = "Johnny"
		t800.say("baby")
  }                                               //> using_standardClasses: ()Unit
  
  def demo_usingCaseClasses() = {
    //instantiate an Android based on a case class
    val C3PO = Android("C3PO","Human-Like Andoid","Anakin Skywalker")
    val manufacturer = C3PO.manufacturer
	  println(s"My manufacturer is $manufacturer")
	
	  C3PO.say("I am an Android!")
  }                                               //> demo_usingCaseClasses: ()Unit
  
  def displayRobotInfo(robot: Robot) = {
    robot match {
      case Android(_,_,"Anakin Skywalker") => println("Oh no! You belong to Darth Vader!")
      case _ => println("fallback")
    }
  }                                               //> displayRobotInfo: (robot: brakmic.demo1.Robot)Unit
  
  def demo_HigherOrderFunctions() = {

    val words = List("Lorem","ipsum","dolor","sit","amet")
  
    words.map(word => word.toUpperCase())

  }                                               //> demo_HigherOrderFunctions: ()List[String]
  

}

//a trait is like an interface but it can also set some "default" implemetations
//however, defining constructors isn't allowed
trait Robot {
  def say(word: String) = {
    println(s"I say: $word")
  }
}
//this class is used in method demo_usingCaseClasses
case class Android(name: String, model: String, manufacturer: String) extends Robot

//this is a "normal" class you can also find in Java
//here we manually define a getter/setter
class Cyborg(var cyborgsName: String, val model: String, val manufacturer: String) {

  def say(word: String) = {
    println(s"Hasta la vista, $word")
  }
  
  def name=cyborgsName
  def name_=(newName: String) {
    println(s"Now changing name to $newName")
    cyborgsName = newName
  }

}