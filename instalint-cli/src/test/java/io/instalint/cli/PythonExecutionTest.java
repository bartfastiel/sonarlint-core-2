package io.instalint.cli;

import org.junit.BeforeClass;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;

public class PythonExecutionTest extends AnalyzerExecutionTest {
  @BeforeClass
  public static void beforeClass() {
    analyzerFilename = "sonar-python-plugin-1.7.0.1195.jar";

    analyzerFilesExtension = "py";

    expected = expected()
      .issueCount(1)
      .highlight(TypeOfText.KEYWORD, range(1, 0, 1, 3))
      .highlight(TypeOfText.KEYWORD, range(2, 4, 2, 8))
      .highlight(TypeOfText.KEYWORD, range(4, 0, 4, 3))
      .highlight(TypeOfText.KEYWORD, range(5, 4, 5, 10))
      // TODO SonarPython doesn't report symbol refs!!! :-(
      ;

    result = analyzerHelper();
  }

}
