package aplicacion.servicios.traits

import co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores._
import akka.Done
import aplicacion.dtos.PlacaVehiculoDto
import cats.data.Reader
import co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores.Dependencias
import dominio.modelos.RegistroParqueo

trait ServicioParqueaderoTraits {

  def registrarIngresoVehiculo(registroVehiculo: RegistroParqueo): Reader[Dependencias, FormatoEitherT[Done]]

  def registrarSalidaVehiculo(placaVehiculo: PlacaVehiculoDto): Reader[Dependencias, FormatoEitherT[Double]]
}



