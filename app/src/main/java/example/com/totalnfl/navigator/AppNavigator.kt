package example.com.totalnfl.navigator

interface AppNavigator {

    fun navigateToDetail(id: Long, awayTeam: String, homeTeam: String)
    fun popBackStack()
}