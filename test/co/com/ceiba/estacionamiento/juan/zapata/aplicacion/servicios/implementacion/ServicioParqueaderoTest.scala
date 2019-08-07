package co.com.ceiba.estacionamiento.juan.zapata.aplicacion.servicios.implementacion

import akka.Done
import aplicacion.dtos.PlacaVehiculoDto
import aplicacion.servicios.implementacion.ServicioParqueadero
import cats.implicits._
import co.com.ceiba.estacionamiento.juan.zapata.factorys.RegistroParqueoFactory._
import co.com.ceiba.estacionamiento.juan.zapata.mocksServicioIngresoVehiculo.{MockRepositorioParqueadero, MockServicioValidacionesParqueadero}
import infraestructura.FalseConfigurations
import org.mockito.Mockito.verify
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores._
import co.com.ceiba.estacionamiento.juan.zapata.factorys.InformacionRegistroParqueoFactory._
import dominio.modelos.{SinCategoria, TipoVehiculo}
import infraestructura.configuracion.{Aplicacion, MensajeError, Tecnico}

class mockServicioParqueadero extends ServicioParqueadero

class ServicioParqueaderoTest extends PlaySpec with Mockito with MockRepositorioParqueadero with MockServicioValidacionesParqueadero{

  "Al usar el metodo: registrarIngresoVehiculo" when {

    "Guardamo un nuevo registro" must {
      "Retornar Done" in {
        val dependencia = new FalseConfigurations(null, null, null, null)
        val spyServicio = spy(new mockServicioParqueadero)

        val registroParqueo = registroParqueoMotoBajoCilindraje
        val cantidadVehiculosRegistrados = 5

        mockearFuncionConsultarCantidadVehiculosRegistrados(dependencia,cantidadVehiculosRegistrados)
        mockearFuncionValidarPermiteIngresarVehiculos(dependencia)
        doReturn(Done.asRight).when(spyServicio).validarInformacion(anyObject)
        mockearFuncionGuardarRegistroParqueadero(dependencia)

        val respuesta = Await.result(spyServicio.registrarIngresoVehiculo(registroParqueo).run(dependencia).value.runToFuture, Duration.Inf)

        respuesta mustBe Done.asRight

        verify(  dependencia.repoParqueadero ).consultarCantidadVehiculosRegistrados( anyObject)
        verify(  dependencia.servicioValidacionesParqueadero).validarPermiteIngresarVehiculos( anyObject, anyInt)
        verify( spyServicio ).validarInformacion( anyObject )
        verify(  dependencia.repoParqueadero).guardarRegistroParqueadero( anyObject, anyObject)
      }
    }

    "Guardamo un nuevo registro y ocurre un error" must {
      "Retornar el error generado" in {
        val dependencia = new FalseConfigurations(null, null, null, null)
        val spyServicio = spy(new mockServicioParqueadero)

        val registroParqueo = registroParqueoMotoBajoCilindraje

        val cantidadVehiculosRegistrados = 5

        mockearFuncionConsultarCantidadVehiculosRegistrados(dependencia, cantidadVehiculosRegistrados)
        mockearFuncionValidarPermiteIngresarVehiculosError(dependencia)

        val respuesta = Await.result(spyServicio.registrarIngresoVehiculo(registroParqueo).run(dependencia).value.runToFuture, Duration.Inf)

        respuesta mustBe MensajeError(Tecnico, "Hubo un error en el metodo").asLeft

        verify(  dependencia.repoParqueadero ).consultarCantidadVehiculosRegistrados( anyObject)
      }
    }
  }

  "Al usar el metodo: validarInformacion" when {

    "Validamos la informacion registrada" must {
      "Retornar Done" in {
        val spyServicio = spy(new mockServicioParqueadero)

        val registroParqueo = registroParqueoMotoBajoCilindraje

        val respuesta = spyServicio.validarInformacion(registroParqueo)

        respuesta mustBe Done.asRight
      }
    }

    "Validamos la informacion registrada y ocurre un error" must {
      "Retornar el error generado" in {
        val spyServicio = spy(new mockServicioParqueadero)

        val registroParqueo = registroParqueoMotoBajoCilindraje.copy(placaVehiculo = "ALS21DD")

        val respuesta = spyServicio.validarInformacion(registroParqueo)

        respuesta mustBe (MensajeError(Aplicacion, "- Hay un error en el formato de la placa")).asLeft
      }
    }

    "Validamos la informacion registrada y ocurre un error en la categoria del vehiculo" must {
      "Retornar el error generado" in {
        val spyServicio = spy(new mockServicioParqueadero)

        val registroParqueo = registroParqueoMotoBajoCilindraje.copy(tipoVehiculo = SinCategoria)

        val respuesta = spyServicio.validarInformacion(registroParqueo)

        respuesta mustBe MensajeError(Aplicacion, "- El vehiculo no tiene una categoria aceptable").asLeft
      }
    }

  }

  "Al usar el metodo: salidaVehiculo" when {

    "Generamos una nueva salida" must {
      "Retornar el valor del servicio prestado" in {
        val dependencia = new FalseConfigurations(null, null, null, null)
        val spyServicio = spy(new mockServicioParqueadero)

        val infoParqueadero = registroParqueoCarro2Horas
        val valorGenerado = 11000d

        mockearFuncionConsultarVehiculoRegistrado(dependencia,infoParqueadero)
        mockearFuncionEliminarRegistroParqueadero(dependencia)
        mockearFuncionGenerarValorServicioParqueo(dependencia,valorGenerado)

        val respuesta = Await.result(spyServicio.salidaVehiculo(PlacaVehiculoDto("PLACA01")).run(dependencia).value.runToFuture, Duration.Inf)

        respuesta mustBe valorGenerado.asRight

        verify(  dependencia.repoParqueadero ).consultarVehiculoRegistrado( anyObject)
        verify(  dependencia.repoParqueadero).eliminarRegistroParqueadero( anyString)
        verify(  dependencia.servicioValidacionesParqueadero).generarValorServicioParqueo(anyObject)
      }
    }

    "Generamos una nueva salida pero ocurre un error" must {
      "Retornar el error generado" in {
        val dependencia = new FalseConfigurations(null, null, null, null)
        val spyServicio = spy(new mockServicioParqueadero)

        mockearFuncionConsultarVehiculoRegistradoError(dependencia)

        val respuesta = Await.result(spyServicio.salidaVehiculo(PlacaVehiculoDto("PLACA01")).run(dependencia).value.runToFuture, Duration.Inf)

        respuesta mustBe MensajeError(Tecnico, "Hubo un error consultando el registro").asLeft

        verify(  dependencia.repoParqueadero ).consultarVehiculoRegistrado( anyObject)
      }
    }

    "Generamos una nueva salida pero no encuentra ningun registro" must {
      "Retornar el error generado" in {
        val dependencia = new FalseConfigurations(null, null, null, null)
        val spyServicio = spy(new mockServicioParqueadero)

        mockearFuncionConsultarVehiculoRegistradoVacio(dependencia)

        val respuesta = Await.result(spyServicio.salidaVehiculo(PlacaVehiculoDto("PLACA01")).run(dependencia).value.runToFuture, Duration.Inf)

        respuesta mustBe MensajeError(Aplicacion, "No se encontr\u00F3 ningun registro con esta placa").asLeft

        verify(  dependencia.repoParqueadero ).consultarVehiculoRegistrado( anyObject)
      }
    }
  }
}
