package infraestructura.transformadores

import java.sql.Timestamp

import dominio.modelos.{RegistroParqueo, TipoVehiculo, InformacionVehiculoParqueadero}
import infraestructura.repositorios.registros.InformacionParqueoRegistro

object InformacionParqueoTransformer {

  def aInformacionParqueoRegistro(informacion: RegistroParqueo, horaFechaRegistro: Timestamp) = InformacionParqueoRegistro(
    informacion.placaVehiculo,
    informacion.tipoVehiculo.descripcion,
    informacion.esAltoCilindraje.getOrElse(false),
    horaFechaRegistro
  )

  def aInformacionVehiculoParqueadero(informacion: InformacionParqueoRegistro) = InformacionVehiculoParqueadero(
    informacion.placaVehiculo,
    TipoVehiculo(informacion.tipoVehiculo),
    informacion.esAltoCilindraje,
    informacion.horaFechaIngresoVehiculo
  )

}
