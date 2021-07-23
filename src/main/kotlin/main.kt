import io.vertx.core.Vertx

fun main() {

    val mainVerticle = MainVerticle()
    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(mainVerticle)
}

//map
//filter