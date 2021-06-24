object ArgumentProcessor {
  def process(args: Array[String]): List[Either[String, String]] =
    (1 to args.length).filter(_ > 0)
      .foldLeft[(List[Either[String, String]], Array[String])](Nil, args)((processedAndUnprocessedArgs, index) => {
        val (currentArg, remainingArgs) = getNextArg(index, processedAndUnprocessedArgs._2)
        val processedArgs = processedAndUnprocessedArgs._1 :+ currentArg
        (processedArgs, remainingArgs)
      })._1

  private def getNextArg(index: Int, args: Array[String]): (Either[String, String], Array[String]) =
    (args.splitAt(1)._1.headOption.toRight(s"Missing argument $index"), args.splitAt(1)._2)
}
