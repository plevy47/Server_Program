import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import data.HockeyTeams

class MainVerticle : AbstractVerticle() {

    var teamList = mutableListOf<HockeyTeams>()

    private val defaultMessage =
        "Endpoint not found. Try again from our list of endpoints. " +
                "\n/create " +
                "\n/read " +
                "\n/read/single " +
                "\n/update " +
                "\n/delete" +
                "\n/delete/single"

    override fun start() {
        val router = Router.router(vertx)
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(6969)
            .onSuccess { server ->
                println("HTTP server started on port " + server.actualPort())
            }

        router.route("/create").handler { createData(it) }
        router.route("/read").handler { readData(it) }
        router.route("/read/search").handler { readSingleData(it) }
        router.route("/update").handler { updateData(it) }
        router.route("/delete").handler { deleteAllData(it) }
        router.route("/delete").handler { deleteData(it) }
        router.route().handler { displayDefault(it) }
    }

    private fun displayDefault(routingContext: RoutingContext) {
        routingContext.response().end(defaultMessage)
    }

    private fun createData(routingContext: RoutingContext) {
        val parameters = routingContext.request().params()
        val name = parameters.get("name")
        val city = parameters.get("city")
        val t = HockeyTeams(name, city)
        teamList.add(t)
        routingContext.response().end("new list is:\n$teamList")
    }

    private fun readData(routingContext: RoutingContext) {
        routingContext.response().end(teamList.toString())
    }

    private fun readSingleData(routingContext: RoutingContext) {
        val parameters = routingContext.request().params()
        val name = parameters.get("name")
        val city = parameters.get("city")
        val t = HockeyTeams(name, city)
        if (isInHockeyTeams(t)) {
            routingContext.response().end(t.toString())
        } else {
            routingContext.response().setStatusCode(404).end("Data Not Found")
        }
    }

    private fun updateData(routingContext: RoutingContext) {

        routingContext.response().end("/update")
    }

    private fun deleteData(routingContext: RoutingContext){
        routingContext.response().end("/delete")

    }

    private fun deleteAllData(routingContext: RoutingContext) {
        teamList.clear()
        routingContext.response().end("All data cleared")
    }

    fun isInHockeyTeams(requestedTeam: HockeyTeams): Boolean {
        for (team in teamList)
            if (team.name == requestedTeam.name &&
                team.homeCity == requestedTeam.homeCity)
                {
                return true
            }
        return false
    }
}

