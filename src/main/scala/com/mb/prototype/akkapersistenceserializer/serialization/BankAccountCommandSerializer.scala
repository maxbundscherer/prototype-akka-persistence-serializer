package com.mb.prototype.akkapersistenceserializer.serialization

import akka.serialization.SerializerWithStringManifest
import java.nio.charset.StandardCharsets

import io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

class BankAccountCommandSerializer extends SerializerWithStringManifest {

  import com.mb.prototype.akkapersistenceserializer.actors.BankAccountActor._

  /**
    * Charset
    */
  final val UTF_8 = StandardCharsets.UTF_8.name()

  /**
    * Manifests
    */
  final val IncrementManifest = classOf[Requests.Increment] .getName
  final val DecrementManifest = classOf[Requests.Decrement] .getName
  final val CrashManifest     = classOf[Requests.Crash]     .getName

  /**
    * Serializer identifier
    * @return
    */
  override def identifier: Int = 9001

  /**
    * Get manifest (getClass.getName) - same for developer-defined manifests
    * @param o Object
    * @return String
    */
  override def manifest(o: AnyRef): String = o.getClass.getName

  /**
    * Convert from binary
    * @param bytes Array
    * @param manifest String
    * @return Command
    */
  override def fromBinary(bytes: Array[Byte], manifest: String): Requests.Command = {

    manifest match {

      case IncrementManifest => decode[Requests.Increment]    (new String(bytes, UTF_8)).right.get
      case DecrementManifest => decode[Requests.Decrement]    (new String(bytes, UTF_8)).right.get
      case CrashManifest     => decode[Requests.Crash]        (new String(bytes, UTF_8)).right.get

      case a: Any => throw new NotImplementedError("Can not deserialize '" + a + "'")
    }

  }

  /**
    * Convert from command to binary
    * @param o Command
    * @return Array
    */
  override def toBinary(o: AnyRef): Array[Byte] = {

    o match {

      case Requests.Increment(_)  => o.asInstanceOf[Requests.Increment]   .asJson.toString.getBytes(UTF_8)
      case Requests.Decrement(_)  => o.asInstanceOf[Requests.Decrement]   .asJson.toString.getBytes(UTF_8)
      case Requests.Crash()       => o.asInstanceOf[Requests.Crash]       .asJson.toString.getBytes(UTF_8)

      case a: Any => throw new NotImplementedError("Can not serialize '" + a + "'")
    }

  }

}