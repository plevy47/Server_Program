import entities.Assignments
import entities.Classes
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import entities.CustomerInfo
import io.vertx.core.MultiMap
import java.lang.NumberFormatException

class MainVerticle : AbstractVerticle() {

    private val default = "0"
    private val data = DatabaseFunctions()

    enum class AllPaths(val path: String) {
        Create("/create"),
        Read("/read"),
        Update("/update"),
        Delete("/delete")
    }

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
        router.route("/test").handler{runTest(it) }
        router.route().handler { displayDefault(it) }
    }

    private fun runTest(routingContext: RoutingContext){
        val newClass = Classes()
        newClass.className = "Communications"
        newClass.classCode = "COMM-3020"
        newClass.teacherName = "Dr. Jeffery Weingarten"
        newClass.teacherEmail = "jweingarten@fanshaweonline.ca2"
        newClass.semester = "W21"
        routingContext.response().end(data.create(newClass))
    }

    private fun displayDefault(routingContext: RoutingContext) {
        routingContext.response().end(defaultMessage)
    }

    private fun createData(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val d = data.create(getParams(params))
        routingContext.response().end("added $d")
    }

    private fun readData(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val searchKey = getParam(params, "searchKey")
        if (searchKey == null) {
            routingContext.response().end(data.listAll())
        }
        val searchQuery = getParam(params, searchKey!!)
        try {
            routingContext.response().end(data.listOne(searchQuery!!.toInt()))
        } catch (e: NumberFormatException) {
            routingContext.response().end("Invalid value for search")
        }
    }

    private fun updateData(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val v = getParams(params)
        val oc = data.update(v)
        routingContext.response().end("updated the row at Id: ? to $oc")
    }

    private fun deleteData(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val searchKey = getParam(params, "searchKey")
        if (searchKey == null) {
            routingContext.response().end("searchKey not specified, please try again")
        }
        val searchQuery = getParam(params, searchKey!!)
        try {
            routingContext.response().end(data.deleteOne(searchQuery!!.toInt()))
        } catch (e: NumberFormatException) {
            routingContext.response().end("Invalid value for 'Id'")
        }
        routingContext.response().end(data.deleteAll())
    }

    private fun getParams(params: MultiMap): Any? {

        val c1 = Classes()
        val a1 = Assignments()

        when (params.get("table")){
            "classes" -> {
                c1.id = assignDefaultIfNull(params.get("id"), default).toInt()
                c1.className = assignDefaultIfNull(params.get("className"), default)
                c1.classCode = assignDefaultIfNull(params.get("classCode"), default)
                c1.teacherName = assignDefaultIfNull(params.get("teacherName"), default)
                c1.teacherEmail = assignDefaultIfNull(params.get("teacherEmail"), default)
                c1.semester = assignDefaultIfNull(params.get("semester"), default)
                return c1
            }
            "assignments" -> {
                a1.id = assignDefaultIfNull(params.get("id"), default).toInt()
                a1.assignmentDescription = assignDefaultIfNull(params.get("assignmentDescription"), default)
                a1.assignmentType = assignDefaultIfNull(params.get("assignmentType"), default)
                a1.dueDate = assignDefaultIfNull(params.get("dueDate"), default)
                a1.gradeValue = assignDefaultIfNull(params.get("gradeValue"), default)
                a1.gradeResult = assignDefaultIfNull(params.get("gradeResult"), default)
                return a1
            }
            else -> print(params.get("Table Not Found"))
        }
        return null
    }

    private fun assignDefaultIfNull(parameter: String?, default: String): String {
        return parameter ?: default
    }

    private fun getParam(params : MultiMap, request : String): String? {
        return params.get(request)
    }
}