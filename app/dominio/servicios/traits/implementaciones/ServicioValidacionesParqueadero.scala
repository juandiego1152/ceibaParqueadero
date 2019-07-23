package dominio.servicios.traits.implementaciones

import java.util.Calendar

import akka.Done
import aplicacion.FormatoEither
import cats.implicits._
import dominio.contantes.Constantes._
import dominio.modelos.{Carro, InformacionParqueo, Motocicleta}
import dominio.servicios.traits.ServicioValidacionesParqueaderoTraits
import infraestructura.configuracion.{MensajeError, Negocio}

object ServicioValidacionesParqueadero extends ServicioValidacionesParqueaderoTraits {

  def validarPermiteIngresarVehiculos(informacionParqueo: InformacionParqueo, cantidadVehiculosRegistrados: Int): FormatoEither[Done] = {
    informacionParqueo.tipoVehiculo match {
      case Carro => validacionesParaCarros(informacionParqueo.placaVehiculo, cantidadVehiculosRegistrados)
      //Otra forma de realizar la condicion.
      case Motocicleta => if (cantidadVehiculosRegistrados >= CantidadMaximaMotos) MensajeError(Negocio, "No hay capacidad en celdas para mas vehiculos").asLeft else Done.asRight
      case _ => MensajeError(Negocio, "No se tiene espacios disponibles para otras categorias").asLeft
    }
  }

  private[servicios] def validacionesParaCarros(placa: String, cantidadVehiculosRegistrados: Int): FormatoEither[Done] = {
    val diaActual = Calendar.getInstance.get(Calendar.DAY_OF_WEEK) - 1
    if (cantidadVehiculosRegistrados >= CantidadMaximaCarros)
      MensajeError(Negocio, "No hay capacidad en celdas para mas vehiculos").asLeft
    else if (PlacaVehiculosRestringidosDias.exists(_.contains(placa.charAt(0))) &&
      DiasPermitidosDeCarrosConRestriccionDePlaca.exists(_ == (diaActual)))
      MensajeError(Negocio, "No se permite el ingreso de este vehiculo").asLeft
    else
      Done.asRight
  }


  def generarValorServicioParqueo(placa: String): FormatoEither[Double] = {
    ???
  }

}
