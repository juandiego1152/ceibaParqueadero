package co.com.ceiba.estacionamiento.juan.zapata.infraestructura.repositorios

import java.sql.Timestamp

import akka.Done
import co.com.ceiba.estacionamiento.juan.zapata.factorys.RegistroParqueoFactory._
import co.com.ceiba.estacionamiento.juan.zapata.infraestructura.repositoriosTest._
import dominio.modelos.Carro
import infraestructura.repositorios.repoParqueaderoObj
import monix.execution.schedulers.ExecutorScheduler
import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.util.Success


class RepositorioParqueaderoTest extends PlaySpec with BeforeAndAfter with Mockito {

  import driver.api._
  implicit val st: ExecutorScheduler  = SchedulerTest

  before {
    val setup_1Future: Future[Unit] = db.run(setup_test_parqueadero)
    Await.result(setup_1Future, Duration.Inf)
  }

  "RepositorioParqueadero" should {

    "Al usar el metodo: guardarRegistroParqueadero" when {

      "Guardamos un nuvero registro en la bd" must {
        "Retorar Donde" in {
          val registroParqueo = registroParqueoCarro.copy(placaVehiculo = "JUA010")
          val horaFechaActual = new Timestamp(System.currentTimeMillis())
          val resultado = Await.ready(repoParqueaderoObj.guardarRegistroParqueadero(registroParqueo, horaFechaActual).run(dbconfig).value.runToFuture, Duration.Inf).value.get

          resultado mustBe Success(Right(Done))
        }
      }
    }

    "Al usar el metodo: consultarCantidadVehiculosRegistrados" when {

      "Consultamos la cantidad de registros de un tipo de vehiculo" must {
        "Retorar la cantidad" in {

          val resultado = Await.ready(repoParqueaderoObj.consultarCantidadVehiculosRegistrados(Carro).run(dbconfig).value.runToFuture, Duration.Inf).value.get

          resultado mustBe Success(Right(5))
        }
      }
    }

//        "Al usar el metodo: consultarVehiculoRegistrado" when {
//
//        "Guardamos un nuvero registro en la bd" must {
//          "Retorar Donde" in {
//
//
//            val registroParqueo = InformacionVehiculoParqueadero("ALS21D", Carro, 0, new Timestamp(System.currentTimeMillis() - (3600050 * 2)))
//            val resultado = Await.ready(repoParqueaderoObj.consultarVehiculoRegistrado("ALS21D").run(dbconfig).value.runToFuture, Duration.Inf).value.get
//
//            val fechahora = resultado.map(x => x.map(a => a.map(z => z.horaFechaIngresoVehiculo)))
//            val resultadoFinal = registroParqueo.copy(horaFechaIngresoVehiculo = fechahora)
//            resultado mustBe Success(Right(Some()))
//          }
//        }
//      }

    "Al usar el metodo: eliminarRegistroParqueadero" when {

      "Eliminamos un registro de la bd" must {
        "Retorar Donde" in {

          val resultado = Await.ready(repoParqueaderoObj.eliminarRegistroParqueadero("ALS21D").run(dbconfig).value.runToFuture, Duration.Inf).value.get

          resultado mustBe Success(Right(Done))
        }
      }
    }
  }

  after {
    Await.result( db.run( DBIO.seq( schema.drop ) ), Duration.Inf)
  }

}