package data

class HockeyTeams(name: String, homeCity: String) {
    val name: String = name
    val homeRink: String = homeCity
    override fun toString(): String {
       return "$name , $homeRink"
    }
}



