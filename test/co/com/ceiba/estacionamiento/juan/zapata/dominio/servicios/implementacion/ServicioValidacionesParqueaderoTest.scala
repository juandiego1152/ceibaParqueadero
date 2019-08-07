package co.com.ceiba.estacionamiento.juan.zapata.dominio.servicios.implementacion

import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import akka.Done
import cats.implicits._
import co.com.ceiba.estacionamiento.juan.zapata.factorys.InformacionRegistroParqueoFactory._
import co.com.ceiba.estacionamiento.juan.zapata.factorys.RegistroParqueoFactory._
import dominio.contantes.Constantes._
import dominio.servicios.implementaciones.ServicioValidacionesParqueadero
import infraestructura.configuracion.{MensajeError, Negocio}

class mockServicioValidacionesParqueadero extends ServicioValidacionesParqueadero

class ServicioValidacionesParqueaderoTest extends PlaySpec with Mockito {

  "Al usar el metodo: validarPermiteIngresarVehiculos" when {

    "El tipo de vehiculo es moto y hay espacios disponibles  en el parqueadero" must {
      "Permitir el ingreso del vehiculo" in {
        val registroParqueo = registroParqueoMotoBajoCilindraje
        val cantidadVehiculosRegistrados = 5
        val respuesta = ServicioValidacionesParqueadero.validarPermiteIngresarVehiculos(registroParqueo, cantidadVehiculosRegistrados)
        respuesta mustBe Done.asRight
      }
    }

    "El tipo de vehiculo es moto y NO hay espacios disponibles en el parqueadero" must {
      "Sacar el mensaje generado" in {
        val registroParqueo = registroParqueoMotoBajoCilindraje
        val cantidadVehiculosRegistrados = 20
        val respuesta = ServicioValidacionesParqueadero.validarPermiteIngresarVehiculos(registroParqueo, cantidadVehiculosRegistrados)
        respuesta mustBe MensajeError(Negocio, "No hay capacidad en celdas para mas vehiculos").asLeft
      }
    }

    "El tipo de vehiculo es carro, tiene placa permitida para el ingreso y hay espacios disponibles" must {
      "Permitir el ingreso del vehiculo" in {
        val registroParqueo = registroParqueoCarro
        val cantidadVehiculosRegistrados = 5
        val spyServicio = spy(new mockServicioValidacionesParqueadero)

        doReturn(Done.asRight).when(spyServicio).validacionesParaCarros(anyString, anyInt)

        val respuesta = spyServicio.validarPermiteIngresarVehiculos(registroParqueo, cantidadVehiculosRegistrados)
        respuesta mustBe Done.asRight
      }
    }

    "El tipo de vehiculo es carro, tiene placa permitida para el ingreso y NO hay espacios disponibles" must {
      "Sacar el mensaje generado" in {
        val registroParqueo = registroParqueoCarro
        val cantidadVehiculosRegistrados = 5
        val spyServicio = spy(new mockServicioValidacionesParqueadero)
        doReturn(MensajeError(Negocio, "No hay capacidad en celdas para mas vehiculos").asLeft).when(spyServicio).validacionesParaCarros(anyString, anyInt)
        val respuesta = spyServicio.validarPermiteIngresarVehiculos(registroParqueo, cantidadVehiculosRegistrados)
        respuesta mustBe MensajeError(Negocio, "No hay capacidad en celdas para mas vehiculos").asLeft
      }
    }

    "El tipo de vehiculo es carro, y tiene placa No permitida para el ingreso de ese dia" must {
      "Sacar el mensaje generado" in {
        val registroParqueo = registroParqueoCarro
        val cantidadVehiculosRegistrados = 5
        val spyServicio = spy(new mockServicioValidacionesParqueadero)
        doReturn(MensajeError(Negocio, "No se permite el ingreso de este vehiculo").asLeft).when(spyServicio).validacionesParaCarros(anyString, anyInt)
        val respuesta = spyServicio.validarPermiteIngresarVehiculos(registroParqueo, cantidadVehiculosRegistrados)
        respuesta mustBe MensajeError(Negocio, "No se permite el ingreso de este vehiculo").asLeft
      }
    }

    "El tipo de vehiculo no es una categoria valida" must {
      "Sacar el mensaje generado" in {
        val registroParqueo = registroParqueoSinCategoriaValida
        val cantidadVehiculosRegistrados = 5
        val respuesta = ServicioValidacionesParqueadero.validarPermiteIngresarVehiculos(registroParqueo, cantidadVehiculosRegistrados)
        respuesta mustBe MensajeError(Negocio, "No se tiene espacios disponibles para otras categorias").asLeft
      }
    }
  }

