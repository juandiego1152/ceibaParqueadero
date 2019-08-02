package infraestructura.repositorios

import java.sql.Timestamp

import akka.Done
import aplicacion.{FormatoEither, FormatoEitherT}
import cats.data.{EitherT, Reader}
import cats.implicits._
import dominio.contratos.RepositorioParqueaderoTraits
import dominio.modelos.{InformacionVehiculoParqueadero, RegistroParqueo}
import infraestructura.configuracion.{MensajeError, Tecnico}
import infraestructura.repositorios.tablas._
import infraestructura.transformadores.InformacionParqueoTransformer._
import monix.eval.Task
import play.api.Logger
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._

trait RepositorioParqueadero extends RepositorioParqueaderoTraits with JdbcProfile {

  def guardarRegistroParqueadero(registroParqueadero: RegistroParqueo, horaFechaEntrada: Timestamp): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Done]] = Reader {
    dbConfig: DatabaseConfig[JdbcProfile] =>
      EitherT {
        val informacionRegistro = aInformacionParqueoRegistro(registroParqueadero, horaFechaEntrada)
        Task.fromFuture(dbConfig.db.run(TB_parqueadero.insertOrUpdate(informacionRegistro)))
          .map(_ => Right(Done))
          .onErrorRecover[FormatoEither[Done]] {
          case error: Throwable => {
            Logger.logger.error(error.getMessage)
            Left(MensajeError(Tecnico, "Ocurri\u00F3 un error registrando el vehiculo"))
          }
        }
      }
  }

  def consultarCantidadVehiculosRegistrados(): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Int]] = Reader {
    dbConfig: DatabaseConfig[JdbcProfile] =>
      EitherT {
        Task.fromFuture(dbConfig.db.run(TB_parqueadero.size.result))
          .map(_.asRight)
          .onErrorRecover[FormatoEither[Int]] {
          case error: Throwable => {
            Logger.logger.error(error.getMessage)
            Left(MensajeError(Tecnico, "Ocurri\u00F3 un error consultando la cantidad de vehiculos registrados"))
          }
        }
      }
  }

  def consultarVehiculoRegistrado(placa: String): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Option[InformacionVehiculoParqueadero]]] = Reader {
    dbConfig: DatabaseConfig[JdbcProfile] =>
      val transaccion = TB_parqueadero.filter(_.placaVehiculo === placa)
      EitherT {
        Task.fromFuture(dbConfig.db.run(transaccion.result))
          .map(registroTabla => Right(registroTabla.headOption.map(registro => aInformacionVehiculoParqueadero(registro)))
          ).onErrorRecover[FormatoEither[Option[InformacionVehiculoParqueadero]]] {
          case error: Throwable => {
            Logger.logger.error(error.getMessage)
            Left(MensajeError(Tecnico, s"Ocurri\u00F3 un error consultando el registro del vehiculo con placa: $placa"))
          }
        }
      }
  }

  def consultarVehiculosRegistrados(): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[List[InformacionVehiculoParqueadero]]] = Reader {
    dbConfig: DatabaseConfig[JdbcProfile] =>
      EitherT {
        Task.fromFuture(dbConfig.db.run(TB_parqueadero.result))
          .map(registroTabla => Right(registroTabla.map(registro => aInformacionVehiculoParqueadero(registro)).toList)
          ).onErrorRecover[FormatoEither[List[InformacionVehiculoParqueadero]]] {
          case error: Throwable => {
            Logger.logger.error(error.getMessage)
            Left(MensajeError(Tecnico, s"Ocurri\u00F3 un error consultando los vehiculos registrados"))
          }
        }
      }
  }

  def eliminarRegistroParqueadero(placa: String): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Done]] = Reader {
    dbConfig: DatabaseConfig[JdbcProfile] =>
      val transaccion = TB_parqueadero.filter(_.placaVehiculo === placa).delete
      EitherT {
        Task.fromFuture(dbConfig.db.run(transaccion))
          .map(_ => Right(Done))
          .onErrorRecover[FormatoEither[Done]] {
          case error: Throwable => {
            Logger.logger.error(error.getMessage)
            Left(MensajeError(Tecnico, s"Ocurri\u00F3 un error eliminado el registro del vehiculo con placa: $placa"))
          }
        }
      }
  }
}


object repoParqueaderoObj extends RepositorioParqueadero