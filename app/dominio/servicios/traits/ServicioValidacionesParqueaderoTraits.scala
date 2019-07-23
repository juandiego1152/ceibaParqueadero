package dominio.servicios.traits

import akka.Done
import aplicacion.FormatoEither
import dominio.modelos.{InformacionParqueo, TipoVehiculo}

trait ServicioValidacionesParqueaderoTraits {

  def validarPermiteIngresarVehiculos(informacionParqueo: InformacionParqueo, cantidadVehiculosRegistrados: Int): FormatoEither[Done]

  def generarValorServicioParqueo(placa: String): FormatoEither[Double]
}
