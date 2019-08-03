package mocks.mocksServicioIngresoVehiculo

import akka.Done
import cats.data.{EitherT, Reader}
import infraestructura.FalseConfigurations
import org.specs2.mock.Mockito

trait MockRegistrarIngresoVehiculo extends Mockito{

//  def mockearFuncionGuardarRegistroVehiculoError(dependencia: Dependencias) = {
//    EitherT.leftT(MensajeError(Tecnico, "Hubo un error guardando los datos de la cuenta-Habiente"))
//  }

  def mockearFuncionGuardarRegistroVehiculo(dependencia: FalseConfigurations) = {
    dependencia.servicioParqueadero.registrarIngresoVehiculo( any ) returns Reader { _ =>
      EitherT.rightT(Done)
    }
  }

}

