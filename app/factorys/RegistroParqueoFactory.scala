package factorys

import dominio.modelos.{Carro, Motocicleta, RegistroParqueo, TipoVehiculo}

object RegistroParqueoFactory {

  def registroParqueoCarro = RegistroParqueo("ALS21D", Carro, None)

  def registroParqueoMotoAltoCilindraje = RegistroParqueo("ALS21D", Motocicleta, Some(true))

  def registroParqueoMotoBajoCilindraje = RegistroParqueo("ALS21D", Motocicleta, Some(false))

  def registroParqueoSinCategoriaValida = RegistroParqueo("ALS21D", TipoVehiculo("Volqueta"), Some(false))

}
