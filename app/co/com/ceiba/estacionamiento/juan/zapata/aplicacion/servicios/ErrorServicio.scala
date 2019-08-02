package aplicacion.servicios

import infraestructura.configuracion._
import play.api.mvc.Result
import play.api.mvc.Results.{BadRequest, InternalServerError}

trait ErrorServicio {

  def generarMensajeErrorUnico(errors: List[MensajeError] ): MensajeError = {
    val tipoError = definirTipoErrores( errors )
    val mensaje = errors.map(_.mensaje).mkString("\n- ")
    MensajeError( tipoError, "- " + mensaje )
  }

  def generarHttpError(error: MensajeError ): Result = {
    error.tipoError match {
      case Negocio | Aplicacion => BadRequest( error.mensaje )
      case _                      => InternalServerError( error.mensaje )
    }
  }

  private[servicios] def definirTipoErrores( errores: List[MensajeError] ): TipoError = {
    val tipoErrores = errores.map( _.tipoError )
    ( tipoErrores.contains( Negocio ), tipoErrores.contains( Aplicacion ), tipoErrores.contains( Tecnico ) ) match {
      case ( true, _, _ ) => Negocio
      case ( _, true, _ ) => Aplicacion
      case ( _, _, true ) => Tecnico
      case _              => Aplicacion
    }
  }
}

object ErrorServicio extends ErrorServicio
