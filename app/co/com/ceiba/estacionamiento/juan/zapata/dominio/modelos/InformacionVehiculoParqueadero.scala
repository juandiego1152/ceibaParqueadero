package dominio.modelos

import java.sql.Timestamp

final case class InformacionVehiculoParqueadero(
                                                 placaVehiculo: String,
                                                 tipoVehiculo: TipoVehiculo,
                                                 cilindraje: Int,
                                                 horaFechaIngresoVehiculo: Timestamp
                                               )

