package io.instalint.cli;

import org.junit.BeforeClass;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;

public class PhpExecutionTest extends AnalyzerExecutionTest {
  @BeforeClass
  public static void beforeClass() {
    analyzerFilename = "sonar-php-plugin-2.10.0.2087.jar";

    analyzerFilesExtension = "php";

    expected = expected()
      .issueCount(1)
      .highlight(TypeOfText.COMMENT, range(2, 0, 2, 21))
      ;

    result = analyzerHelper();
  }

}
