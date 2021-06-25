import org.scalatest.{FlatSpec, Matchers}

class ForexSpec extends FlatSpec with Matchers {
  "The Forex api" should "fetch currencies" in {
    Forex.getExchangeRates().isEmpty should be (false)
  }
}
