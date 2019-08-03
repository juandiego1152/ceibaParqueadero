package aplicacion

import akka.actor.ActorSystem
import aplicacion.servicios.implementacion.{ServicioParqueadero, ServicioParqueaderoObj}
import com.google.inject.Injector
import dominio.contratos.RepositorioParqueaderoTraits
import infraestructura.configuracion.DataBaseConfig
import infraestructura.repositorios.repoParqueaderoObj
import javax.inject.{Inject, Singleton}
import play.api.{Configuration, Environment}
import play.api.libs.ws.WSClient
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

@Singleton
class Dependencias @Inject()(val config: Configuration,
                             val ws: WSClient,
                             val injector:    Injector,
                             val actor:       ActorSystem,
                             val environment: Environment
                            ){

//  override implicit val injector: Injector = injector

  lazy val databaseConfig: DatabaseConfig[JdbcProfile] = DataBaseConfig.dbConfigPostgres

  lazy val servicioParqueadero: ServicioParqueadero = ServicioParqueaderoObj

  lazy val repoParqueadero: RepositorioParqueaderoTraits = repoParqueaderoObj

}


//@Singleton
//final class ControladorDeComandos @Inject() ( override val confComando: Dependencias ) extends ControllerBase with AutosCommandHelperList {
//  implicit val executionContext = commandExecutionContext
//  override implicit val configuration: Configuration = confComando.config
//  override implicit val injector: Injector = confComando.injector
//  override implicit val actorSystem: ActorSystem = confComando.actor
//}