import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext


fun main() {
    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(MainVerticle())
}

class MainVerticle() : AbstractVerticle() {

    override fun start() {
        // Create a Router to handle incoming requests
        val router = Router.router(vertx)
        // Create the HTTP server
        vertx.createHttpServer()
            // Handle every request using the router
            .requestHandler(router)
            // Start listening
            .listen(8091)
            // Print the port
            .onSuccess { server ->
                println("HTTP server started on port " + server.actualPort())
            }


        // Mount the handler for all incoming requests at every path and HTTP method
        // HANDLER 1

        router.route("/bruh1").handler { context ->
            val response = context.response()
            response.putHeader("content-type", "text/html")
                .setChunked(true)
                .write("<h1 style=\"color: red\">bruh1</h1>")
            response.end()
            // Get the address of the request
        }

        // HANDLER 2
        router.route("/bruh2").handler {getData(it)}
    }

    fun getData(routingContext: RoutingContext): String{
        return "bruh2"
    }
}
