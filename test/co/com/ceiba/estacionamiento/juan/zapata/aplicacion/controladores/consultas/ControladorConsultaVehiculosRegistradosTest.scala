package co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores.consultas

import co.com.ceiba.estacionamiento.juan.zapata.factorys.InformacionRegistroParqueoFactory
import co.com.ceiba.estacionamiento.juan.zapata.mocksServicioIngresoVehiculo.MockRepositorioParqueadero
import infraestructura.FalseConfigurations
import org.scalatestplus.play.PlaySpec
import play.api.test.{FakeRequest, WithApplication}
import play.api.test.Helpers.{OK, stubControllerComponents, _}

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class ControladorConsultaVehiculosRegistradosTest extends PlaySpec with MockRepositorioParqueadero {

  "Al usar el ControladorConsultaVehiculosRegistrados" when {

    "Vamos a todos los registros de vehiculos" must {
      "Retornar el listado de vehiculos" in {

        val dependencias = new FalseConfigurations(null, null, null, null)

        val registro = InformacionRegistroParqueoFactory.registroParqueoCarro2Horas

        val listaRegistros = List(registro,registro)

        mockearFuncionConsultarVehiculosRegistrados(dependencias, listaRegistros)

        val controlador = new ControladorConsultaVehiculosRegistrados(dependencias, stubControllerComponents())

        val result = Await.result(controlador.execute().apply(FakeRequest("POST", "/ceibaestacionamiento/consultar-registros")), Duration.Inf)

        result.header.status mustEqual OK
      }
    }

    "Vamos a todos los registros de vehiculos pero ocurre un error guardando" must {
      "Debe devolver 500" in {

        val dependencias = new FalseConfigurations(null, null, null, null)

        mockearFuncionConsultarVehiculosRegistradosError(dependencias)

        val controlador = new ControladorConsultaVehiculosRegistrados(dependencias, stubControllerComponents())

        val result = Await.result(controlador.execute().apply(FakeRequest("POST", "/ceibaestacionamiento/consultar-registros")), Duration.Inf)

        result.header.status mustEqual INTERNAL_SERVER_ERROR

      }
    }
  }
}