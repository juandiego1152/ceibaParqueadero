package co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores.comandos

import aplicacion.Dependencias
import aplicacion.dtos.PlacaVehiculoDto
import aplicacion.servicios.ErrorServicio
import aplicacion.dtos.FormatosHttpDto._
import infraestructura.configuracion.MensajeExito
import javax.inject.{Inject, Singleton}
import org.joda.time.DateTime
import play.api.Logger
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import play.api.libs.json._
import aplicacion._

@Singleton
case class ComandoSalidaDeVehiculo @Inject()(dependencias: Dependencias, controllerComponents: ControllerComponents) extends BaseController with CommandHelper {

  def execute: Action[AnyContent] = Action.async(parse.anyContent) {
    Logger.logger.debug("Entro al comando salida de vehiculo")
    implicit request =>

      request.obtenerDatosComoEither[PlacaVehiculoDto].aFormatoEitherT
        .flatMap {
          dependencias.servicioParqueadero.registrarSalidaVehiculo(_).run(dependencias)
        }
        .fold(
          error => {
            Logger.logger.error(error.tipoError.toString + error.mensaje)
            ErrorServicio.generarHttpError(error)
          },
          valorServicio => Ok(Json.toJson(MensajeExito(date = new DateTime(), message = s"El valor del servicio prestado es: $valorServicio")))
        ).runToFuture
  }

}