  "Al usar el metodo: generarValorServicioParqueo" when {

    "Un vehiculo cumple dos horas en el parqueadero" must {
      "Sacar el valor generado" in {
        val registroParqueo = registroParqueoCarro2Horas
        val respuesta = ServicioValidacionesParqueadero.generarValorServicioParqueo(registroParqueo)
        respuesta mustBe ValorHoraCarro * 2
      }
    }

    "Una moto de alto cilindraje cumple dos horas en el parqueadero" must {
      "Sacar el valor generado con el valor extra por ser alto cilindraje" in {
        val registroParqueo = registroParqueoMoto2HorasAltoCilindraje
        val respuesta = ServicioValidacionesParqueadero.generarValorServicioParqueo(registroParqueo)
        respuesta mustBe ValorHoraMoto * 2 + ValorExtraPorAltoCilindraje
      }
    }

    "Una moto de bajo cilindraje cumple dos horas en el parqueadero" must {
      "Sacar el valor generado" in {
        val registroParqueo = registroParqueoMoto2HorasBajoCilindraje
        val respuesta = ServicioValidacionesParqueadero.generarValorServicioParqueo(registroParqueo)
        respuesta mustBe ValorHoraMoto * 2
      }
    }

    "Una moto cumple varios dias y algunas horas adentro del parqueadero y es alto cilindraje" must {
      "Sacar el valor generado los dias y las horas extras" in {
        val registroParqueo = registroParqueoMoto1Dia3HorasAltoCilindraje
        val respuesta = ServicioValidacionesParqueadero.generarValorServicioParqueo(registroParqueo)
        respuesta mustBe (ValorDiaMoto + (ValorHoraMoto * 3) + ValorExtraPorAltoCilindraje)
      }
    }

    "Un vehiculo No cumple las horas minimas para el cobro del dia" must {
      "Sacar el valor generado cobrando las horas que accedio al servicio" in {
        val registroParqueo = registroParqueoCarroHorasMaximasParaCobroHoras
        val respuesta = ServicioValidacionesParqueadero.generarValorServicioParqueo(registroParqueo)
        respuesta mustBe (ValorHoraCarro * (CantidadHorasMinimasCobroDia - 1))
      }
    }

    "Un carro cumple las horas minimas para el cobro del dia" must {
      "Sacar el valor generado por el valor del dia" in {
        val registroParqueo = registroParqueoCarroHorasMaximasParaCobroDia
        val respuesta = ServicioValidacionesParqueadero.generarValorServicioParqueo(registroParqueo)
        respuesta mustBe ValorDiaCarro
      }
    }

    "Un carro cumple varios dias y algunas horas adentro del parqueadero" must {
      "Sacar el valor generado los dias y las horas extras" in {
        val registroParqueo = registroParqueoCarro1Dia3Horas
        val respuesta = ServicioValidacionesParqueadero.generarValorServicioParqueo(registroParqueo)
        respuesta mustBe (ValorDiaCarro + (ValorHoraCarro * 3))
      }
    }

    "Un vehiculo cumple 5 minutos de estar adentro del parqueadero" must {
      "Sacar el valor generado por la hora" in {
        val registroParqueo = registroParqueoCarro5Minutos
        val respuesta = ServicioValidacionesParqueadero.generarValorServicioParqueo(registroParqueo)
        respuesta mustBe ValorHoraCarro
      }
    }
  }
}
