import data.CustomerInformation
import io.vertx.core.Vertx


fun main() {

    val mainVerticle = MainVerticle()
    val c1 = CustomerInformation(
        "John",
        "Smith",
        40,
        "jonhsmith@gmail.com")

    println(c1)

    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(mainVerticle)
}


/* how do i define what data
 to create/remove/replace with only the path */

