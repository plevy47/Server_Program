import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import data.HockeyTeam
import io.vertx.core.MultiMap

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
        val params = routingContext.request().params()
        val t = getParams(params)
        teamList.add(t)
        routingContext.response().end("new list is:\n$teamList")
    }

    private fun readData(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val t = getParams(params)
        if (params == null) { //this needs to be fixed
            routingContext.response().end(teamList.toString())
        } else {
            if (isInHockeyTeams(t)) {
                routingContext.response().end(t.toString())
            } else {
                routingContext.response().setStatusCode(404).end("Data Not Found")
            }
        }
    }

    private fun updateData(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val oldName = params.get("oldName")
        val oldCity = params.get("oldCity")
        val oldCupWins = params.get("oldCupWins").toInt()
        val oldIsOriginalSix = params.get("oldIsOriginalSix").toBoolean()
        val oldTeamColours = params.get("oldTeamColours").split(",")
        val o = HockeyTeam(oldName, oldCity, oldCupWins, oldIsOriginalSix, oldTeamColours)
        val n = getParams(params)
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
        val params = routingContext.request().params()
        if (params == null) {
            teamList.clear()
            routingContext.response().end("All data cleared")
        } else {
            val t = getParams(params)
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

    private fun getParams(params: MultiMap): HockeyTeam {
        val name = params.get("name")
        val city = params.get("city")
        val cupWins = params.get("cupWins").toInt()
        val isOriginalSix = params.get("isOriginalSix").toBoolean()
        val teamColours = params.get("teamColours").split(",")

        return HockeyTeam(name, city, cupWins, isOriginalSix, teamColours)
    }
}

