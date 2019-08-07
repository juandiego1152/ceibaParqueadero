package co.com.ceiba.estacionamiento.juan.zapata.mocksServicioIngresoVehiculo

import cats.data.{EitherT, Reader}
import infraestructura.FalseConfigurations
import infraestructura.configuracion.{MensajeError, Tecnico}
import org.specs2.mock.Mockito

trait MockServicioSalidaVehiculo extends Mockito {

  def mockearFuncionRegistrarSalidaVehiculo(dependencia: FalseConfigurations) = {
    dependencia.servicioParqueadero.salidaVehiculo(any) returns Reader { _ =>
      EitherT.rightT(11000d)
    }
  }

  def mockearFuncionRegistrarSalidaVehiculoError(dependencia: FalseConfigurations) = {
    dependencia.servicioParqueadero.salidaVehiculo(any) returns Reader { _ =>
      EitherT.leftT(MensajeError(Tecnico, "Hubo un error en el proceso de salida del vehiculo"))
    }
  }

}

