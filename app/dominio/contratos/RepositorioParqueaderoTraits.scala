package dominio.contratos

import akka.Done
import aplicacion.FormatoEitherT
import cats.data.Reader
import dominio.modelos.InformacionParqueo
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

trait RepositorioParqueaderoTraits {

  def guardarRegistroParqueadero(registroParqueadero: InformacionParqueo): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Done]]

  def consultarVehiculosRegistrados(): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Int]]

  def eliminarRegistroParqueadero(placa: String): Reader[DatabaseConfig[JdbcProfile], FormatoEitherT[Done]]

}
