import ArgumentProcessor._

object CompundInterest {
  def main(args: Array[String]): Unit = {
    calculate(args) match {
      case Right((compoundInterest, depositAmount, _, numberOfYearsLeftToGrow, _)) =>
        println(f"Your investment of $$$depositAmount%1.2f will be worth $$$compoundInterest%1.2f in $numberOfYearsLeftToGrow years.")
      case Left(errorMessage) => println(s"Failed to calculate the compound interest: $errorMessage.")
    }
  }

  def calculate(args: Array[String]): Either[String, (Double, Double, Double, Double, Double)] = calculate(4, args)

  def calculate(neededArguments: Int, args: Array[String]): Either[String, (Double, Double, Double, Double, Double)] = {
    process(args) match {
      case List(maybeDepositAmount, maybeAnnualReturnRate, maybeNumberOfYearsLeftToGrow, maybeNumberOfTimesCompoundedPerYear, _*) =>
        val maybeCompoundInterest =
          calculateCompoundInterest(maybeDepositAmount, maybeAnnualReturnRate, maybeNumberOfYearsLeftToGrow, maybeNumberOfTimesCompoundedPerYear)

        for {
          depositAmount <- maybeDepositAmount
          annualReturnRate <- maybeAnnualReturnRate
          numberOfYearsLeftToGrow <- maybeNumberOfYearsLeftToGrow
          numberOfTimesCompoundedPerYear <- maybeNumberOfTimesCompoundedPerYear
          compoundInterest <- maybeCompoundInterest
        } yield {
          (compoundInterest, depositAmount, annualReturnRate, numberOfYearsLeftToGrow, numberOfTimesCompoundedPerYear)
        }
      case _ =>
        Left(s"Calculation requires at least $neededArguments arguments")
    }
  }

  private def calculateCompoundInterest(maybeDepositAmount: Either[String, Double],
                                        maybeAnnualReturnRate: Either[String, Double],
                                        maybeNumberOfYearsLeftToGrow: Either[String, Double],
                                        maybeNumberOfTimesCompoundedPerYear: Either[String, Double]): Either[String, Double] =
    for {
      depositAmount <- maybeDepositAmount
      annualReturnRate <- maybeAnnualReturnRate.map(_ / 100)
      numberOfYearsLeftToGrow <- maybeNumberOfYearsLeftToGrow
      numberOfTimesCompoundedPerYear <- maybeNumberOfTimesCompoundedPerYear
    } yield {
      val returnRatePerTime = annualReturnRate / numberOfTimesCompoundedPerYear
      val numberOfTimesToCompound = numberOfTimesCompoundedPerYear * numberOfYearsLeftToGrow
      depositAmount * math.pow(1 + returnRatePerTime, numberOfTimesToCompound)
    }
}
