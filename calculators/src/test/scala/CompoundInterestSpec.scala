import org.scalatest.{FlatSpec, Matchers}

class CompoundInterestSpec extends FlatSpec with Matchers {
  "The CompundInterest calculator" should "calculate the compound interest" in {
    assertCalculate(Right((8144.47, 5000, 5, 10, 1)), 5000, 5, 10, 1)
    assertCalculate(Right((8146.10, 5001, 5, 10, 1)), 5001, 5, 10, 1)
    assertCalculate(Right((8954.24, 5000, 6, 10, 1)), 5000, 6, 10, 1)
    assertCalculate(Right((8551.70, 5000, 5, 11, 1)), 5000, 5, 11, 1)
    assertCalculate(Right((8193.08, 5000, 5, 10, 2)), 5000, 5, 10, 2)
    assertCalculate(Left("java.lang.NumberFormatException: For input string: \"A\""), "A", 5, 10, 1)
    assertCalculate(Left("java.lang.NumberFormatException: For input string: \"B\""), 5000, "B", 10, 1)
    assertCalculate(Left("java.lang.NumberFormatException: For input string: \"C\""), 5000, 5, "C", 1)
    assertCalculate(Left("java.lang.NumberFormatException: For input string: \"D\""), 5000, 5, 10, "D")
    assertCalculate(Left("Calculation requires at least 4 arguments"), 5000, 5, 10)
    assertCalculate(Left("Calculation requires at least 4 arguments"), 5000, 5)
    assertCalculate(Left("Calculation requires at least 4 arguments"), 5000)
    assertCalculate(Left("Calculation requires at least 4 arguments"))
  }

  def assertCalculate(expected: Either[String, (Double, Double, Double, Double, Double)], args: Any*): Unit = {
    val actual = CompundInterest.calculate(args.map(_.toString).toArray)
    actual match {
      case Right((actualCompoundInterest, actualDepositAmount, actualAnnualReturnRate, actualNumberOfYearsLeftToGrow, actualNumberOfTimesCompoundedPerYear)) =>
        expected match {
          case Right((expectedCompoundInterest, expectedDepositAmount, expectedAnnualReturnRate, expectedNumberOfYearsLeftToGrow, expectedNumberOfTimesCompoundedPerYear)) =>
            val e = 1d
            assertApproximately(expectedCompoundInterest, actualCompoundInterest, e, "Compound interest")
            assertApproximately(expectedDepositAmount, actualDepositAmount, e, "deposit amount")
            assertApproximately(expectedAnnualReturnRate, actualAnnualReturnRate, e, "annual return rate")
            assertApproximately(expectedNumberOfYearsLeftToGrow, actualNumberOfYearsLeftToGrow, e, "number of years left to grow")
            assertApproximately(expectedNumberOfTimesCompoundedPerYear, actualNumberOfTimesCompoundedPerYear, e, "number of times compounded per year")
        }
      case _ =>
        assert(actual == expected, s"Expected $expected but the result was $actual")
    }
  }

  def assertApproximately(expected: Double, actual: Double, e: Double, message: String): Unit =
    assert(expected + e >= actual && expected - e <= actual, s"$message of $actual was expected to be $expected +/- $e but it was not")
}