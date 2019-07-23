package aplicacion.controladores.comandos

import aplicacion.FormatoEither
import cats.implicits._
import infraestructura.configuracion.{Aplicacion, MensajeError}
import play.api.libs.json.{JsError, JsSuccess, Json, Reads}
import play.api.mvc.{AnyContent, Request}

trait CommandHelper {

  implicit class RequestAFormatoEither(val request: Request[AnyContent] ) {
    def obtenerDatosComoEither[T](implicit reads: Reads[T] ): FormatoEither[T] = {
      request.body.asJson match {
        case Some ( json ) => Json.fromJson [T]( json ) match {
          case JsSuccess ( objeto, _ ) => objeto.asRight
          case JsError ( error ) =>
            val mensaje = "- " + error.map ( v => v._1.toJsonString + " " + v._2.map ( _.message ).mkString ( "\n- " ) ).mkString ( "- " ).replace ( '.', ' ' )
            MensajeError ( Aplicacion, mensaje ).asLeft
        }
        case None => MensajeError ( Aplicacion, "Hub\u00F3 un error al leer la informaci\u00F3n de la petici\u00F3n" ).asLeft
      }
    }
  }

}
