package infraestructura

import java.util.concurrent.Executors

import akka.actor.ActorSystem
import aplicacion.Dependencias
import aplicacion.servicios.implementacion.ServicioParqueadero
import com.google.inject.Injector
import com.sun.xml.internal.bind.v2.model.core.ErrorHandler
import com.typesafe.config.ConfigFactory
import dominio.contratos.RepositorioParqueaderoTraits
import javax.inject.{Inject, Singleton}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatestplus.play.PlaySpec
import org.specs2.mock.Mockito
import play.api.http.HttpErrorHandler
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.ws.WSClient
import play.api.routing.Router
import play.api.{Application, Configuration, Environment}
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
  Configuration(ConfigFactory.load("applicationTest12312.conf")),
  wsClient,
  injector,
  actor,
  environment) with Mockito {

//  implicit val executionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(15))

  implicit val executionContext: ExecutionContext = ExecutionContext.Implicits.global

  override lazy val databaseConfig: DatabaseConfig[JdbcProfile] = mock[DatabaseConfig[JdbcProfile]]

  override lazy val servicioParqueadero: ServicioParqueadero = mock[ServicioParqueadero]

  override lazy val repoParqueadero: RepositorioParqueaderoTraits = mock[RepositorioParqueaderoTraits]

}

//
//abstract class AppTestKit extends PlaySpec with GeneratorDrivenPropertyChecks {
//
////  implicit val ec = appExecutionContext
//
//  final private val appBuilder = new GuiceApplicationBuilder()
//
//  final val conf: Configuration = Configuration( ConfigFactory.load( "test/resources/test/resources/applicationTest.conf" ) )
//
//  def testApp: Application = appBuilder.configure( conf ).overrides(
//    bind[Dependencias].to[FalseConfigurations]
////    bind[HttpErrorHandler].to[ErrorHandler]
//  ).build()
//
//  def getInstanceFalseConfiguracions: FalseConfigurations = new FalseConfigurations( null, null, null, null )
//
//}