package com.github.dnvriend.akka.ex.test

import java.util.concurrent.Executors
import scala.concurrent._
import scala.concurrent.duration._

object Test3 extends App {

  implicit val ec = ExecutionContext.fromExecutorService(Executors.newWorkStealingPool(2))

  def calc(name: String, max: Int = 0) = (1 to max)
    .map(_*2)
    .map{ e =>
    Thread.sleep((1 second).toMillis)
    val calc = e + 2
    println(name + ": " + calc)
    calc
  }

  val f1 = Future {
    calc("fut1", 5)
  }

  val f2 = Future {
    calc("fut2", 10)
  }

  val f3 = Future {
    calc("fut3", 20)
  }


  Await.ready(Future.sequence(List(f1, f2, f3)), 5 minutes)
}
