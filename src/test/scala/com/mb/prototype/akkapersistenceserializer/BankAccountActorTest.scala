package com.mb.prototype.akkapersistenceserializer

import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.testkit.{ImplicitSender, TestKit}
import com.mb.prototype.akkapersistenceserializer.actors.BankAccountActor

class BankAccountActorTest extends TestKit(ActorSystem("system")) with ImplicitSender with WordSpecLike with Matchers with BeforeAndAfterAll {

  import BankAccountActor._

  val bankAccountActor: ActorRef = system.actorOf( Props(new BankAccountActor()), Config.name )

  "Bank Account Actor" should {

    "increment" in {

      bankAccountActor ! Requests.Increment(5)
      expectMsg( Responses.Increment(5) )

      bankAccountActor ! Requests.Increment(15)
      expectMsg( Responses.Increment(20) )

    }

    "decrement" in {

      bankAccountActor ! Requests.Decrement(5)
      expectMsg( Responses.Decrement(15) )

      bankAccountActor ! Requests.Decrement(5)
      expectMsg( Responses.Decrement(10) )

    }

    "increment after recover after crash" in {

      bankAccountActor ! Requests.Crash()
      expectMsg( Responses.Crash() )

      bankAccountActor ! Requests.Increment(5)
      expectMsg( Responses.Increment(15) )

    }

  }

}