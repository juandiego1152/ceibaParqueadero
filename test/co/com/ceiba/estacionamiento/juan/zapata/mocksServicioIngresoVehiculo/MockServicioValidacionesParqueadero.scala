package co.com.ceiba.estacionamiento.juan.zapata.mocksServicioIngresoVehiculo

import akka.Done
import infraestructura.FalseConfigurations
import infraestructura.configuracion.{MensajeError, Tecnico}
import org.specs2.mock.Mockito

trait MockServicioValidacionesParqueadero extends Mockito {

  def mockearFuncionValidarPermiteIngresarVehiculos(dependencia: FalseConfigurations) = {
    dependencia.servicioValidacionesParqueadero.validarPermiteIngresarVehiculos(anyObject, anyInt) returns Right(Done)
  }

  def mockearFuncionValidarPermiteIngresarVehiculosError(dependencia: FalseConfigurations) = {
    dependencia.servicioValidacionesParqueadero.validarPermiteIngresarVehiculos(anyObject, anyInt) returns
      Left(MensajeError(Tecnico, "Hubo un error en el metodo"))
  }

 def mockearFuncionGenerarValorServicioParqueo(dependencia: FalseConfigurations, valorGenerado: Double) = {
    dependencia.servicioValidacionesParqueadero.generarValorServicioParqueo(anyObject) returns valorGenerado
  }
}

