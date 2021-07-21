import entities.CustomerInfo
import entities.query.QCustomerInfo
import io.ebean.DB
import io.ebean.Database
import io.ebean.DatabaseFactory
import io.ebean.config.DatabaseConfig
import io.ebean.datasource.DataSourceConfig
import io.vertx.kotlin.ext.auth.authentication.usernamePasswordCredentialsOf

class DatabaseFunctions(){
        val ebeanDataSourceConfig = DataSourceConfig().apply {
            username = "postgres"
            password = "postgres"
            url = "jdbc:postgresql://localhost:4200/postgres"
        }

        val config =  DatabaseConfig().apply {
            dataSourceConfig = ebeanDataSourceConfig
        }
        val database = DatabaseFactory.create(config)



   // private val customerInfo: Database = DB.byName("db")
    //private val orderInfo = DB.byName("orders")

    fun listAllCustomers(): String {
        if (QCustomerInfo(database).exists()) {
            return QCustomerInfo(database).where().findList().toString()
        }
        return "That database doesn't exist"
    }

    fun listOneCustomer(): CustomerInfo? {
        return QCustomerInfo(database).where().age.eq(50).findOne()
    }


}