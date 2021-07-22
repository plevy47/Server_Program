import entities.CustomerInfo
import entities.query.QCustomerInfo
import io.ebean.DB
import io.ebean.Database
import io.ebean.DatabaseFactory
import io.ebean.config.DatabaseConfig
import io.ebean.datasource.DataSourceConfig
import io.vertx.kotlin.ext.auth.authentication.usernamePasswordCredentialsOf

class DatabaseFunctions() {
    private val ebeanDataSourceConfig = DataSourceConfig().apply {
        username = "postgres"
        password = "postgres"
        url = "jdbc:postgresql://localhost:4200/postgres"
    }
    private val config = DatabaseConfig().apply {
        dataSourceConfig = ebeanDataSourceConfig
    }
    private val database = DatabaseFactory.create(config)

    fun createCustomer(){}

    fun listAllCustomers(): String {
        return QCustomerInfo(database).where().findList().toString()
    }

    fun listOneCustomer(id: Int): String {
        return QCustomerInfo(database).where().id.eq(id).findOne().toString()
    }

    fun updateCustomer(info : CustomerInfo): String {
        var old = QCustomerInfo(database).where().id.eq(info.id).findOne()
        old?.firstName = info.firstName
        old?.lastName = info.lastName
        old?.age = info.age
        old?.email = info.email
        var new =QCustomerInfo(database).where().id.eq(info.id).delete()
        return new.toString()
    }

    fun deleteOneCustomer(id: Int):String{
        QCustomerInfo(database).where().id.eq(id).delete()
        return listOneCustomer(id)
    }

    fun deleteAllCustomers():String {
        return QCustomerInfo(database).where().delete().toString()
    }
}