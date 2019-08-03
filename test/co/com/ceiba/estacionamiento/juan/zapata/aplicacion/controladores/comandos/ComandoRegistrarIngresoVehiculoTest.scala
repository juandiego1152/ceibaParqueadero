package co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores.comandos

import java.util.concurrent.Executors

import mocks.mocksServicioIngresoVehiculo.MockRegistrarIngresoVehiculo
import org.scalatestplus.play.PlaySpec
import play.api.libs.json._
import play.api.test.Helpers.{route,POST, OK,status,contentAsString,stubControllerComponents,_}
import play.api.test.{FakeRequest, WithApplication}
import infraestructura.FalseConfigurations
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import org.specs2.mock.Mockito

class ComandoRegistrarIngresoVehiculoTest extends PlaySpec with Mockito with MockRegistrarIngresoVehiculo {

//  implicit val commandScheduler: ExecutorScheduler = ExecutorScheduler(
//    Executors.newFixedThreadPool( 10 ),
//    UncaughtExceptionReporter( t => println( s"this should not happen: ${t.getMessage}" ) ),
//    AlwaysAsyncExecution
//  )

  "Al usar el metodo" when {
//    "Vamos a guardar un nuevo registro de vehiculo" must {
//      "Guardar correctamente" in new WithApplication {
//
//        val request = FakeRequest(POST, "/ceibaestacionamiento/juanzapata/v0.1/guardar-registro").withJsonBody(Json.parse("""{ "placaVehiculo": "ALS211","tipoVehiculo": "Carro"}"""))
//
//        val Some(result) = route(app, request)
//
//        val resultado: JsValue = Json.parse(contentAsString(result))
//
//        status(result) mustEqual OK
//        resultado.toString().contains("Datos guardados con \u00E9xito") mustBe true
//      }
//    }

    "Vamos a guardar un nuevo registro de vehiculo instanciando el comando" must {
      "Guardar correctamente" in {

        val dependencias = new FalseConfigurations(null, null, null, null)

        mockearFuncionGuardarRegistroVehiculo(dependencias)

        val comando = new ComandoRegistrarIngresoVehiculo(dependencias, stubControllerComponents())

        val result = Await.result(comando.execute().apply(FakeRequest("POST", "/ceibaestacionamiento/juanzapata/v0.1/guardar-registro").withJsonBody(Json.parse("""{ "placaVehiculo": "ALS222","tipoVehiculo": "Carro" }"""))), Duration.Inf)

        result.header.status mustEqual OK
      }
    }

  }
}