package data

class HockeyTeams(name: String, homeCity: String) {
    val name: String = name
    val homeCity: String = homeCity
    override fun toString(): String {
       return "$name , $homeCity"
    }
}



