import io.ebean.DB
import entities.CustomerInfo
import entities.Orders
import io.vertx.core.Vertx
import entities.query.QCustomerInfo
import io.ebean.Database

fun main() {

    //val database = DB.byName("customer_info")
    //val database = DB.getDefault()
    //println(database.name)
    //val cust = QCustomerInfo(database).where().age.eq(50).findOne()

    //println(cust)



    val mainVerticle = MainVerticle()
    val c1 = CustomerInfo()
    val o1 = Orders()
    val data = DatabaseFunctions()

    println(data.listAllCustomers())



    c1.firstName = "John"
    c1.lastName = "Smith"
    c1.age = 45
    c1.email = "jsmith@gmail.com"


    mainVerticle.customerList.add(c1)

    //c1.save()
    //c1.delete()

    var customerInfo: QCustomerInfo
    //customerInfo.id.equalTo(1)

   // println(mainVerticle.customerList.toString())

    //QCustomerInformation c = QCustomerInformation()
    //c1.age
    //c1.delete

    println(c1)
    println(o1)

    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(mainVerticle)
}

//map
//filter