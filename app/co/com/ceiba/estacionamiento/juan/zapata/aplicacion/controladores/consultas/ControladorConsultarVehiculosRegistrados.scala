package co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores.consultas

import co.com.ceiba.estacionamiento.juan.zapata.aplicacion.Dependencias
import co.com.ceiba.estacionamiento.juan.zapata.aplicacion.servicios.ErrorServicio
import co.com.ceiba.estacionamiento.juan.zapata.comandos.CommandHelper
import co.com.ceiba.estacionamiento.juan.zapata.factorys.comandos.CommandHelper
import co.com.ceiba.estacionamiento.juan.zapata.factorys.aplicacion.{Dependencias, _}
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import co.com.ceiba.estacionamiento.juan.zapata.factorys.aplicacion.dtos.FormatosHttpDto._

@Singleton
case class ControladorConsultarVehiculosRegistrados @Inject()(dependencias: Dependencias, controllerComponents: ControllerComponents) extends BaseController with CommandHelper {

  def execute: Action[AnyContent] = Action.async(parse.anyContent) {

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
