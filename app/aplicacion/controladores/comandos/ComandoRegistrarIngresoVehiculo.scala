package aplicacion.controladores.comandos

import aplicacion._
import aplicacion.Dependencias
import aplicacion.dtos.FormatosHttpDto._
import aplicacion.servicios.ErrorServicio
import dominio.modelos.RegistroParqueo
import infraestructura.configuracion.MensajeExito
import javax.inject.{Inject, Singleton}
import org.joda.time.DateTime
import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

@Singleton
case class ComandoRegistrarIngresoVehiculo @Inject()(dependencias: Dependencias, controllerComponents: ControllerComponents) extends BaseController with CommandHelper {

  def execute: Action[AnyContent] = Action.async(parse.anyContent) {

    implicit request =>

      request.obtenerDatosComoEither[RegistroParqueo].aFormatoEitherT
        .flatMap {
          dependencias.servicioParqueadero.registrarIngresoVehiculo(_).run(dependencias)
        }
        .fold(
          error => {
            Logger.logger.error(error.tipoError.toString + error.mensaje)
            ErrorServicio.generarHttpError(error)
          },
          _ => Ok(Json.toJson(MensajeExito(date = new DateTime(), message = "Datos guardados con \u00E9xito")))
        ).runToFuture
  }

}
