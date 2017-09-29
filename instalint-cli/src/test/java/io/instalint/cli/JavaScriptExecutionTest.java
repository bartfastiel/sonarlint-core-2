package io.instalint.cli;

import org.junit.BeforeClass;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;

public class JavaScriptExecutionTest extends AnalyzerExecutionTest {
  @BeforeClass
  public static void beforeClass() {
    analyzerFilename = "sonar-javascript-plugin-2.21.1.4786.jar";

    analyzerFilesExtension = "js";

    expected = expected()
      .issueCount(12)
      .highlight(TypeOfText.KEYWORD, range(1, 0, 1, 3))
      .highlight(TypeOfText.KEYWORD, range(2, 0, 2, 3))
      .highlight(TypeOfText.KEYWORD, range(2, 7, 2, 9))
      .highlight(TypeOfText.CONSTANT, range(1, 14, 1, 15))
      .highlight(TypeOfText.CONSTANT, range(1, 11, 1, 12))
      .highlight(TypeOfText.CONSTANT, range(1, 17, 1, 18))
      ;

    result = analyzerHelper();
  }

}
