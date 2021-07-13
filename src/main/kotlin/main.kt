import data.HockeyTeams
import io.vertx.core.Vertx


fun main() {

    val mainVerticle = MainVerticle()



    val t1 = HockeyTeams("Canadiens","Montreal")
    val t2 = HockeyTeams("Canucks","Vancouver")
    val t3 = HockeyTeams("Maple Leafs","Toronto")


    mainVerticle.teamList.add(t1)
    mainVerticle.teamList.add(t2)
    mainVerticle.teamList.add(t3)


    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(mainVerticle)
}
/* how do i define what data
 to create/remove/replace with only the path */

