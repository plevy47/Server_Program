package data

class HockeyTeam(val name: String,
                 private val homeCity: String,
                 private val cupWins: Int,
                 private val isOriginalSix: Boolean,
                 private val teamColours: List<String>
                 ) {
    override fun toString(): String {
       return "$name,\n $homeCity,\n $cupWins,\n $isOriginalSix,\n $teamColours,\n\n "
    }
}



