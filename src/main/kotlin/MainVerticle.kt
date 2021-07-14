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

        router.route("/create").handler { createData(it) }
        router.route("/read").handler { readData(it) }
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
        if (t.name == "all") {
            routingContext.response().end(teamList.toString())
        } else {
            for (team in teamList) {
                if (team.name == t.name) {
                    routingContext.response().end(team.toString())
                }
            }
            routingContext.response().setStatusCode(404).end("Data Not Found")
        }
    }

    private fun updateData(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val newName = params.get("newName")
        val newCity = params.get("newCity")
        val newCupWins = params.get("newCupWins").toInt()
        val newIsOriginalSix = params.get("newIsOriginalSix").toBoolean()
        val newTeamColours = params.get("newTeamColours").split(",")
        val n = HockeyTeam(newName, newCity, newCupWins, newIsOriginalSix, newTeamColours)
        val o = getParams(params)
        if (isInHockeyTeams(o)) {
            val foundTeam: HockeyTeam? = teamList.find { it.name == newName }
            teamList.remove(foundTeam)
            teamList.add(n)
            routingContext.response().end(teamList.toString())
        } else {
            routingContext.response().setStatusCode(404).end("Data Not Found")
        }
    }

    private fun deleteData(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val t = getParams(params)
        if (t.name == "all") {
            teamList.clear()
            routingContext.response().end("All data cleared")
        } else {
            for (team in teamList) {
                if (team.name == t.name) {
                    val foundTeam: HockeyTeam? = teamList.find { it.name == t.name && it.homeCity == t.homeCity }
                    teamList.remove(foundTeam)
                    routingContext.response().end("Deleted: $t")
                }
            }
            routingContext.response().setStatusCode(404).end("Data Not Found")
        }

    }

    private fun isInHockeyTeams(requestedTeam: HockeyTeam): Boolean {
        for (team in teamList)
            if (team.name == requestedTeam.name) {
                return true
            }
        return false
    }


    private fun getParams(params: MultiMap): HockeyTeam {
        var name = params.get("name")
        var city = params.get("city")
        var cupWins = params.get("cupWins")
        var isOriginalSix = params.get("isOriginalSix")
        var teamColours = params.get("teamColours")
        if (name == null) {
            name = "all"
        }
        if (city == null) {
            city = "none"
        }
        if (cupWins == null) {
            cupWins = "0"
        }
        if (isOriginalSix == null) {
            isOriginalSix = "false"
        }
        if (teamColours == null) {
            teamColours = "none"
        }

        return HockeyTeam(name, city, cupWins.toInt(), isOriginalSix.toBoolean(), teamColours.split(","))
    }
}

