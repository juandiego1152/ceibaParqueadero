package infraestructura

import java.util.concurrent.Executors

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

@Singleton
class FalseConfigurations @Inject()(
                                     wsClient: WSClient
                                   ) extends Dependencias(Configuration(ConfigFactory.load("applicationTest12312.conf")), wsClient) with Mockito {

  implicit val executionContext = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(15))

  override lazy val databaseConfig: DatabaseConfig[JdbcProfile] = mock[DatabaseConfig[JdbcProfile]]

  override lazy val servicioParqueadero: ServicioParqueadero = mock[ServicioParqueadero]

  override lazy val repoParqueadero: RepositorioParqueaderoTraits = mock[RepositorioParqueaderoTraits]

}