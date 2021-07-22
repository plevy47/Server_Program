import entities.CustomerInfo
import entities.query.QCustomerInfo
import io.ebean.DatabaseFactory
import io.ebean.config.DatabaseConfig
import io.ebean.datasource.DataSourceConfig

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

    fun createCustomer(newData: CustomerInfo): String {
        QCustomerInfo(database).where().id.eq(newData.id).asUpdate()
            .set("id", newData.id)
            .set("firstName", newData.firstName)
            .set("lastName", newData.lastName)
            .set("age", newData.age)
            .set("email", newData.email)
        return newData.toString()
    }

    fun listAll(): String {
        return QCustomerInfo(database).where().findList().toString()
    }

    fun listOne(id: Int): String {
        return QCustomerInfo(database).where().id.eq(id).findOne().toString()
    }

    fun updateCustomer(info: CustomerInfo): String {
        var old = QCustomerInfo(database).where().id.eq(info.id).findOne()
        old?.firstName = info.firstName
        old?.lastName = info.lastName
        old?.age = info.age
        old?.email = info.email
        var new = QCustomerInfo(database).where().id.eq(info.id).delete()
        return new.toString()
    }

    fun deleteOne(id: Int): String {
        QCustomerInfo(database).where().id.eq(id).delete()
        return listOne(id)
    }

    fun deleteAll(): String {
        return QCustomerInfo(database).where().delete().toString()
    }
}