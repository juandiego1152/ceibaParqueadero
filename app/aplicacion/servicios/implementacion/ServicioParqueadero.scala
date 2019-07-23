package aplicacion.servicios.implementacion

import akka.Done
import aplicacion.{Dependencias, _}
import aplicacion.servicios.traits.ServicioParqueaderoTraits
import cats.data.{EitherT, Reader}
import cats.implicits._
import dominio.modelos.{InformacionParqueo, SinCategoria, TipoVehiculo}
import dominio.servicios.traits.implementaciones.ServicioValidacionesParqueadero
import infraestructura.configuracion.{Aplicacion, MensajeError, Negocio}


object ServicioParqueadero extends ServicioParqueaderoTraits {

  def registrarIngresoVehiculo(registroVehiculo: InformacionParqueo): Reader[Dependencias, FormatoEitherT[Done]] = Reader {
    case dependencia: Dependencias =>

      dependencia.repoParqueadero.consultarVehiculosRegistrados().run(dependencia.databaseConfig).flatMap(
        cantidadVehiculos => ServicioValidacionesParqueadero.validarPermiteIngresarVehiculos(registroVehiculo, cantidadVehiculos)
          .fold(
            error => EitherT.leftT(error),
            _ => {
              for {
                _ <- validarInformacion(registroVehiculo).aFormatoEitherT
                resultado <- dependencia.repoParqueadero.guardarRegistroParqueadero(registroVehiculo).run(dependencia.databaseConfig)
              } yield resultado
            }
          )
      )
  }

  private[servicios] def validarInformacion(informacionParqueo: InformacionParqueo): FormatoEither[Done] = {
    (validarFormatoPlaca(informacionParqueo.placaVehiculo),
      validarTipoVehiculo(informacionParqueo.tipoVehiculo)).mapN((_, _) => Done).aFormatoEither
  }

  private[servicios] def validarFormatoPlaca(placa: String): FormatoValidatedNel[Done] = {
    if (placa.size == 6)
    //      if(placa.substring(0,3).contains(i))
      Done.valid
    else
      MensajeError(Aplicacion, "Hay un error en el formato de la placa").invalidNel
  }

  private[servicios] def validarTipoVehiculo(tipoVehiculo: TipoVehiculo): FormatoValidatedNel[Done] = {
    if (tipoVehiculo != SinCategoria)
      Done.valid
    else
      MensajeError(Aplicacion, "El vehiculo no tiene una categoria aceptable").invalidNel
  }

  def registrarSalidaVehiculo(placaVehiculo: String): Reader[Dependencias, FormatoEitherT[Double]] = {
    ???
  }
}