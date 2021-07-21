import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import entities.CustomerInfo
import io.vertx.core.MultiMap

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
        val c = getParams(params)
        customerList.add(c)
        routingContext.response().end("new list is:\n$customerList")
    }

    private fun readData(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val c = getParams(params)

        if (c.id == defaultID.toInt()) {
            routingContext.response().end(customerList.toString())
        } else {
            for (customer in customerList) {
                if (customer.id == c.id) {
                    routingContext.response().end(customer.toString())
                }
            }
            routingContext.response().setStatusCode(404).end("Data Not Found")
        }
    }

    private fun updateData(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val nc = getNewParams(params)
        val oc = getParams(params)
        if (isInCustomerList(oc.id)) {
            val foundTeam: CustomerInfo? = customerList.find { it.id == oc.id }
            customerList.remove(foundTeam)
            customerList.add(nc)
            routingContext.response().end(customerList.toString())
        } else {
            routingContext.response().setStatusCode(404).end("Data Not Found")
        }
    }

    private fun deleteData(routingContext: RoutingContext) {
        val params = routingContext.request().params()
        val t = getParams(params)
        if (t.id == defaultID.toInt()) {
            customerList.clear()
            routingContext.response().end("All data cleared")
        } else {
            for (team in customerList) {
                if (team.id == t.id) {
                    val foundCustomer: CustomerInfo? = customerList.find { it.id == t.id }
                    customerList.remove(foundCustomer)
                    routingContext.response().end("Deleted: $foundCustomer")
                }
            }
            routingContext.response().setStatusCode(404).end("Data Not Found")
        }

    }

    private fun isInCustomerList(requestedCustomerID: Int): Boolean {
        for (customer in customerList)
            if (customer.id == requestedCustomerID) {
                return true
            }
        return false
    }

    private fun getParams(params: MultiMap): CustomerInfo {
        val id = assignDefaultIfNull(params.get("id"), defaultID)
        val firstName = assignDefaultIfNull(params.get("firstName"), defaultFirstName)
        val lastName = assignDefaultIfNull(params.get("lastName"), defaultLastName)
        val age = assignDefaultIfNull(params.get("age"), defaultAge)
        val email = assignDefaultIfNull(params.get("email"), defaultEmail)

        return CustomerInfo()
    }

    private fun getNewParams(params: MultiMap): CustomerInfo {
        val newId = assignDefaultIfNull(params.get("id"), defaultID)
        val newFirstName = assignDefaultIfNull(params.get("newFirstName"), newDefaultFirstName)
        val newLastName = assignDefaultIfNull(params.get("newLastName"), newDefaultLastName)
        val newAge = assignDefaultIfNull(params.get("newAge"), newDefaultAge)
        val newEmail = assignDefaultIfNull(params.get("newEmail"), newDefaultEmail)

        return CustomerInfo()
    }


    private fun assignDefaultIfNull(parameter: String?, default: String): String {
        return parameter ?: default
    }
}