package io.instalint.cli;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.junit.Test;
import org.sonar.api.batch.fs.TextRange;
import org.sonar.api.batch.fs.internal.DefaultTextPointer;
import org.sonar.api.batch.fs.internal.DefaultTextRange;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;
import org.sonarsource.sonarlint.core.StandaloneSonarLintEngineImpl;
import org.sonarsource.sonarlint.core.client.api.common.LogOutput;
import org.sonarsource.sonarlint.core.client.api.common.analysis.AnalysisResults;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;
import org.sonarsource.sonarlint.core.client.api.common.analysis.HighlightingListener;
import org.sonarsource.sonarlint.core.client.api.common.analysis.IssueListener;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneAnalysisConfiguration;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneGlobalConfiguration;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneSonarLintEngine;

import static org.assertj.core.api.Assertions.assertThat;

public class AnalyzerExecutionTest {

  static String analyzerFilename = "TO SET IN SUB-CLASS";
  static String analyzerFilesExtension = "TO SET IN SUB-CLASS";

  static class Result {
    private int issueCount;
    private Set<HighlightingImpl> highlightings = new HashSet<>();
    private int failedFileCount;
    private int fileCount;

    public Result issueCount(int issueCount) {
      this.issueCount = issueCount;
      return this;
    }

    public int issueCount() {
      return issueCount;
    }

    private Set<HighlightingImpl> highlightings() {
      return highlightings;
    }

    public Result symbolRef(int i) {
      return this;
    }

    public Result parseErrorCount(int i) {
      return this;
    }

    public Result highlight(TypeOfText textType, TextRange range) {
      highlightings.add(new HighlightingImpl(textType, range));
      return this;
    }

    public Result failedFileCount(int failedFileCount) {
      this.failedFileCount = failedFileCount;
      return this;
    }

    public Result fileCount(int fileCount) {
      this.fileCount = fileCount;
      return this;
    }
  }

  @EqualsAndHashCode
  @ToString
  static class HighlightingImpl {
    private final TypeOfText textType;
    private final TextRange range;

    public HighlightingImpl(TypeOfText textType, TextRange range) {
      this.textType = textType;
      this.range = range;
    }
  }

  static Result expected() {
    return new Result();
  }

  static Result result = expected();

  static Result expected = expected();

  static TextRange range(int startLine, int startLineOffset, int endLine, int endLineOffset) {
    return new DefaultTextRange(
      new DefaultTextPointer(startLine, startLineOffset),
      new DefaultTextPointer(endLine, endLineOffset)
    );
  }

  private static Path newDir(Path path) throws IOException {
    return Files.createDirectories(path);
  }

  private static Path newTempDir() throws IOException {
    return Files.createTempDirectory("sonarlint-");
  }

  static Result analyzerHelper() {
    try {
      return analyze();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  private static Result analyze() throws IOException {
    StandaloneGlobalConfiguration globalConfig = StandaloneGlobalConfiguration.builder()
      .addPlugin(new File("../core/target/plugins", analyzerFilename).toURI().toURL())
      .build();
    StandaloneSonarLintEngine engine = new StandaloneSonarLintEngineImpl(globalConfig);

    Path tmp = newTempDir();
    Path baseDir = newDir(tmp.resolve("base"));
    Path workDir = newDir(tmp.resolve("work"));
    InputFileFinder inputFileFinder = new InputFileFinder("**/*." + analyzerFilesExtension, null, null, Charset.defaultCharset());
    Iterable<ClientInputFile> inputFiles = inputFileFinder.collect(Paths.get("../samples").toAbsolutePath());

    Map<String, String> extraProperties = new HashMap<>();
    StandaloneAnalysisConfiguration config = new StandaloneAnalysisConfiguration(baseDir, workDir, inputFiles, extraProperties);

    AtomicInteger issueCount = new AtomicInteger();
    IssueListener issueListener = issue -> issueCount.incrementAndGet();
    LogOutput logOutput = (formattedMessage, level) -> {
//      System.out.println("log: " + formattedMessage);
    };

    Result expected = expected();

    HighlightingListener highlightingListener = highlighting -> {
      highlighting.forEach(hl -> expected.highlight(hl.getTextType(), hl.range()));
    };

    AnalysisResults results = engine.analyze(config, issueListener, highlightingListener, logOutput);

    return expected
      .issueCount(issueCount.get())
      .failedFileCount(results.failedAnalysisFiles().size())
      .fileCount(results.fileCount())
      ;
  }

  @Test
  public void should_report_issues() {
    assertThat(result.issueCount()).isEqualTo(expected.issueCount());
  }

  @Test
  public void should_report_syntax_highlights() {
    assertThat(result.highlightings()).isEqualTo(expected.highlightings());
  }

//  @Test
//  public void should_report_symbol_refs() {
//    assertThat(result.symbolRefs()).isEqualTo(expected.symbolRefs());
//  }
//
//  @Test
//  public void should_raise_on_parse_error() {
//    assertThat(result.parseErrors()).isEqualTo(expected.parseErrors());
//  }
}
