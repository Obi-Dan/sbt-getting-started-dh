import scala.util.Try

object NetWorth {
  def main(args: Array[String]): Unit = {
    calculate(args) match {
      case Right(netWorth) => println(s"Your net worth is $$$netWorth.")
      case Left(errorMessage) => println(s"Failed to calculate your net worth: $errorMessage.")
    }
  }

  def calculate(args: Array[String]): Either[String, Double] =
    calculate(
      neededArguments = 2,
      args = args
    )

  def calculate(neededArguments: Int, args: Array[String]): Either[String, Double] = {
    ArgumentProcessor.process(args).map(convertToDouble) match {
      case List(maybeAssets, maybeLiabilities, _*) =>
        calculateNetWorth(maybeAssets, maybeLiabilities)
      case _ =>
        Left(s"Calculation requires at least $neededArguments arguments")
    }
  }

  private def convertToDouble(maybeString: Either[String, String]): Either[String, Double] =
    maybeString.flatMap(string => Try(string.toDouble).toEither.left.map(e => s"$e"))

  private def calculateNetWorth(maybeAssets: Either[String, Double], maybeLiabilities: Either[String, Double]): Either[String, Double] =
    for {
      assets <- maybeAssets
      liabilities <- maybeLiabilities
    } yield {
      assets - liabilities
    }
}
