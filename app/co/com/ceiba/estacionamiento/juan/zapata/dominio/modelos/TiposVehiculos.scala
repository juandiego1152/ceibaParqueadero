package dominio.modelos

trait TipoVehiculo {
  def descripcion: String
}

object TipoVehiculo {
  def apply(tipo: String): TipoVehiculo = tipo match {
    case "Motocicleta" => Motocicleta
    case "Carro" => Carro
    case _ => SinCategoria
  }
}

object Motocicleta extends TipoVehiculo {
  override def descripcion: String = "Motocicleta"
}

object Carro extends TipoVehiculo {
  override def descripcion: String = "Carro"
}

object SinCategoria extends TipoVehiculo {
  override def descripcion: String = "Sin Categoria"
}

