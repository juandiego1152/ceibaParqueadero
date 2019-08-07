package co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores.consultas

import aplicacion.dtos.FormatosHttpDto._
import aplicacion.servicios.ErrorServicio
import co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores._
import co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores.comandos.CommandHelper
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}

@Singleton
case class ControladorConsultaVehiculosRegistrados @Inject()(dependencias: Dependencias, controllerComponents: ControllerComponents) extends BaseController with CommandHelper {

  def execute: Action[AnyContent] = Action.async(parse.anyContent) {
    Logger.logger.debug("Entro al comando consulta de vehiculos")

    implicit request =>
      dependencias.repoParqueadero.consultarVehiculosRegistrados().run(dependencias.databaseConfig)
        .fold(
          error => {
            Logger.logger.error(error.tipoError.toString + error.mensaje)
            ErrorServicio.generarHttpError(error)
          },
          valorServicio => Ok(Json.toJson(valorServicio))
        ).runToFuture
  }

}
