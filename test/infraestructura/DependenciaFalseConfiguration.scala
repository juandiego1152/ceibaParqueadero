package infraestructura

import aplicacion.Dependencias
import aplicacion.servicios.implementacion.ServicioParqueadero
import com.typesafe.config.ConfigFactory
import dominio.contratos.RepositorioParqueaderoTraits
import javax.inject.{Inject, Singleton}
import org.specs2.mock.Mockito
import play.api.Configuration
import play.api.libs.ws.WSClient
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

import scala.concurrent.ExecutionContext

//abstract class AppTestKit {
//
//  final private val appBuilder = new GuiceApplicationBuilder()
//
//  final val conf: Configuration = Configuration(ConfigFactory.load("test/resources/test/resources/test.conf"))
//
//  def testApp: Application = appBuilder.configure(conf).overrides(
//    bind[Dependencias].to[FalseConfigurations],
//    //    bind[Router].to[CustomRouter],
//    bind[HttpErrorHandler].to[ErrorHandler]
//  ).build()
//
//  def getInstanceFalseConfiguracions: FalseConfigurations = new FalseConfigurations(null, null, null, null)
//
//}
//
//abstract class TestKit extends PlaySpec {
//  implicit val ec = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(15))
//}
//
//
//@Singleton
//class FalseConfigurations @Inject()(
//                                     wsClient: WSClient
//                                   ) extends Dependencias(Configuration(ConfigFactory.load("application.conf")), wsClient) with Mockito {
//
//  implicit val executionContext: ExecutionContext = ExecutionContext.Implicits.global
//
//  override lazy val databaseConfig: DatabaseConfig[JdbcProfile] = mock[DatabaseConfig[JdbcProfile]]
//
//  override lazy val servicioParqueadero: ServicioParqueadero = mock[ServicioParqueadero]
//
//  override lazy val repoParqueadero: RepositorioParqueaderoTraits = mock[RepositorioParqueaderoTraits]
//
//}