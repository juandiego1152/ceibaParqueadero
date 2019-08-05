package aplicacion.dtos

import java.sql.Timestamp

import dominio.modelos.{InformacionVehiculoParqueadero, RegistroParqueo, TipoVehiculo}
import infraestructura.configuracion.MensajeExito
import org.joda.time.DateTime
import play.api.libs.json._

object FormatosHttpDto {

  implicit val writesDateTime: Writes[DateTime] = Writes {
    value: DateTime =>
      JsString(value.toLocalDateTime.toString("yyyy-MM-dd HH:mm:ss"))
  }

  implicit val writesTimestamp: Writes[Timestamp] = Writes {
    value: Timestamp =>
      JsString(new DateTime(value.getTime).toString("yyyy-MM-dd HH:mm:ss"))
  }

  implicit val tipoVehiculo = new Format[TipoVehiculo] {
    override def reads(json: JsValue): JsResult[TipoVehiculo] = {
      JsSuccess(TipoVehiculo(json.as[String]))
    }

    override def writes(tipoVehiculo: TipoVehiculo): JsValue = {
      Json.obj(
        "tipoVehiculo" -> tipoVehiculo.descripcion
      )
    }
  }

  implicit val formatInformacionVehiculoParqueado: OWrites[InformacionVehiculoParqueadero] = Json.writes[InformacionVehiculoParqueadero]

  implicit val formatInformacionParqueo: OFormat[RegistroParqueo] = Json.format[RegistroParqueo]

  implicit val mensajeExito: OWrites[MensajeExito] = Json.writes[MensajeExito]

  implicit val placaVehiculo: OFormat[PlacaVehiculoDto] = Json.format[PlacaVehiculoDto]


}
