import data.HockeyTeams
import io.vertx.core.Vertx


fun main() {

    val mainVerticle = MainVerticle()



    val MTL = HockeyTeams("Canadiens","Bell Centre")
    val VAN = HockeyTeams("green","Rogers Arena")
    val TOR = HockeyTeams("blue","Scotiabank Arena")


    mainVerticle.colourList.add(MTL)
    mainVerticle.colourList.add(VAN)
    mainVerticle.colourList.add(TOR)


    val vertx: Vertx = Vertx.vertx()
    vertx.deployVerticle(mainVerticle)
}
/* how do i define what data
 to create/remove/replace with only the path */

