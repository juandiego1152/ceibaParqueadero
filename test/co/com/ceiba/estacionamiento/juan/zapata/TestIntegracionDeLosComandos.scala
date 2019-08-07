package co.com.ceiba.estacionamiento.juan.zapata

import mocks.mocksServicioIngresoVehiculo.MockServicioRegistrarIngresoVehiculo
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsValue, Json}
import play.api.test.Helpers.{OK, POST, contentAsString, route, status, _}
import play.api.test.{FakeRequest, WithApplication}

class TestIntegracionDeLosComandos extends PlaySpec with MockServicioRegistrarIngresoVehiculo {

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
  }

  "Al usar el ComandoSalidaDeVehiculo" when {
    val parametrosEntrada: JsValue = Json.parse("""{ "placaVehiculo": "ALS222" }""")

    "Vamos a realizar la salida de un vehiculo" must {
      "Genera la informacion correctamente" in new WithApplication {

        val request = FakeRequest(POST, "/ceibaestacionamiento/salida-vehiculo").withJsonBody(parametrosEntrada)

        val Some(result) = route(app, request)

        val resultado: JsValue = Json.parse(contentAsString(result))

        status(result) mustEqual OK
        resultado.toString().contains("El valor del servicio prestado es:") mustBe true
      }
    }
  }

  "Al usar el ControladorConsultaVehiculosRegistrados" when {

    "Vamos a consultar los registros de vehiculos" must {
      "Guardar correctamente" in new WithApplication {

        val request = FakeRequest(GET, "/ceibaestacionamiento/consultar-registros")

        val Some(result) = route(app, request)

        val resultado: JsValue = Json.parse(contentAsString(result))

        status(result) mustEqual OK
      }
    }
  }

}
