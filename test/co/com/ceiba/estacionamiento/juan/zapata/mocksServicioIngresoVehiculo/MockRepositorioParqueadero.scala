package co.com.ceiba.estacionamiento.juan.zapata.mocksServicioIngresoVehiculo

import akka.Done
import cats.data.{EitherT, Reader}
import dominio.modelos.InformacionVehiculoParqueadero
import infraestructura.FalseConfigurations
import infraestructura.configuracion.{MensajeError, Tecnico}
import org.specs2.mock.Mockito

trait MockRepositorioParqueadero extends Mockito {

  def mockearFuncionConsultarVehiculosRegistrados(dependencia: FalseConfigurations, listaVehiculos: List[InformacionVehiculoParqueadero]) = {
    dependencia.repoParqueadero.consultarVehiculosRegistrados() returns Reader { _ =>
      EitherT.rightT(listaVehiculos)
    }
  }

  def mockearFuncionConsultarVehiculosRegistradosError(dependencia: FalseConfigurations) = {
    dependencia.repoParqueadero.consultarVehiculosRegistrados() returns Reader { _ =>
      EitherT.leftT(MensajeError(Tecnico, "Hubo un error consultando los registros"))
    }
  }

  def mockearFuncionConsultarVehiculoRegistrado(dependencia: FalseConfigurations, inforVehiculo: InformacionVehiculoParqueadero) = {
    dependencia.repoParqueadero.consultarVehiculoRegistrado(anyString) returns Reader { _ =>
      EitherT.rightT(Some(inforVehiculo))
    }
  }

  def mockearFuncionConsultarVehiculoRegistradoError(dependencia: FalseConfigurations) = {
    dependencia.repoParqueadero.consultarVehiculoRegistrado(anyString) returns Reader { _ =>
      EitherT.leftT(MensajeError(Tecnico, "Hubo un error consultando el registro"))
    }
  }

  def mockearFuncionEliminarRegistroParqueadero(dependencia: FalseConfigurations) = {
    dependencia.repoParqueadero.eliminarRegistroParqueadero(anyString) returns Reader { _ =>
      EitherT.rightT(Done)
    }
  }

  def mockearFuncionEliminarRegistroParqueaderoError(dependencia: FalseConfigurations) = {
    dependencia.repoParqueadero.eliminarRegistroParqueadero(anyString) returns Reader { _ =>
      EitherT.leftT(MensajeError(Tecnico, "Hubo un error eliminado el registro"))
    }
  }

  def mockearFuncionConsultarCantidadVehiculosRegistrados(dependencia: FalseConfigurations, cantidadVehiculos: Int) = {
    dependencia.repoParqueadero.consultarCantidadVehiculosRegistrados(anyObject) returns Reader { _ =>
      EitherT.rightT(cantidadVehiculos)
    }
  }

  def mockearFuncionConsultarCantidadVehiculosRegistradosError(dependencia: FalseConfigurations) = {
    dependencia.repoParqueadero.consultarCantidadVehiculosRegistrados(anyObject) returns Reader { _ =>
      EitherT.leftT(MensajeError(Tecnico, "Hubo un error consultando la cantidad de registros"))
    }
  }

  def mockearFuncionGuardarRegistroParqueadero(dependencia: FalseConfigurations) = {
    dependencia.repoParqueadero.guardarRegistroParqueadero(anyObject, anyObject) returns Reader { _ =>
      EitherT.rightT(Done)
    }
  }

  def mockearFuncionGuardarRegistroParqueaderoError(dependencia: FalseConfigurations) = {
    dependencia.repoParqueadero.guardarRegistroParqueadero(anyObject, anyObject) returns Reader { _ =>
      EitherT.leftT(MensajeError(Tecnico, "Hubo un error guardando el registros"))
    }
  }


}

