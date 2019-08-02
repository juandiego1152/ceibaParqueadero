package dominio.servicios.implementaciones

import java.sql.Timestamp
import java.util.Calendar
import java.util.concurrent.TimeUnit

import akka.Done
import aplicacion.FormatoEither
import cats.implicits._
import dominio.contantes.Constantes._
import dominio.modelos.{Carro, InformacionVehiculoParqueadero, Motocicleta, RegistroParqueo}
import dominio.servicios.traits.ServicioValidacionesParqueaderoTrait
import infraestructura.configuracion.{MensajeError, Negocio}

trait ServicioValidacionesParqueadero extends ServicioValidacionesParqueaderoTrait {

  override def validarPermiteIngresarVehiculos(informacionParqueo: RegistroParqueo, cantidadVehiculosRegistrados: Int): FormatoEither[Done] = {
    informacionParqueo.tipoVehiculo match {
      case Carro => validacionesParaCarros(informacionParqueo.placaVehiculo, cantidadVehiculosRegistrados)
      case Motocicleta => if (cantidadVehiculosRegistrados >= CantidadMaximaMotos) MensajeError(Negocio, "No hay capacidad en celdas para mas vehiculos").asLeft else Done.asRight
      case _ => MensajeError(Negocio, "No se tiene espacios disponibles para otras categorias").asLeft
    }
  }

  def validacionesParaCarros(placa: String, cantidadVehiculosRegistrados: Int): FormatoEither[Done] = {
    val diaActual = Calendar.getInstance.get(Calendar.DAY_OF_WEEK) - 1
    if (cantidadVehiculosRegistrados >= CantidadMaximaCarros)
      MensajeError(Negocio, "No hay capacidad en celdas para mas vehiculos").asLeft
    else if (PlacaVehiculosRestringidosDias.exists(_.contains(placa.charAt(0))) &&
      DiasPermitidosDeCarrosConRestriccionDePlaca.exists(_ == (diaActual)))
      MensajeError(Negocio, "No se permite el ingreso de este vehiculo").asLeft
    else
      Done.asRight
  }


  override def generarValorServicioParqueo(registroParqueo: InformacionVehiculoParqueadero): Double = {
    val horaFechaActual = new Timestamp(System.currentTimeMillis())
    val diferencia = (horaFechaActual.getTime - registroParqueo.horaFechaIngresoVehiculo.getTime)
    val minutos = TimeUnit.MILLISECONDS.toMinutes(diferencia)
    //    val minutos = (registroParqueo.horaFechaIngresoVehiculo.getTime - horaFechaActual.getTime)/1000

    val diasYHorasParqueado = validarTiempoVehiculoParqueado(minutos.toInt)

    valorFinalServicio(registroParqueo, diasYHorasParqueado)
  }

  private[servicios] def validarTiempoVehiculoParqueado(minutos: Int): (Int, Int) = {
    val horas = (minutos / 60)
    val minutosRestantes = minutos - (horas * 60)

    val horaExtraPorMinutos = if (minutosRestantes > MinutosMaximosNuevaHora) 1 else 0

    val horasServicio = horas + horaExtraPorMinutos

    val diasServicio = calcularDiasServicioParqueaderoPrestado(horasServicio)
    val horasRestantesDelServicio = {
      val horasRestantes = horasServicio - (24 * diasServicio)
      if (horasRestantes > 0) horasRestantes else 0
    }
    (diasServicio, horasRestantesDelServicio)
  }

  private[servicios] def calcularDiasServicioParqueaderoPrestado(horasServicio: Int): Int = {
    if (horasServicio >= CantidadHorasMinimasCobroDia && horasServicio < 25) 1
    else if (horasServicio >= CantidadHorasMinimasCobroDia) {
      1 + calcularDiasServicioParqueaderoPrestado(horasServicio - 24)
    }
    else 0
  }


  private[servicios] def valorFinalServicio(informacionVehiculoParqueadero: InformacionVehiculoParqueadero, diasHorasParqueado: (Int, Int)): Double = {
    val diasParqueado = diasHorasParqueado._1
    val horasParqueado = diasHorasParqueado._2
    informacionVehiculoParqueadero.tipoVehiculo match {
      case Carro => (diasParqueado * ValorDiaCarro) + (horasParqueado * ValorHoraCarro)
      case Motocicleta => {
        val valorParqueo = (diasParqueado * ValorDiaMoto) + (horasParqueado * ValorHoraMoto)
        if (informacionVehiculoParqueadero.esAltoCilindraje)
          valorParqueo + ValorExtraPorAltoCilindraje
        else
          valorParqueo
      }
    }
  }
}

object ServicioValidacionesParqueadero extends ServicioValidacionesParqueadero
