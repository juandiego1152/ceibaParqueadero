package co.com.ceiba.estacionamiento.juan.zapata.factorys

import dominio.modelos.{Carro, Motocicleta, RegistroParqueo, TipoVehiculo}

object RegistroParqueoFactory {

  def registroParqueoCarro = RegistroParqueo("ALS21D", Carro, None)

  def registroParqueoMotoAltoCilindraje = RegistroParqueo("ALS21D", Motocicleta, Some(650))

  def registroParqueoMotoBajoCilindraje = RegistroParqueo("ALS21D", Motocicleta, Some(200))

  def registroParqueoSinCategoriaValida = RegistroParqueo("ALS21D", TipoVehiculo("Volqueta"), Some(0))

}
