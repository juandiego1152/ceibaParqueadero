package infraestructura.repositorios.registros

import java.sql.Timestamp

case class InformacionParqueoRegistro(
                                       placaVehiculo: String,
                                       tipoVehiculo: String,
                                       cilindraje: Int,
                                       horaFechaIngresoVehiculo: Timestamp
                                     )
