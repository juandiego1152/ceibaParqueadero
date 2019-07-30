package aplicacion.controladores.comandos

import aplicacion.Dependencias
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.{JsValue, Json}
import play.api.test.Helpers._
import play.api.test.{FakeRequest, WithApplication}

class ComandoRegistrarIngresoVehiculoTest extends PlaySpec {

  "Al usar el metodo" when {
    "Vamos a guardar un nuevo registro de vehiculo" must {
      "Guardar correctamente" in new WithApplication {

//        val dependencias = app.injector.instanceOf[Dependencias](classOf[Dependencias])

        val request = FakeRequest(POST, "/parqueaderoCeiba/ceibajdl/v0.1/guardar-registro").withJsonBody(Json.parse("""{ "placaVehiculo": "ALS211","tipoVehiculo": "Carro" }"""))

        val Some(result) = route(app, request)

        val resultado: JsValue = Json.parse(contentAsString(result))

        status(result) mustEqual OK

      }
    }

  }
}
//  "Al usar el metodo: " when {
//    "Vamos a guardar un nuevo registro de vehiculo" must {
//      "Guardar correctamente" in new WithApplication {
//
//        mockearFuncionGuardarCuentaHabienteExito(app)
//
//        val dependencias = app.injector.instanceOf[FalseConfigurations](classOf[FalseConfigurations])
//
//        val comando = new ComandoRegistrarIngresoVehiculo(dependencias, stubControllerComponents())
//
////        val xa = Await.result(comando.execute().run((dependencias, null)), Duration.Inf)
//
//        val xa = Await.result(comando.execute().apply(FakeRequest("POST", "/guardar-registro")), Duration.Inf)
//
//
//        xa mustBe OK

        //
//        //        val request = FakeRequest(POST, "/guardar-registro").withJsonBody(Json.parse("""{ "field": "value" }"""))
//        val request = FakeRequest(POST, "/guardar-registro").withBody(Json.parse("""{ "field": "value" }"""))
//
//        val body = Json.parse("""{ "field": "value" }""")
//        val Some(respuesta) = jRoute(app, request, body)
//
//        val resultado: JsValue = Json.parse(contentAsString(respuesta))


        //        Await.result( comando.execute() , Duration.Inf )//
        //
        //        val controller = new HomeController(stubControllerComponents())
        //        val home = controller.index().apply(FakeRequest(GET, "/"))


        //        status(home) mustBe OK
        ////        contentType(home) mustBe Some("text/html")
        //        contentAsString(xa) must include ("Welcome to Play").asRight
//
//      }
//    }


    //  val dependencias = new Dependencias(null, null, null, null,null)
    //
    //    val controller = new ComandoRegistrarIngresoVehiculo(Helpers.stubControllerComponents())
    //    val result: Future[Result] = controller.index().apply(FakeRequest())
    //    val bodyText: String = contentAsString(result)
    //    bodyText mustBe "ok"
//  }
//}


//"Al usar el metodo: " when {
//  "Vamos a guardar un nuevo registro de vehiculo" must {
//  "Guardar correctamente" in new WithApplication {
//
//
//  //        val dependencias = app.injector.instanceOf[Dependencias](classOf[Dependencias])
//
//  val request = FakeRequest(POST, "/parqueaderoCeiba/ceibajdl/v0.1/guardar-registro").withJsonBody(Json.parse("""{ "placaVehiculo": "ALS26E","tipoVehiculo": "Carro" }"""))
//
//  val Some( result ) = route(app, request)
//
//  val resultado: JsValue = Json.parse(contentAsString(result))
//
//  status( result ) mustEqual OK
//
//}
//}
