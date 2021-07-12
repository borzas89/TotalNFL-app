package example.com.totalnfl.util

import android.view.View
import example.com.totalnfl.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


fun imageResolverId(key: String) : Int {
    val teamsMap: MutableMap<String, Int> = HashMap()
    teamsMap["Green Bay Packers"] = R.drawable.green_bay_packers
    teamsMap["Buffalo Bills"] =   R.drawable.buffalo_bills
    teamsMap["Tampa Bay Buccaneers"] =  R.drawable.tampa_bay_buccaneers
    teamsMap["Tennessee Titans"] = R.drawable.tennessee_titans
    teamsMap["New Orleans Saints"] =  R.drawable.new_orleans_saints
    teamsMap["Kansas City Chiefs"] = R.drawable.kansas_city_chiefs
    teamsMap["Baltimore Ravens"] = R.drawable.baltimore_ravens
    teamsMap["Seattle Seahawks"] = R.drawable.seattle_seahawks
    teamsMap["Indianapolis Colts"] = R.drawable.indianapolis_colts
    teamsMap["Las Vegas Raiders"] =  R.drawable.las_vegas_raiders
    teamsMap["Minnesota Vikings"] = R.drawable.minnesota_vikings
    teamsMap["Pittsburgh Steelers"] = R.drawable.pittsburgh_steelers
    teamsMap["Arizona Cardinals"] = R.drawable.arizona_cardinals
    teamsMap["Cleveland Browns"] = R.drawable.cleveland_browns
    teamsMap["Miami Dolphins"] = R.drawable.miami_dolphins
    teamsMap["Atlanta Falcons"] = R.drawable.atlanta_falcons
    teamsMap["Dallas Cowboys"] = R.drawable.dallas_cowboys
    teamsMap["Houston Texans"] = R.drawable.houston_texans
    teamsMap["Los Angeles Chargers"] = R.drawable.los_angeles_chargers
    teamsMap["Detroit Lions"] = R.drawable.detroit_lions
    teamsMap["San Francisco 49ers"] = R.drawable.san_francisco_46ers
    teamsMap["Los Angeles Rams"] = R.drawable.los_angeles_rams
    teamsMap["Chicago Bears"] = R.drawable.chicago_bears
    teamsMap["Carolina Panthers"] = R.drawable.carolina_panthers
    teamsMap["Washington Football Team"] = R.drawable.washington_football_team
    teamsMap["Philadelphia Eagles"] = R.drawable.philadelphia_eagles
    teamsMap["New England Patriots"] = R.drawable.new_england_patriots
    teamsMap["Denver Broncos"] = R.drawable.denver_broncos
    teamsMap["Cincinnati Bengals"] = R.drawable.cincinatti_bengals
    teamsMap["Jacksonville Jaguars"] = R.drawable.jacksonville_jaguars
    teamsMap["New York Giants"] = R.drawable.new_york_giants
    teamsMap["New York Jets"] = R.drawable.new_york_jets

    return teamsMap.get(key)!!.toInt()
}

fun toSimpleString(date: Date) : String {
    val format = SimpleDateFormat("yyyy dd MM")
    return format.format(date)
}

fun View.onClick(onClickListener: (View) -> Unit) {
    this.setOnClickListener(onClickListener)
}