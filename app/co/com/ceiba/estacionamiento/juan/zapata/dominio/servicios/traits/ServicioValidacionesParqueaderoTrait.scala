package dominio.servicios.traits

import akka.Done
import aplicacion.FormatoEither
import dominio.modelos.{InformacionVehiculoParqueadero, RegistroParqueo}

trait ServicioValidacionesParqueaderoTrait {

  def validarPermiteIngresarVehiculos(informacionParqueo: RegistroParqueo, cantidadVehiculosRegistrados: Int): FormatoEither[Done]

  def generarValorServicioParqueo(registroParqueo: InformacionVehiculoParqueadero): Double
}
