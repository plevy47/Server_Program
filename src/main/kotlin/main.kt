import io.vertx.core.AbstractVerticle
import io.vertx.*
import io.vertx.core.Vertx
import io.vertx.core.http.HttpServerOptions
import io.vertx.kotlin.core.json.json
import io.vertx.kotlin.core.json.obj
import io.vertx.ext.*
import io.vertx.ext.web.Router
import io.vertx.kotlin.core.http.httpServerOptionsOf
import kotlin.coroutines.coroutineContext

//import io.ktor.routing.*

fun main() {
    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(MainVerticle())
}

class MainVerticle() : AbstractVerticle() {

    override fun start() {
        // Create a Router
        val router = Router.router(vertx)
        // Mount the handler for all incoming requests at every path and HTTP method
        router.route().handler { context ->
            val response = context.response()
            response.putHeader("content-type", "text-plain")
                .setChunked(true)
                .write("hello\n")
            response.write("HELLO")

            response.end("ended")
            // Get the address of the request
            val address = context.request().connection().remoteAddress().toString()
            // Get the query parameter "name"
            val queryParams = context.queryParams()
            val name = queryParams.get("name") ?: "unknown"
            // Write a json response
            context.json(
                json {
                    obj(
                        "name" to name,
                        "address" to address,
                        "message" to "Hello $name connected from $address"
                    )
                }
            )
        }
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
    }
}
