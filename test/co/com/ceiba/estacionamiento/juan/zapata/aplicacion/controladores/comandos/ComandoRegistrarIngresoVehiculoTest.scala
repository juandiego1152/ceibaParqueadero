package co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores.comandos

import infraestructura.FalseConfigurations
import mocks.mocksServicioIngresoVehiculo.MockServicioRegistrarIngresoVehiculo
import org.scalatestplus.play.PlaySpec
import play.api.libs.json._
import play.api.test.Helpers.{OK, contentAsString, route, status, stubControllerComponents, _}
import play.api.test.{FakeRequest, WithApplication}
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class ComandoRegistrarIngresoVehiculoTest extends PlaySpec with MockServicioRegistrarIngresoVehiculo {

  "Al usar el ComandoRegistrarIngresoVehiculo" when {

    val parametrosEntrada = Json.parse("""{ "placaVehiculo": "ALS222","tipoVehiculo": "Carro" }""")

    "Vamos a guardar un nuevo registro de vehiculo" must {
      "Guardar correctamente" in new WithApplication {

        val request = FakeRequest(POST, "/ceibaestacionamiento/guardar-registro").withJsonBody(parametrosEntrada)

        val Some(result) = route(app, request)

        val resultado: JsValue = Json.parse(contentAsString(result))

        status(result) mustEqual OK
        resultado.toString().contains("Datos guardados con \u00E9xito") mustBe true
      }
    }

    "Vamos a guardar un nuevo registro de vehiculo instanciando el comando" must {
      "Guardar correctamente" in {

        val dependencias = new FalseConfigurations(null, null, null, null)

        mockearFuncionGuardarRegistroVehiculo(dependencias)

        val comando = new ComandoRegistrarIngresoVehiculo(dependencias, stubControllerComponents())

        val result = Await.result(comando.execute().apply(FakeRequest("POST", "/ceibaestacionamiento/guardar-registro").withJsonBody(parametrosEntrada)), Duration.Inf)

        result.header.status mustEqual OK
      }
    }

    "Vamos a guardar un nuevo registro de vehiculo instanciando el comando pero ocurre un error guardando" must {
      "Debe devolver 500" in {

        val dependencias = new FalseConfigurations(null, null, null, null)

        mockearFuncionGuardarRegistroVehiculoError(dependencias)

        val comando = new ComandoRegistrarIngresoVehiculo(dependencias, stubControllerComponents())

        val result = Await.result(comando.execute().apply(FakeRequest("POST", "/ceibaestacionamiento/guardar-registro").withJsonBody(parametrosEntrada)), Duration.Inf)

        result.header.status mustEqual INTERNAL_SERVER_ERROR

      }
    }
  }
}