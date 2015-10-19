package brakmic.monads

object app {
  val entries = AClass(1,"A")                     //> entries  : brakmic.monads.AClass = ID: 1, Value: A
  val monad = aMonad(entries)                     //> monad  : brakmic.monads.aMonad[brakmic.monads.AClass] = aMonad(ID: 1, Value: 
                                                  //| A)
  
  def convertAToB(a: AClass) : BClass = {
    println("map on " + a.toString())
    new BClass(a.id + 100, "COPY_FROM_" + a.value, "EXTRA " + a.id)
  }                                               //> convertAToB: (a: brakmic.monads.AClass)brakmic.monads.BClass
  
  def flatMapAToB(a: AClass) : Monad[BClass] = {
    aMonad(new BClass(1,"some value","some extra value"))
  }                                               //> flatMapAToB: (a: brakmic.monads.AClass)brakmic.monads.Monad[brakmic.monads.B
                                                  //| Class]
                             
  monad.map { convertAToB }                       //> map on ID: 1, Value: A
                                                  //| res0: brakmic.monads.Monad[brakmic.monads.BClass] = aMonad(ID: 101, Value: C
                                                  //| OPY_FROM_A, Extra: EXTRA 1)
  monad.flatMap { flatMapAToB }                   //> res1: brakmic.monads.Monad[brakmic.monads.BClass] = aMonad(ID: 1, Value: som
                                                  //| e value, Extra: some extra value)
}

  sealed trait Monad[+A] {
    def map[B](fun: A => B): Monad[B]
    def flatMap[B](fun: A => Monad[B]): Monad[B]
  }
  
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
  
  case class aMonad[+A](a: A) extends Monad[A] {
    override def map[B](fn: A => B) : Monad[B] = new aMonad[B](fn(a))
    override def flatMap[B](fn: A => Monad[B]) : Monad[B] = fn(a)
  }