package aplicacion.controladores.consultas

import aplicacion.controladores.comandos.CommandHelper
import aplicacion.servicios.ErrorServicio
import aplicacion.{Dependencias, _}
import javax.inject.{Inject, Singleton}
import play.api.Logger
import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import aplicacion.dtos.FormatosHttpDto._

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
