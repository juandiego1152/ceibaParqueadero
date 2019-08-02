package dominio.modelos

final case class RegistroParqueo(
                                  placaVehiculo: String,
                                  tipoVehiculo: TipoVehiculo,
                                  esAltoCilindraje: Option[Boolean]
                                )


