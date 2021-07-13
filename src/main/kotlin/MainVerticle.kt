import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import data.HockeyTeam

class MainVerticle : AbstractVerticle() {

    var teamList = mutableListOf<HockeyTeam>()

    private val defaultMessage =
        "Endpoint not found. Try again from our list of endpoints. " +
                "\n/create " +
                "\n/read " +
                "\n/update " +
                "\n/delete"

    override fun start() {
        val router = Router.router(vertx)
        vertx.createHttpServer()
            .requestHandler(router)
            .listen(6969)
            .onSuccess { server ->
                println("HTTP server started on port " + server.actualPort())
            }

        router.post("/create").handler { createData(it) }
        router.get("/read").handler { readData(it) }
        router.route("/update").handler { updateData(it) }
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
        val t = HockeyTeam(name, city)
        teamList.add(t)
        routingContext.response().end("new list is:\n$teamList")
    }

    private fun readData(routingContext: RoutingContext) {
        val parameters = routingContext.request().params()
        val name = parameters.get("name")
        val city = parameters.get("city")
        if (name == null || city == null) {
            routingContext.response().end(teamList.toString())
        } else {
            val t = HockeyTeam(name, city)
            if (isInHockeyTeams(t)) {
                routingContext.response().end(t.toString())
            } else {
                routingContext.response().setStatusCode(404).end("Data Not Found")
            }
        }
    }

    private fun updateData(routingContext: RoutingContext) {
        val parameters = routingContext.request().params()
        val oldName = parameters.get("oldName")
        val oldCity = parameters.get("oldCity")
        val name = parameters.get("name")
        val city = parameters.get("city")
        val o = HockeyTeam(oldName, oldCity)
        val n = HockeyTeam(name, city)
        if (isInHockeyTeams(o)) {
            val foundTeam: HockeyTeam? = teamList.find { it.name == oldName }
            teamList.remove(foundTeam)
            teamList.add(n)
            routingContext.response().end(teamList.toString())
        } else {
            routingContext.response().setStatusCode(404).end("Data Not Found")
        }
    }

    private fun deleteData(routingContext: RoutingContext) {
        val parameters = routingContext.request().params()
        val name = parameters.get("name")
        val city = parameters.get("city")
        if (name == null || city == null) {
            teamList.clear()
            routingContext.response().end("All data cleared")
        } else {
            val t = HockeyTeam(name, city)
            if (isInHockeyTeams(t)) {
                val foundTeam: HockeyTeam? = teamList.find { it.name == t.name && it.homeCity == t.homeCity }
                teamList.remove(foundTeam)
                routingContext.response().end("Deleted: $t")
            } else {
                routingContext.response().setStatusCode(404).end("Data Not Found")
            }
        }
    }

    private fun isInHockeyTeams(requestedTeam: HockeyTeam): Boolean {
        for (team in teamList)
            if (team.name == requestedTeam.name && team.homeCity == requestedTeam.homeCity) {
                return true
            }
        return false
    }
}

