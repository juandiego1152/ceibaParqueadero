package aplicacion.servicios.implementacion

import java.sql.Timestamp

import akka.Done
import aplicacion.dtos.PlacaVehiculoDto
import aplicacion.{Dependencias, _}
import cats.data.{EitherT, Reader}
import cats.implicits._
import dominio.modelos.{RegistroParqueo, SinCategoria, TipoVehiculo}
import dominio.servicios.implementaciones.ServicioValidacionesParqueadero
import infraestructura.configuracion.{Aplicacion, MensajeError}
import monix.eval.Task

trait ServicioParqueadero {

  def registrarIngresoVehiculo(registroVehiculo: RegistroParqueo): Reader[Dependencias, FormatoEitherT[Done]] = Reader {
    case dependencia: Dependencias =>

      dependencia.repoParqueadero.consultarCantidadVehiculosRegistrados(registroVehiculo.tipoVehiculo).run(dependencia.databaseConfig).flatMap(
        cantidadVehiculos => ServicioValidacionesParqueadero.validarPermiteIngresarVehiculos(registroVehiculo, cantidadVehiculos)
          .fold(
            error => EitherT.leftT(error),
            _ => {
              for {
                _ <- validarInformacion(registroVehiculo).aFormatoEitherT
                resultado <- {
                  val horaFechaActual = new Timestamp(System.currentTimeMillis())
                  dependencia.repoParqueadero.guardarRegistroParqueadero(registroVehiculo, horaFechaActual).run(dependencia.databaseConfig)
                }
              } yield resultado
            }
          )
      )
  }

  private[servicios] def validarInformacion(informacionParqueo: RegistroParqueo): FormatoEither[Done] = {
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

  def registrarSalidaVehiculo(placaVehiculo: PlacaVehiculoDto): Reader[Dependencias, FormatoEitherT[Double]] = Reader {
    case dependencias: Dependencias =>
      dependencias.repoParqueadero.consultarVehiculoRegistrado(placaVehiculo.placaVehiculo)
        .run(dependencias.databaseConfig)
        .flatMap(_ match {
          case Some(registro) => {
            for {
              _ <- dependencias.repoParqueadero.eliminarRegistroParqueadero(placaVehiculo.placaVehiculo).run(dependencias.databaseConfig)
              valorServicio <- EitherT.rightT[Task, MensajeError](ServicioValidacionesParqueadero.generarValorServicioParqueo(registro))
            } yield valorServicio
          }
          case None => EitherT.leftT(MensajeError(Aplicacion, "No se encontr\u00F3 ningun registro con esta placa"))
        }
        )
  }

}

object ServicioParqueaderoObj extends ServicioParqueadero