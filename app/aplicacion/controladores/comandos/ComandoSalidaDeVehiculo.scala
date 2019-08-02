package aplicacion.controladores.comandos

import aplicacion.{Dependencias, _}
import aplicacion.dtos.FormatosHttpDto._
import aplicacion.dtos.placaVehiculoDto
import aplicacion.servicios.ErrorServicio
import dominio.modelos.RegistroParqueo
import infraestructura.configuracion.MensajeExito
import javax.inject.{Inject, Singleton}
import org.joda.time.DateTime
import play.api.Logger
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import play.api.libs.json._

@Singleton
case class ComandoSalidaDeVehiculo @Inject()(dependencias: Dependencias, controllerComponents: ControllerComponents) extends BaseController with CommandHelper {

  def execute: Action[AnyContent] = Action.async(parse.anyContent) {

    implicit request =>

      request.obtenerDatosComoEither[placaVehiculoDto].aFormatoEitherT
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
