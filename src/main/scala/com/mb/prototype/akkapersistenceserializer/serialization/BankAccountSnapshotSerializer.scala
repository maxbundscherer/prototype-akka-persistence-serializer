package com.mb.prototype.akkapersistenceserializer.serialization

import akka.serialization.SerializerWithStringManifest
import java.nio.charset.StandardCharsets

import io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

class BankAccountSnapshotSerializer extends SerializerWithStringManifest {

  import com.mb.prototype.akkapersistenceserializer.actors.BankAccountActor._

  /**
    * Charset
    */
  final val UTF_8 = StandardCharsets.UTF_8.name()

  /**
    * Manifests
    */
  final val DataStorageManifest = classOf[DataStorage].getName

  /**
    * Serializer identifier
    * @return
    */
  override def identifier: Int = 9002

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
    * @return DataStorage
    */
  override def fromBinary(bytes: Array[Byte], manifest: String): DataStorage = {

    manifest match {

      case DataStorageManifest => decode[DataStorage](new String(bytes, UTF_8)).right.get

      case a: Any => throw new NotImplementedError("Can not deserialize '" + a + "'")
    }

  }

  /**
    * Convert to binary
    * @param o Command
    * @return Array
    */
  override def toBinary(o: AnyRef): Array[Byte] = {

    o match {

      case DataStorage  => o.asInstanceOf[DataStorage].asJson.toString.getBytes(UTF_8)

      case a: Any => throw new NotImplementedError("Can not serialize '" + a + "'")
    }

  }

}
