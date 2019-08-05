package infraestructura.repositorios

import java.sql.Timestamp

import infraestructura.repositorios.registros.InformacionParqueoRegistro

package object tablas {

  import slick.jdbc.PostgresProfile.api._

  val TB_parqueadero = TableQuery[TbParqueadero]

  class TbParqueadero(tag: Tag) extends Table[InformacionParqueoRegistro](tag, "tb_parqueadero") {

    def placaVehiculo = column[String]("placaVehiculo")

    def tipoVehiculo = column[String]("tipoVehiculo")

    def cilindraje = column[Int]("cilindraje")

    def horaFechaEntradaVehiculo = column[Timestamp]("horaFechaEntradaVehiculo")

    def pk = primaryKey("tb_parqueadero_pk", placaVehiculo)

    def * = (placaVehiculo, tipoVehiculo, cilindraje, horaFechaEntradaVehiculo) <> (InformacionParqueoRegistro.tupled, InformacionParqueoRegistro.unapply)
  }
}
