package services

import java.io.{BufferedReader, InputStream, InputStreamReader}
import java.net.{HttpURLConnection, URL, URLEncoder}

import scala.util.parsing.json.{JSON, JSONObject}



/**
  * Created by lorenzo on 25/03/17.
  */
class PlacesService {

  object M extends PlacesService[Map[String, Any]]

  val LOG_TAG = "ExampleApp"

  val PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place"

  val TYPE_AUTOCOMPLETE = "/autocomplete"
  val TYPE_DETAILS = "/details"
  val TYPE_SEARCH = "/search"

  val OUT_JSON = "/json"

  // KEY!
  val API_KEY = "AIzaSyClYcPwn8vCrJs54ImEz17A98Ha7ZPL198"

  def autocomplete(input: String) = {

    val buf = new StringBuilder
    buf ++= PLACES_API_BASE
    buf ++= TYPE_AUTOCOMPLETE
    buf ++= OUT_JSON

    buf ++= "?sensor=false"
    buf ++= "&key=" + API_KEY
    buf ++= "&input=" + URLEncoder.encode(input, "utf8")

    val in = getUrlInputStream(buf.toString())

    inputStreamToString(in)

  }

  def getUrlInputStream(url: String,
                        connectTimeout: Int = 5000,
                        readTimeout: Int = 5000,
                        requestMethod: String = "GET") = {
    val u = new URL(url)
    val conn = u.openConnection.asInstanceOf[HttpURLConnection]
    HttpURLConnection.setFollowRedirects(false)
    conn.setConnectTimeout(connectTimeout)
    conn.setReadTimeout(readTimeout)
    conn.setRequestMethod(requestMethod)
    conn.connect
    conn.getInputStream
  }

  def inputStreamToString(is: InputStream) = {
    val inputStreamReader = new InputStreamReader(is)
    val bufferedReader = new BufferedReader(inputStreamReader)
    Iterator continually bufferedReader.readLine takeWhile (_ != null) mkString
  }
}


