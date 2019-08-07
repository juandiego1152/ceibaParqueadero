package co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores.comandos

import co.com.ceiba.estacionamiento.juan.zapata.mocksServicioIngresoVehiculo.MockServicioSalidaVehiculo
import infraestructura.FalseConfigurations
import org.scalatestplus.play.PlaySpec
import play.api.libs.json._
import play.api.test.FakeRequest
import play.api.test.Helpers.{OK, stubControllerComponents, _}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class ComandoSalidaVehiculoTest extends PlaySpec with MockServicioSalidaVehiculo {

  "Al usar el ComandoSalidaDeVehiculo" when {

    val parametrosEntrada: JsValue = Json.parse("""{ "placaVehiculo": "ALS222" }""")

    "Vamos realizar una salida de vehiculo instanciando el comando" must {
      "Genera la informacion correctamente" in {

        val dependencias = new FalseConfigurations(null, null, null, null)

        mockearFuncionRegistrarSalidaVehiculo(dependencias)

        val comando = new ComandoSalidaVehiculo(dependencias, stubControllerComponents())

        val result = Await.result(comando.execute().apply(FakeRequest(POST, "/ceibaestacionamiento/salida-vehiculo").withJsonBody(parametrosEntrada)), Duration.Inf)

        result.header.status mustEqual OK
      }
    }

    "Vamos realizar una salida de vehiculo instanciando el comando pero ocurre un error guardando" must {
      "Debe devolver 500" in {

        val dependencias = new FalseConfigurations(null, null, null, null)

        mockearFuncionRegistrarSalidaVehiculoError(dependencias)

        val comando = new ComandoSalidaVehiculo(dependencias, stubControllerComponents())

        val result = Await.result(comando.execute().apply(FakeRequest(POST, "/ceibaestacionamiento/salida-vehiculo").withJsonBody(parametrosEntrada)), Duration.Inf)

        result.header.status mustEqual INTERNAL_SERVER_ERROR

      }
    }
  }
}