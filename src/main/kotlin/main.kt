import data.HockeyTeam
import io.vertx.core.Vertx


fun main() {

    val mainVerticle = MainVerticle()

    val t1 = HockeyTeam("Canadiens",
        "Montreal",
        24,
        true,
        listOf("Blue","White","Red"))
    val t2 = HockeyTeam("Canucks",
        "Vancouver",
        0,
        false,
        listOf("Blue","Green","White"))
    val t3 = HockeyTeam("Maple Leafs",
        "Toronto",
        13,
        true,
        listOf("Blue","White"))

    mainVerticle.teamList.add(t1)
    mainVerticle.teamList.add(t2)
    mainVerticle.teamList.add(t3)


    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(mainVerticle)
}
/* how do i define what data
 to create/remove/replace with only the path */

