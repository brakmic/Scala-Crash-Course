package brakmic.monads

object app {
  val entries = List(AClass(1,"A"), AClass(2,"A"), AClass(3,"A"))
                                                  //> entries  : List[brakmic.monads.AClass] = List(ID: 1, Value: A, ID: 2, Value:
                                                  //|  A, ID: 3, Value: A)
  val monad = new aMonad(entries)                 //> monad  : brakmic.monads.aMonad[brakmic.monads.AClass] = brakmic.monads.aMona
                                                  //| d@3cda1055
  
  def convertAToB(a: AClass) : BClass = {
    println("map on " + a.toString())
    new BClass(a.id + 100, "COPIED_FROM_" + a.value, "EXTRA " + a.id)
  }                                               //> convertAToB: (a: brakmic.monads.AClass)brakmic.monads.BClass
  
  def flatConvertAToB(a: AClass) : Monad[BClass] = {
    println("flatMap on " + a.toString())
    val entries = List(BClass(1,"B","extra 1"), BClass(2,"B","extra 2"), BClass(2,"B","extra 3"))
    new aMonad[BClass](entries)
  }                                               //> flatConvertAToB: (a: brakmic.monads.AClass)brakmic.monads.Monad[brakmic.mona
                                                  //| ds.BClass]
  
  val fromMap = monad.map { entry => convertAToB(entry) }
                                                  //> map on ID: 1, Value: A
                                                  //| map on ID: 2, Value: A
                                                  //| map on ID: 3, Value: A
                                                  //| fromMap  : brakmic.monads.Monad[brakmic.monads.BClass] = brakmic.monads.aMon
                                                  //| ad@7a5d012c
                                                  
  val fromFlatMap = monad.flatMap { entry => flatConvertAToB(entry)}
                                                  //> flatMap on ID: 1, Value: A
                                                  //| flatMap on ID: 2, Value: A
                                                  //| flatMap on ID: 3, Value: A
                                                  //| fromFlatMap  : brakmic.monads.Monad[brakmic.monads.BClass] = brakmic.monads.
                                                  //| aMonad@6b2fad11
                                                  
}

  trait Monad[X] {
    def map[Y](fun: X => Y): Monad[Y]
    def flatMap[Y](fun: X => Monad[Y]): Monad[Y]
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
  
  class aMonad[X](values: List[X]) extends Monad[X] {
    def map[Y](fn: X => Y) : Monad[Y] = {
      val bEntries = values.map(entry => fn(entry))
      new aMonad[Y](bEntries)
    }

    def flatMap[Y](fn: X => Monad[Y]) : Monad[Y] = {
      var newValues = List[Y]()
      values.foreach(elem => {
	      newValues :: List(fn(elem))
      })
      new aMonad[Y](newValues)
    }
  }