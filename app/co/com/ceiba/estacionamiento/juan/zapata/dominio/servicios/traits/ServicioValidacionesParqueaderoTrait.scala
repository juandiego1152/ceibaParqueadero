package dominio.servicios.traits

import akka.Done
import co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores._
import dominio.modelos.{InformacionVehiculoParqueadero, RegistroParqueo}

trait ServicioValidacionesParqueaderoTrait {

  def validarPermiteIngresarVehiculos(informacionParqueo: RegistroParqueo, cantidadVehiculosRegistrados: Int): FormatoEither[Done]

  def generarValorServicioParqueo(registroParqueo: InformacionVehiculoParqueadero): Double
}
