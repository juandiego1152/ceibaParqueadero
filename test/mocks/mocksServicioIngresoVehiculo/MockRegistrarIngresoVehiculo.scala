package mocks.mocksServicioIngresoVehiculo

import akka.Done
import aplicacion.Dependencias
import cats.data.{EitherT, Reader}
import infraestructura.FalseConfigurations
import org.specs2.mock.Mockito
import play.api.Application
trait MockRegistrarIngresoVehiculo extends Mockito {

  //  def mockearFuncionGuardarCuentaHabienteError( dependencia: Dependencias ) = {
  //    dependencia.servicioParqueadero.registrarIngresoVehiculo(anyObject) returns Reader { _ =>
  //      EitherT.leftT( MensajeError( Tecnico, "Hubo un error guardando los datos de la cuenta-Habiente" ) )
  //    }
  //  }
  //
  def mockearFuncionGuardarCuentaHabienteExito(dependencia: FalseConfigurations) = {
      dependencia.servicioParqueadero.registrarIngresoVehiculo(anyObject) returns Reader { _ =>
        EitherT.rightT(Done)
      }
    }

}

