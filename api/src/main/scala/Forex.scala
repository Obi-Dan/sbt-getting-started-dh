import scala.util.Try
import scala.xml.XML

object Forex {
  def getExchangeRates(): Map[String, Double] = Try(requests.get("https://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml"))
    .toEither.left.map(e => s"Error when attempting to load the exchange rates: $e")
    .flatMap(response => {
      Try(XML.loadString(response.text))
        .toEither.left.map(e => s"Error when attempting to parse the exchange rate xml: $e")
        .map(xml => (xml \\ "@currency").map(_.text).zip((xml \\ "@rate").map(_.text).map(rate =>
          Try(rate.toDouble).toEither.left.map(e => s"Error when attempting to convert the rate to double: $e")
        )).toMap.filter(_._2.isRight).transform((_, value) => value.right.get))
    }) match {
    case Right(currencyAndRate) => currencyAndRate
    case Left(message) =>
      println(message)
      Map()
  }

  def main(args: Array[String]): Unit = {
    println(getExchangeRates())
  }
}
