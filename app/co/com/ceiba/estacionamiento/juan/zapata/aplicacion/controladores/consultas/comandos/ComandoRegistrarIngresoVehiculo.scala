package co.com.ceiba.estacionamiento.juan.zapata.comandos

import co.com.ceiba.estacionamiento.juan.zapata.factorys.aplicacion._
import co.com.ceiba.estacionamiento.juan.zapata.factorys.aplicacion.Dependencias
import co.com.ceiba.estacionamiento.juan.zapata.factorys.aplicacion.dtos.FormatosHttpDto._
import co.com.ceiba.estacionamiento.juan.zapata.factorys.aplicacion.servicios.ErrorServicio
import co.com.ceiba.estacionamiento.juan.zapata.factorys.dominio.modelos.RegistroParqueo
import co.com.ceiba.estacionamiento.juan.zapata.factorys.infraestructura.configuracion.MensajeExito
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
