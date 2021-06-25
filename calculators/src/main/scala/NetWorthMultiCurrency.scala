object NetWorthMultiCurrency {
  def calculate(assets: Double, liabilities: Double): Double = assets - liabilities

  def main(args: Array[String]): Unit = {
    val Array(assetsInEuros, liabilitiesInEuros) = args.map(_.split(",").map(Currency(_)).map(_.valueInEuros).sum)
    val netWorthInEuros = calculate(assetsInEuros, liabilitiesInEuros)

    println(f"Your net worth is €$netWorthInEuros")
  }
}

object Currency {
  lazy val rateByCurrency: Map[String, Double] = Forex.getExchangeRates()

  private def getMultiplier(currencyCode: String): Double =
    currencyCode match {
      case "EUR" => 1d
      case _ => 1d / rateByCurrency(currencyCode)
    }

  def getCurrencyPrecision(value: Double): Double = BigDecimal(value).setScale(2, BigDecimal.RoundingMode.HALF_EVEN).toDouble

  def apply(value: String): Currency = {
    val Array(amountText, currencyCode, _*) = value.split("\\s")
    val amount: Double = amountText.toDouble

    new Currency(
      amount = getCurrencyPrecision(amount),
      code = currencyCode,
      valueInEuros = getCurrencyPrecision(amount * getMultiplier(currencyCode))
    )
  }
}

case class Currency(amount: Double, code: String, valueInEuros: Double)