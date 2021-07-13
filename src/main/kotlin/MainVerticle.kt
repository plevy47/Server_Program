import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import data.HockeyTeams

class MainVerticle : AbstractVerticle() {

    var teamList = mutableListOf<HockeyTeams>()

    private val defaultMessage = "Endpoint not found. Try again from our list of endpoints. \n/create \n/read \n/update \n/delete"

    override fun start() {
        val router = Router.router(vertx)
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(6969)
            .onSuccess { server ->
                println("HTTP server started on port " + server.actualPort())
            }
        router.route("/create").handler { createData(it, "Lightning","Amalie Arena") }
        router.route("/read").handler { readData(it) }
        router.route("/update").handler { updateData(it) }
        router.route("/delete").handler { deleteAllData(it) }
        router.route("/read/search").handler{readSingleData(it)}
        router.route().handler { displayDefault(it) }
    }

    private fun displayDefault(routingContext: RoutingContext){
        routingContext.response().end(defaultMessage)
    }
    private fun createData(routingContext: RoutingContext, name: String, city: String) {
        val t4 = HockeyTeams(name,city)
        teamList.add(t4)
        routingContext.response().end(teamList.toString())
    }
    private fun readData(routingContext: RoutingContext) {
        routingContext.response().end(teamList.toString())
    }
    private fun updateData(routingContext: RoutingContext) {
        routingContext.response().end("/update")
    }
    private fun deleteAllData(routingContext: RoutingContext,) {
        teamList.clear()
        routingContext.response().end("All data cleared")
    }
    private fun readSingleData(routingContext: RoutingContext){
        val parameters = routingContext.request().params()
        val name = parameters.get("name")
        val city = parameters.get("city")

        teamList.find{it == HockeyTeams(name,city)}
        //println("$name , $city")
        routingContext.response().end(teamList.toString())

        }



    }