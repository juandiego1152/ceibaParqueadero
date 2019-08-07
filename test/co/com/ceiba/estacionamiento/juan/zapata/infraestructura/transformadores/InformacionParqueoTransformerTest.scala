package co.com.ceiba.estacionamiento.juan.zapata.infraestructura.transformadores

import java.sql.Timestamp

import co.com.ceiba.estacionamiento.juan.zapata.factorys.RegistroParqueoFactory._
import dominio.modelos.InformacionVehiculoParqueadero
import infraestructura.repositorios.registros.InformacionParqueoRegistro
import infraestructura.transformadores.InformacionParqueoTransformer
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito

class InformacionParqueoTransformerTest extends PlaySpec with Mockito {

  "Al usar el metodo: aInformacionParqueoRegistro" when {
    "Tenemos un dto RegistroParqueo y queremos convertirlo a InformacionParqueoRegistro" must {
      "Retornar el Dto transformado " in {

        val registroParqueo = registroParqueoMotoAltoCilindraje
        val fechaActual = new Timestamp(System.currentTimeMillis())
        val informacionParqueoRegistro =
          InformacionParqueoRegistro(registroParqueo.placaVehiculo, registroParqueo.tipoVehiculo.descripcion, registroParqueo.cilindraje.getOrElse(0), fechaActual)

        val respuesta = InformacionParqueoTransformer.aInformacionParqueoRegistro(registroParqueo, fechaActual)

        respuesta mustBe informacionParqueoRegistro
      }
    }
  }

  "Al usar el metodo: aInformacionVehiculoParqueadero" when {
    "Tenemos un dto informacionParqueoRegistro y queremos convertirlo a InformacionVehiculoParqueadero" must {
      "Retornar el Dto transformado " in {

        val registroParqueo = registroParqueoMotoAltoCilindraje
        val fechaActual = new Timestamp(System.currentTimeMillis())

        val informacionParqueoRegistro =
          InformacionParqueoRegistro(registroParqueo.placaVehiculo,
            registroParqueo.tipoVehiculo.descripcion,
            registroParqueo.cilindraje.getOrElse(0),
            fechaActual)

        val invormacionVehiculoParqueadero = InformacionVehiculoParqueadero(
          registroParqueo.placaVehiculo,
          registroParqueo.tipoVehiculo,
          registroParqueo.cilindraje.getOrElse(0),
          fechaActual)

        val respuesta = InformacionParqueoTransformer.aInformacionVehiculoParqueadero(informacionParqueoRegistro)

        respuesta mustBe invormacionVehiculoParqueadero
      }
    }
  }
}
