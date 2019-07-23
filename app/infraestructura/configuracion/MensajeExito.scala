package infraestructura.configuracion

import org.joda.time.DateTime

case class MensajeExito(
                         date: DateTime,
                         message: String
                       )