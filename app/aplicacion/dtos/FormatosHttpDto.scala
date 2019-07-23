package aplicacion.dtos

import dominio.modelos.{InformacionParqueo, TipoVehiculo}
import infraestructura.configuracion.MensajeExito
import org.joda.time.DateTime
import play.api.libs.json._

object FormatosHttpDto {

  implicit val writesDateTime: Writes[DateTime] = Writes {
    value: DateTime =>
      JsString( value.toLocalDateTime.toString("yyyy-MM-dd HH:mm:ss") )
  }

  implicit val tipoVehiculo = new Format[TipoVehiculo] {
    override def reads(json: JsValue): JsResult[TipoVehiculo] = {
//      val tipoVehiculo = (json \ "tipoVehiculo" ).as[String]
      JsSuccess(TipoVehiculo(json.as[String]))
    }

    override def writes(tipoVehiculo: TipoVehiculo): JsValue = {
      Json.obj(
        "tipoVehiculo" -> tipoVehiculo.descripcion
      )
    }
  }

  implicit val formatInformacionParqueo: OFormat[InformacionParqueo] = Json.format[InformacionParqueo]

  implicit val mensajeExito: OWrites[MensajeExito] = Json.writes[MensajeExito]

}
