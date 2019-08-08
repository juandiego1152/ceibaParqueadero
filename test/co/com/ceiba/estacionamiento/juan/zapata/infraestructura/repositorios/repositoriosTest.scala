package co.com.ceiba.estacionamiento.juan.zapata.infraestructura

import java.sql.Timestamp
import java.util.concurrent.Executors

import infraestructura.repositorios.registros.InformacionParqueoRegistro
import infraestructura.repositorios.tablas.TB_parqueadero
import monix.execution.ExecutionModel.AlwaysAsyncExecution
import monix.execution.UncaughtExceptionReporter
import monix.execution.schedulers.ExecutorScheduler
import slick.basic.DatabaseConfig
import slick.jdbc.{JdbcBackend, JdbcProfile}

package object repositoriosTest {

  val driver = slick.jdbc.H2Profile

  import driver.api._

  val dbconfig: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig[JdbcProfile]("slick.dbs.default")

  val db: JdbcBackend#DatabaseDef = dbconfig.db

  lazy val SchedulerTest: ExecutorScheduler = ExecutorScheduler(
    Executors.newFixedThreadPool(10),
    UncaughtExceptionReporter(t => println(s"this should not happen: ${t.getMessage}")),
    AlwaysAsyncExecution
  )

  val schema: driver.DDL = TB_parqueadero.schema

  val setup_test_parqueadero = DBIO.seq(
    schema.create,
    TB_parqueadero += InformacionParqueoRegistro("ALS21D", "Carro", 0, new Timestamp(System.currentTimeMillis() - (3600050 * 2))),
    TB_parqueadero += InformacionParqueoRegistro("ALS21A", "Carro", 0, new Timestamp(System.currentTimeMillis() - (60005 * 5))),
    TB_parqueadero += InformacionParqueoRegistro("ALS21B", "Motocicleta", 125, new Timestamp(System.currentTimeMillis() - (3600050 * 2))),
    TB_parqueadero += InformacionParqueoRegistro("ALS21C", "Motocicleta", 650, new Timestamp(System.currentTimeMillis() - (3600050 * 2))),
    TB_parqueadero += InformacionParqueoRegistro("ALS21E", "Motocicleta", 650, new Timestamp(System.currentTimeMillis() - (3600050 * 27))),
    TB_parqueadero += InformacionParqueoRegistro("ALS21F", "Carro", 0, new Timestamp(System.currentTimeMillis() - (3600100 * 9 - 1))),
    TB_parqueadero += InformacionParqueoRegistro("ALS21G", "Carro", 0, new Timestamp(System.currentTimeMillis() - (3600050 * (9)))),
    TB_parqueadero += InformacionParqueoRegistro("ALS21H", "Carro", 0, new Timestamp(System.currentTimeMillis() - (3600050 * 27)))
  )


}
