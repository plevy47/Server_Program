import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import entities.CustomerInfo
import io.vertx.core.MultiMap
import java.lang.NumberFormatException

class MainVerticle : AbstractVerticle() {

    private val defaultID = "0"
    private val defaultFirstName = "none"
    private val defaultLastName = "none"
    private val defaultAge = "0"
    private val defaultEmail = "none"
    private val newDefaultFirstName = "none"
    private val newDefaultLastName = "none"
    private val newDefaultAge = "0"
    private val newDefaultEmail = "none"
    val data = DatabaseFunctions()

    enum class AllPaths(val path: String) {
        Create("/create"),
        Read("/read"),
        Update("/update"),
        Delete("/delete")
    }

    var customerList = mutableListOf<CustomerInfo>()

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
        val d = data.createCustomer(getParams(params))
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
        val oc = getParams(params)
        if (true) {
            routingContext.response().end(data.updateCustomer(oc))
        } else {
            routingContext.response().setStatusCode(404).end("Data Not Found")
        }
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

    private fun isInCustomerList(requestedCustomerID: Int): Boolean {
        for (customer in customerList)
            if (customer.id == requestedCustomerID) {
                return true
            }
        return false
    }

    private fun getParams(params: MultiMap): CustomerInfo {

        val c1 = CustomerInfo()

        c1.id = assignDefaultIfNull(params.get("id"), defaultID).toInt()
        c1.firstName = assignDefaultIfNull(params.get("firstName"), defaultFirstName)
        c1.lastName = assignDefaultIfNull(params.get("lastName"), defaultLastName)
        c1.age = assignDefaultIfNull(params.get("age"), defaultAge).toInt()
        c1.email = assignDefaultIfNull(params.get("email"), defaultEmail)

        return c1
    }

    private fun assignDefaultIfNull(parameter: String?, default: String): String {
        return parameter ?: default
    }

    private fun getParam(params : MultiMap, request : String): String? {
        return params.get(request)
    }
}