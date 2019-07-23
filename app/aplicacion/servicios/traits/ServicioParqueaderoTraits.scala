package aplicacion.servicios.traits

import aplicacion.FormatoEitherT
import akka.Done
import aplicacion.Dependencias
import cats.data.Reader
import dominio.modelos.InformacionParqueo

trait ServicioParqueaderoTraits {

  def registrarIngresoVehiculo(registroVehiculo: InformacionParqueo): Reader[Dependencias, FormatoEitherT[Done]]

  def registrarSalidaVehiculo(placaVehiculo: String): Reader[Dependencias, FormatoEitherT[Double]]
}



