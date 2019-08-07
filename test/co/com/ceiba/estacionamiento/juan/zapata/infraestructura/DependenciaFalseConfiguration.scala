package infraestructura

import akka.actor.ActorSystem
import aplicacion.servicios.implementacion.ServicioParqueadero
import co.com.ceiba.estacionamiento.juan.zapata.aplicacion.controladores.Dependencias
import com.google.inject.Injector
import com.typesafe.config.ConfigFactory
import dominio.contratos.RepositorioParqueaderoTraits
import dominio.servicios.implementaciones.ServicioValidacionesParqueadero
import javax.inject.{Inject, Singleton}
import org.specs2.mock.Mockito
import play.api.libs.ws.WSClient
import play.api.{Configuration, Environment}
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

@Singleton
class FalseConfigurations @Inject()(
                                     wsClient: WSClient,
                                     injector: Injector,
                                     actor: ActorSystem,
                                     environment: Environment
                                   ) extends Dependencias(
  Configuration(ConfigFactory.load("applicationTest123.conf")),
  wsClient,
  injector,
  actor,
  environment) with Mockito {

  implicit val executionContext: ExecutionContext = ExecutionContext.Implicits.global

  override lazy val databaseConfig: DatabaseConfig[JdbcProfile] = mock[DatabaseConfig[JdbcProfile]]

  override lazy val servicioParqueadero: ServicioParqueadero = mock[ServicioParqueadero]

  override lazy val servicioValidacionesParqueadero: ServicioValidacionesParqueadero = mock[ServicioValidacionesParqueadero]

  override lazy val repoParqueadero: RepositorioParqueaderoTraits = mock[RepositorioParqueaderoTraits]

}
