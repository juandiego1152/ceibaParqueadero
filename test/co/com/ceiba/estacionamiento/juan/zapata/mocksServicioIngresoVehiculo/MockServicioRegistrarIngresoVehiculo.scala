package mocks.mocksServicioIngresoVehiculo

import akka.Done
import cats.data.{EitherT, Reader}
import infraestructura.FalseConfigurations
import infraestructura.configuracion.{MensajeError, Tecnico}
import org.specs2.mock.Mockito

trait MockServicioRegistrarIngresoVehiculo extends Mockito {

  def mockearFuncionGuardarRegistroVehiculoError(dependencia: FalseConfigurations) = {
    dependencia.servicioParqueadero.registrarIngresoVehiculo(any) returns Reader { _ =>
      EitherT.leftT(MensajeError(Tecnico, "Hubo un error guardando el registro"))
    }
  }

  def mockearFuncionGuardarRegistroVehiculo(dependencia: FalseConfigurations) = {
    dependencia.servicioParqueadero.registrarIngresoVehiculo(any) returns Reader { _ =>
      EitherT.rightT(Done)
    }
  }
}

