package brakmic.monads

object app {
  val someValue = AClass(1,"A")                   //> someValue  : brakmic.monads.AClass = ID: 1, Value: A
  val nullValue = null                            //> nullValue  : Null = null
  val monad = aMonad(someValue)                   //> monad  : brakmic.monads.aMonad[brakmic.monads.AClass] = aMonad(ID: 1, Value:
                                                  //|  A)
  
  def convertAToB(a: AClass) : BClass = {
    println("map on " + a.toString())
    new BClass(a.id + 100, "COPY_FROM_" + a.value, "EXTRA " + a.id)
  }                                               //> convertAToB: (a: brakmic.monads.AClass)brakmic.monads.BClass
  
  def flatMapAToB(a: AClass) : Monad[BClass] = {
    aMonad(new BClass(a.id + 100,"COPY_FROM_" + a.value,"some extra value"))
  }                                               //> flatMapAToB: (a: brakmic.monads.AClass)brakmic.monads.Monad[brakmic.monads.B
                                                  //| Class]
                             
  monad.map { convertAToB }                       //> map on ID: 1, Value: A
                                                  //| res0: brakmic.monads.Monad[brakmic.monads.BClass] = aMonad(ID: 101, Value: C
                                                  //| OPY_FROM_A, Extra: EXTRA 1)
  monad.flatMap { flatMapAToB }                   //> res1: brakmic.monads.Monad[brakmic.monads.BClass] = aMonad(ID: 101, Value: C
                                                  //| OPY_FROM_A, Extra: some extra value)
  
}

  sealed trait Monad[+A] {
    
    //def map[B](fun: A => B): Monad[B]
    def map[B](fun: A => B): Monad[B] = this match {
      case aMonad(a) => aMonad(fun(a))
      case NullMonad => NullMonad
    }
    
    //def flatMap[B](fun: A => Monad[B]): Monad[B]
    def flatMap[B](fun: A => Monad[B]): Monad[B] = this match {
      case aMonad(a) => fun(a)
      case NullMonad => NullMonad
    }
  }
  
  case class aMonad[+A](a: A) extends Monad[A] {
    //override def map[B](fn: A => B) : Monad[B] = aMonad[B](fn(a))
    //override def flatMap[B](fn: A => Monad[B]) : Monad[B] = fn(a)
  }
  
  case object NullMonad extends Monad[Nothing] {
    //override def map[B](fun: Nothing => B) : Monad[B] = NullMonad
    //override def flatMap[B](fun: Nothing => Monad[B]) = NullMonad
  }
  
  //for testing
  case class AClass(id: Int = -1, value: String = "undefined") {
    
    override def toString() : String = {
      s"ID: $id, Value: $value"
    }
  }
  case class BClass(id: Int = -1,value: String = "undefined",
                       extra: String = "extra data") {
    override def toString() : String = {
      s"ID: $id, Value: $value, Extra: $extra"
    }
  }