package dominio.modelos

final case class InformacionParqueo(
                                     placaVehiculo: String,
                                     tipoVehiculo: TipoVehiculo,
                                     esAltoCilindraje: Option[Boolean]
                                   )
