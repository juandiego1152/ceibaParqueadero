package infraestructura.repositorios.registros

import java.sql.Timestamp

case class InformacionParqueoRegistro(
                                       placaVehiculo: String,
                                       tipoVehiculo: String,
                                       esAltoCilindraje: Boolean,
                                       horaFechaEntradaVehiculo: Timestamp
                                     )
