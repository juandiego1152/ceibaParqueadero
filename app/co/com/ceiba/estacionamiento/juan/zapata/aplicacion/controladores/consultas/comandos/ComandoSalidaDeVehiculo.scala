package co.com.ceiba.estacionamiento.juan.zapata.comandos

import co.com.ceiba.estacionamiento.juan.zapata.factorys.aplicacion.{Dependencias, _}
import co.com.ceiba.estacionamiento.juan.zapata.factorys.aplicacion.dtos.FormatosHttpDto._
import co.com.ceiba.estacionamiento.juan.zapata.factorys.aplicacion.dtos.placaVehiculoDto
import co.com.ceiba.estacionamiento.juan.zapata.factorys.aplicacion.servicios.ErrorServicio
import co.com.ceiba.estacionamiento.juan.zapata.factorys.dominio.modelos.RegistroParqueo
import co.com.ceiba.estacionamiento.juan.zapata.factorys.infraestructura.configuracion.MensajeExito
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
