package aplicacion

import aplicacion.servicios.implementacion.{ServicioParqueadero, ServicioParqueaderoObj}
import dominio.contratos.RepositorioParqueaderoTraits
import infraestructura.configuracion.DataBaseConfig
import infraestructura.repositorios.repoParqueaderoObj
import javax.inject.{Inject, Singleton}

import play.api.Configuration
import play.api.libs.ws.WSClient
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

@Singleton
class Dependencias @Inject()(val config: Configuration,
                             val ws: WSClient){

  lazy val databaseConfig: DatabaseConfig[JdbcProfile] = DataBaseConfig.dbConfigPostgres

  lazy val servicioParqueadero: ServicioParqueadero = ServicioParqueaderoObj

  lazy val repoParqueadero: RepositorioParqueaderoTraits = repoParqueaderoObj

}
