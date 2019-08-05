package aplicacion.servicios.traits

import aplicacion.FormatoEitherT
import akka.Done
import aplicacion.Dependencias
import aplicacion.dtos.PlacaVehiculoDto
import cats.data.Reader
import dominio.modelos.RegistroParqueo

trait ServicioParqueaderoTraits {

  def registrarIngresoVehiculo(registroVehiculo: RegistroParqueo): Reader[Dependencias, FormatoEitherT[Done]]

  def registrarSalidaVehiculo(placaVehiculo: PlacaVehiculoDto): Reader[Dependencias, FormatoEitherT[Double]]
}



