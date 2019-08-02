package infraestructura.configuracion

import com.typesafe.config.{Config, ConfigFactory}
import play.api.Logger
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

object DataBaseConfig {

  val dbConfigPostgres: DatabaseConfig[JdbcProfile] = getDataBaseConfiguration( configuration = "parqueaderoCeiba" )

  private def getDataBaseConfiguration( configuration: String ): DatabaseConfig[JdbcProfile] = {
    val config: Config = ConfigFactory.load()
    val dataBaseConfig: Config = config.getConfig( s"slick.dbs.$configuration" )
    Logger.logger.debug( dataBaseConfig.entrySet().toString )
    DatabaseConfig.forConfig( s"slick.dbs.$configuration" )
  }
}
