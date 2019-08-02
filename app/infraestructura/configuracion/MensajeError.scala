package infraestructura.configuracion

final case class MensajeError(tipoError: TipoError, mensaje: String )

sealed trait TipoError

object Aplicacion extends TipoError {
  override def toString: String = "Error de tipo aplicaci\u00F3n"
}

object Tecnico extends TipoError {
  override def toString: String = "Error de tipo t\u00E9cnico"
}

object Negocio extends TipoError {
  override def toString: String = "Error de tipo negocio"
}

object NoIdentificado extends TipoError {
  override def toString: String = "Error de tipo desconocido"
}
