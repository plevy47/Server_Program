import data.CustomerInfo
import io.vertx.core.Vertx


fun main() {

    val mainVerticle = MainVerticle()

    val t1 = CustomerInfo(1,
        "John",
        "Smith",
        34,
        "johnsmith@gmail.com")
    val t2 = CustomerInfo(2,
        "Jane",
        "Smith",
        33,
        "janesmith@gmail.com")

    mainVerticle.customerList.add(t1)
    mainVerticle.customerList.add(t2)

    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(mainVerticle)
}
/* how do i define what data
 to create/remove/replace with only the path */

