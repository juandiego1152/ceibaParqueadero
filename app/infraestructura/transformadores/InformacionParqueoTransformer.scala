package infraestructura.transformadores

import java.sql.Timestamp

import dominio.modelos.{InformacionParqueo, TipoVehiculo}
import infraestructura.repositorios.registros.InformacionParqueoRegistro

object InformacionParqueoTransformer {

  def aInformacionParqueoRegistro(informacion: InformacionParqueo, horaFechaRegistro: Timestamp) = InformacionParqueoRegistro(
    informacion.placaVehiculo,
    informacion.tipoVehiculo.descripcion,
    informacion.esAltoCilindraje.getOrElse(false),
    horaFechaRegistro
  )

  def aInformacionParqueoDto(informacion: InformacionParqueoRegistro) = InformacionParqueo(
    informacion.placaVehiculo,
    TipoVehiculo(informacion.tipoVehiculo),
    Some(informacion.esAltoCilindraje)
  )

}
