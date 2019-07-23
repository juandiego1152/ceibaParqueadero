package aplicacion

import aplicacion.servicios.implementacion.ServicioParqueadero
import aplicacion.servicios.traits.ServicioParqueaderoTraits
import dominio.contratos.RepositorioParqueaderoTraits
import infraestructura.configuracion.DataBaseConfig
import infraestructura.repositorios.RepositorioParqueadero
import javax.inject.{Inject, Singleton}
import play.api.Configuration
import play.api.libs.ws.WSClient
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

@Singleton
class Dependencias @Inject()(val config: Configuration, val ws: WSClient) {
  val databaseConfig: DatabaseConfig[JdbcProfile] = DataBaseConfig.dbConfigPostgres
  val servicioParqueadero: ServicioParqueaderoTraits = ServicioParqueadero
  val repoParqueadero: RepositorioParqueaderoTraits = RepositorioParqueadero
}
