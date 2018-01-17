package com.mb.prototype.akkapersistenceserializer.actors

import akka.persistence.{PersistentActor, SnapshotOffer}
import akka.actor.ActorLogging

object BankAccountActor {

  object Requests {

    sealed trait Command extends Serializable

    case class  Increment(amount: Long) extends Command
    case class  Decrement(amount: Long) extends Command
    case class  Crash()                 extends Command

  }

  object Responses {

    case class  Increment(accountBalance: Long)
    case class  Decrement(accountBalance: Long)
    case class  Crash()

  }

  object Config {

    val name: String            = "bankAccountActor"
    val snapshotInterval: Int   = 10

  }

  final case class DataStorage(accountBalance: Long = 0) {

    /**
      * Increment accountBalance
      * @param amount Long
      * @return New DataStorage and new accountBalance
      */
    def increment(amount: Long): (DataStorage, Long) = {

      val newDataStorage = copy(accountBalance = accountBalance + amount)
      (newDataStorage, newDataStorage.accountBalance)
    }

    /**
      * Decrement accountBalance
      * @param amount Long
      * @return New DataStorage and new accountBalance
      */
    def decrement(amount: Long): (DataStorage, Long) = {

      increment(-amount)
    }

  }

}

class BankAccountActor extends PersistentActor with ActorLogging {

  import com.mb.prototype.akkapersistenceserializer.actors.BankAccountActor._

  override def persistenceId: String = Config.name

  /**
    * DataStorage
    */
  var dataStorage = DataStorage()

  /**
    * Recovery behavior
    * @return Receive
    */
  override def receiveRecover: Receive = {

    case SnapshotOffer(_, snapshot: DataStorage) =>

      log.info("Recover snapshot (" + snapshot + ")")
      dataStorage = snapshot

    case msg: Requests.Increment => increment(msg.amount)
    case msg: Requests.Decrement => decrement(msg.amount)

    case msg: Any => log.warning("Get unhandled message in recovery behavior (" + msg + ")")
  }

  /**
    * Default behavior
    * @return Receive
    */
  override def receiveCommand: Receive = {

    case msg: Requests.Increment => persist(msg) { event => increment(event.amount) }
    case msg: Requests.Decrement => persist(msg) { event => decrement(event.amount) }
    case msg: Requests.Crash     => persist(msg) { event => crash() }

    case msg: Any => log.warning("Get unhandled message in default behavior (" + msg + ")")
  }

  private def increment(amount: Long): Unit = {

    val result: (DataStorage, Long) = dataStorage.increment(amount)

    dataStorage = result._1
    doSnapshot()

    if(!recoveryRunning) sender ! Responses.Increment(result._2)
  }

  private def decrement(amount: Long): Unit = {

    val result: (DataStorage, Long) = dataStorage.decrement(amount)

    dataStorage = result._1
    doSnapshot()

    if(!recoveryRunning) sender ! Responses.Decrement(result._2)
  }

   private def crash(): Unit = {

    if(!recoveryRunning) sender ! Responses.Crash()
    throw new RuntimeException("Simulate crash")
  }

  private def doSnapshot(): Unit = {

    if(!recoveryRunning && lastSequenceNr % Config.snapshotInterval == 0 && lastSequenceNr != 0) {

      log.info("Do snapshot (" + dataStorage + ") now!")
      saveSnapshot(dataStorage)
    }

  }

}