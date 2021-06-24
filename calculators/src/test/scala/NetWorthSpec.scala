object NetWorthSpec {
  def assertCalculate(expectedNetWorth: Either[String, Double], args: Any*): Unit = {
    val actualNetWorth = NetWorth.calculate(args.map(_.toString).toArray)
    assert(actualNetWorth == expectedNetWorth, s"Expected $expectedNetWorth but the result was $actualNetWorth")
  }

  def runTests: Unit = {
    assertCalculate(Right(80), 100, 20)
    assertCalculate(Right(-1000), 1000, 2000)
    assertCalculate(Left("Calculation requires at least 2 arguments"), 1000)
    assertCalculate(Left("Calculation requires at least 2 arguments"))
  }

  def main(args: Array[String]): Unit = {
    println("Running tests...")
    runTests
    println("All tests passed.")
  }
}
