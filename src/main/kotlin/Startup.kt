import io.vertx.core.AbstractVerticle
import io.vertx.core.Vertx
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext

class Startup() {

    fun startServer() {
        val vertx: Vertx = Vertx.vertx()
        vertx.deployVerticle(MainVerticle())
    }
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
        router.route("/bruh").handler{handler(it)}
    }
    fun handler(routingContext: RoutingContext){

        val response = routingContext.response()
            response.putHeader("content-type", "text/html")
                .setChunked(true)
                .write("<h1 style=\"color: red\">bruh</h1>")
            response.end()
        }
    }


