package verticle

import database.DatabaseFunctions
import entities.Tasks
import entities.Users
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.core.MultiMap
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.handler.CorsHandler

class MainVerticle : AbstractVerticle() {

    private val default = "0"
    private val data = DatabaseFunctions()

    enum class AllPaths(val path: String) {
        CreateUser("/create-user"),
        Create("/create")
    }

    private val defaultMessage =
        "Endpoint not found."

    override fun start() {
        val router = Router.router(vertx)
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(2222)
            .onSuccess { server ->
                println("HTTP server started on port " + server.actualPort())
            }

        router.route().handler(CorsHandler.create("*").allowedMethods(mutableSetOf(HttpMethod.PUT)))
        router.post(AllPaths.CreateUser.path).handler { createUser(it) }
        router.post(AllPaths.Create.path).handler { createData(it) }
        router.route().handler { displayDefault(it) }
    }

    private fun displayDefault(routingContext: RoutingContext) {
        routingContext.response().end(defaultMessage)
    }

    private fun createUser(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val data = data.create(getUsersParams(params))
        routingContext.response().end("success")
    }


    private fun createData(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val data = data.create(getParams(params))
        routingContext.response().end("added $data")
    }

    private fun getUsersParams(params: MultiMap): Users {
        val u1 = Users()
        u1.username = params.get("username")
        u1.password = params.get("password")
        u1.email = params.get("email")
        return u1
    }


    private fun getParams(params: MultiMap): Any? {

        val t1 = Tasks()

        when (params.get("table")) {
            "Tasks" -> {
                t1.id = assignDefaultIfNull(params.get("id"), default).toInt()
                t1.taskName = assignDefaultIfNull(params.get("taskName"), default)
                return t1
            }
            else -> print(params.get("Table Not Found"))
        }
        return null
    }

    private fun assignDefaultIfNull(parameter: String?, default: String): String {
        return parameter ?: default
    }

    private fun getParam(params: MultiMap, request: String): String? {
        return params.get(request)
    }
}