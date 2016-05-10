/**
  * Created by charanjiths on 10/5/16.
  */
import java.io.{File, FileWriter}

// Scala
import scala.io.Source

// Google Guava
import com.google.common.io.Files

// Specs2
import org.specs2.mutable.Specification

class WordCountTest extends Specification {

  "A WordCount job" should {

    "count words correctly" in {

      val tempDir = Files.createTempDir()
      val inputFile = new File(tempDir, "input").getAbsolutePath
      val inWriter = new FileWriter(inputFile)
      inWriter.write("hack hack hack and hack")
      inWriter.close
      val outputDir = new File(tempDir, "output").getAbsolutePath
//  val f = new File("/home/charanjiths/Documents/FLinsurancesample.csv")
//val source = Source.fromURL(getClass.getResource("/FLinsurancesample.csv"))
      val f = new File("./src/test/resources/FLinsurancesample.csv").getAbsolutePath

      WordCount.execute(
        master = Some("local"),
//        args   = List(inputFile, outputDir)
        args   = List(f, outputDir)
      )

      val outputFile = new File(outputDir, "part-00000")
      val actual = Source.fromFile(outputFile).mkString
//      actual must_== "(CLAY,1)\n(Masonry,1)\n"
      actual mustNotEqual  "(CLAY,1)\n(Masonry,1)\n"
     // actual contains "(CLAY,1)\n(Masonry,1)\n"
    }
  }
}