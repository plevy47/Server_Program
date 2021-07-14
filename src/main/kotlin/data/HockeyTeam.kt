package data

class HockeyTeam(val name: String,
                 val homeCity: String,
                 val cupWins: Int,
                 val isOriginalSix: Boolean,
                 val teamColours: List<String>
                 ) {
    override fun toString(): String {
       return "$name,\n $homeCity,\n $cupWins,\n $isOriginalSix,\n $teamColours,\n "
    }
}



