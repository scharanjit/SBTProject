/**
  * Created by charanjiths on 10/5/16.
  */
// Spark

import org.apache.spark.{SparkConf, SparkContext}

object WordCount {

  private val AppName = "WordCountJob"

  // Run the word count. Agnostic to Spark's current mode of operation: can be run from tests as well as from main
  def execute(master: Option[String], args: List[String], jars: Seq[String] = Nil) {

    val sc = {
      val conf = new SparkConf().setAppName(AppName).setJars(jars)
      for (m <- master) {
        conf.setMaster(m)
      }
      new SparkContext(conf)
    }

    // Adapted from Word Count example on http://spark-project.org/examples/
    val file = sc.textFile(args(0))
    val words = file.flatMap(line => tokenize(line))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.saveAsTextFile(args(1))
  }

  // Split a piece of text into individual words.
  private def tokenize(text : String) : Array[String] = {
    // Lowercase each word and remove punctuation.
    text.toLowerCase.replaceAll("[^a-zA-Z0-9\\s]", "").split("\\s+")
  }
}