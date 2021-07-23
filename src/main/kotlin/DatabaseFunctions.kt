import entities.Classes
import entities.CustomerInfo
import entities.query.*
import io.ebean.DatabaseFactory
import io.ebean.config.DatabaseConfig
import io.ebean.datasource.DataSourceConfig

class DatabaseFunctions {
    private val ebeanDataSourceConfig = DataSourceConfig().apply {
        username = "postgres"
        password = "postgres"
        url = "jdbc:postgresql://localhost:4200/postgres"
    }
    private val config = DatabaseConfig().apply {
        dataSourceConfig = ebeanDataSourceConfig
    }
    private val database = DatabaseFactory.create(config)
    //private val database1 = DatabaseFactory.create()

    fun test() :String {
        return "Testing..."
    }

    fun create(newData: Any): String {
        database.insert(newData)
        return newData.toString()
    }

    fun listAll(): String {
        return QCustomerInfo(database).where().findList().toString()
    }

    fun listOne(id: Int): String {
        return QCustomerInfo(database).where().id.eq(id).findOne().toString()
    }

    fun update(newData: CustomerInfo): String {
        QCustomerInfo(database).where().id.eq(newData.id).findOne()
        database.insert(newData)
        return newData.toString()
    }

    fun deleteOne(id: Int): String {
        QCustomerInfo(database).where().id.eq(id).delete()
        return listOne(id)
    }

    fun deleteAll(): String {
        return QCustomerInfo(database).where().delete().toString()
    }
}