package aplicacion

import akka.actor.ActorSystem
import aplicacion.servicios.implementacion.{ServicioParqueadero, ServicioParqueaderoObj}
import com.google.inject.Injector
import dominio.contratos.RepositorioParqueaderoTraits
import infraestructura.configuracion.DataBaseConfig
import infraestructura.repositorios.repoParqueaderoObj
import javax.inject.{Inject, Singleton}
import play.api.libs.ws.WSClient
import play.api.{Configuration, Environment}
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

@Singleton
class Dependencias @Inject()(val config: Configuration,
                             val ws: WSClient,
                             val injector: Injector,
                             val actor: ActorSystem,
                             val environment: Environment
                            ) {

  lazy val databaseConfig: DatabaseConfig[JdbcProfile] = DataBaseConfig.dbConfigPostgres

  lazy val servicioParqueadero: ServicioParqueadero = ServicioParqueaderoObj

  lazy val repoParqueadero: RepositorioParqueaderoTraits = repoParqueaderoObj

}