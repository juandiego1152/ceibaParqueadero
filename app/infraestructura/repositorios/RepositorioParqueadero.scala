package infraestructura.repositorios

import java.sql.Timestamp

import akka.Done
import aplicacion.{FormatoEither, FormatoEitherT}
import cats.data.{EitherT, Reader}
import dominio.contratos.RepositorioParqueaderoTraits
import dominio.modelos.InformacionParqueo
import infraestructura.configuracion.{MensajeError, Tecnico}
import infraestructura.repositorios.tablas._
import infraestructura.transformadores.InformacionParqueoTransformer._
import monix.eval.Task
import play.api.Logger
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile
import slick.jdbc.PostgresProfile.api._
import cats.implicits._

object RepositorioParqueadero extends RepositorioParqueaderoTraits with JdbcProfile {

  def guardarRegistroParqueadero(registroParqueadero: InformacionParqueo): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Done]] = Reader {
    dbConfig: DatabaseConfig[JdbcProfile] =>
      EitherT {
        val horaFechaActual = new Timestamp(System.currentTimeMillis())
        val informacionRegistro = aInformacionParqueoRegistro(registroParqueadero, horaFechaActual)
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

  def consultarVehiculosRegistrados(): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Int]] = Reader {
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

  def eliminarRegistroParqueadero(placa: String): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Done]] = {
    ???
  }
}
