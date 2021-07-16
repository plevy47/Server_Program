import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import data.HockeyTeam
import io.vertx.core.MultiMap

class MainVerticle : AbstractVerticle() {

    private val defaultName = "none"
    private val defaultCity = "none"
    private val defaultCupWins = "0"
    private val defaultIsOriginalSix = "false"
    private val defaultTeamColours = "none"
    private val newDefaultName = "none"
    private val newDefaultCity = "none"
    private val newDefaultCupWins = "0"
    private val newDefaultIsOriginalSix = "false"
    private val newDefaultTeamColours = "none"

    enum class AllPaths(val path: String) {
        Create("/create"),
        Read("/read"),
        Update("/update"),
        Delete("/delete")
    }

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

        router.post(AllPaths.Create.path).handler { createData(it) }
        router.get(AllPaths.Read.path).handler { readData(it) }
        router.put(AllPaths.Update.path).handler { updateData(it) }
        router.delete(AllPaths.Delete.path).handler { deleteData(it) }
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
        if (t.name == defaultName) {
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
        val n = getNewParams(params)
        val o = getParams(params)
        if (isInHockeyTeams(o.name)) {
            val foundTeam: HockeyTeam? = teamList.find { it.name == o.name }
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
        if (t.name == defaultName) {
            teamList.clear()
            routingContext.response().end("All data cleared")
        } else {
            for (team in teamList) {
                if (team.name == t.name) {
                    val foundTeam: HockeyTeam? = teamList.find { it.name == t.name }
                    teamList.remove(foundTeam)
                    routingContext.response().end("Deleted: $foundTeam")
                }
            }
            routingContext.response().setStatusCode(404).end("Data Not Found")
        }

    }

    private fun isInHockeyTeams(requestedTeamName: String): Boolean {
        for (team in teamList)
            if (team.name == requestedTeamName) {
                return true
            }
        return false
    }

    private fun getNewParams(params: MultiMap): HockeyTeam {
        val newName = assignDefaultIfNull(params.get("newName"),newDefaultName)
        val newCity = assignDefaultIfNull(params.get("newCity"),newDefaultCity)
        val newCupWins = assignDefaultIfNull(params.get("newCupWins"),newDefaultCupWins)
        val newIsOriginalSix = assignDefaultIfNull(params.get("newIsOriginalSix"),newDefaultIsOriginalSix)
        val newTeamColours = assignDefaultIfNull(params.get("newTeamColours"),newDefaultTeamColours)

        return HockeyTeam(newName, newCity, newCupWins.toInt(), newIsOriginalSix.toBoolean(), newTeamColours.split(","))
    }


    private fun getParams(params: MultiMap): HockeyTeam {
        val name = assignDefaultIfNull(params.get("name"), defaultName)
        val city = assignDefaultIfNull(params.get("city"),defaultCity)
        val cupWins = assignDefaultIfNull(params.get("cupWins"),defaultCupWins)
        val isOriginalSix = assignDefaultIfNull(params.get("isOriginalSix"),defaultIsOriginalSix)
        val teamColours = assignDefaultIfNull(params.get("teamColours"),defaultTeamColours)

        return HockeyTeam(name, city, cupWins.toInt(), isOriginalSix.toBoolean(), teamColours.split(","))
    }

    private fun assignDefaultIfNull(parameter: String?, default: String): String {
        return parameter ?: default
    }
}