import io.ebean.DB
import entities.CustomerInfo
import entities.Orders
import io.vertx.core.Vertx
import entities.query.QCustomerInfo
import io.ebean.Database

fun main() {

    val mainVerticle = MainVerticle()
    val c1 = CustomerInfo()
    val o1 = Orders()
    //val data = DatabaseFunctions()

    //println(data.listAllCustomers())


    //mainVerticle.customerList.add(c1)

    var customerInfo: QCustomerInfo



    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(mainVerticle)
}

//map
//filter