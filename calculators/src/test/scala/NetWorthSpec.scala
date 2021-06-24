import org.scalatest.{FlatSpec, Matchers}

class NetWorthSpec extends FlatSpec with Matchers {
  "The NetWorth calculator" should "calculate the compound interest" in {
    assertCalculate(Right(80), 100, 20)
    assertCalculate(Right(-1000), 1000, 2000)
    assertCalculate(Left("Calculation requires at least 2 arguments"), 1000)
    assertCalculate(Left("Calculation requires at least 2 arguments"))
  }

  def assertCalculate(expectedNetWorth: Either[String, Double], args: Any*): Unit = {
    val actualNetWorth = NetWorth.calculate(args.map(_.toString).toArray)
    assert(actualNetWorth == expectedNetWorth, s"Expected $expectedNetWorth but the result was $actualNetWorth")
  }
}