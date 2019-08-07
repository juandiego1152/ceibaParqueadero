package dominio.contratos

import java.sql.Timestamp

import akka.Done
import cats.data.Reader
import co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores.FormatoEitherT
import dominio.modelos.{InformacionVehiculoParqueadero, RegistroParqueo, TipoVehiculo}
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

trait RepositorioParqueaderoTraits {

  def guardarRegistroParqueadero(registroParqueadero: RegistroParqueo, horaFechaEntrada: Timestamp): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Done]]

  def consultarCantidadVehiculosRegistrados(tipo: TipoVehiculo): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Int]]

  def consultarVehiculoRegistrado(placa: String): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Option[InformacionVehiculoParqueadero]]]

  def consultarVehiculosRegistrados(): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[List[InformacionVehiculoParqueadero]]]

  def eliminarRegistroParqueadero(placa: String): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Done]]

}
