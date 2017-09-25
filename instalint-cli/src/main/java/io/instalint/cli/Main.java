/*
 * instalint-cli
 * Copyright (C) 2009-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package io.instalint.cli;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.sonarsource.sonarlint.core.StandaloneSonarLintEngineImpl;
import org.sonarsource.sonarlint.core.client.api.common.LogOutput;
import org.sonarsource.sonarlint.core.client.api.common.ProgressMonitor;
import org.sonarsource.sonarlint.core.client.api.common.analysis.AnalysisResults;
import org.sonarsource.sonarlint.core.client.api.common.analysis.ClientInputFile;
import org.sonarsource.sonarlint.core.client.api.common.analysis.Issue;
import org.sonarsource.sonarlint.core.client.api.common.analysis.IssueListener;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneAnalysisConfiguration;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneGlobalConfiguration;
import org.sonarsource.sonarlint.core.client.api.standalone.StandaloneSonarLintEngine;

public class Main {
  public static void main(String[] args) throws IOException {
    StandaloneGlobalConfiguration globalConfig = StandaloneGlobalConfiguration.builder()
      .build();
    StandaloneSonarLintEngine engine = new StandaloneSonarLintEngineImpl(globalConfig);


    Path tmp = newTempDir();
    Path baseDir = newDir(tmp.resolve("base"));
    Path workDir = newDir(tmp.resolve("work"));
    Iterable<ClientInputFile> inputFiles = Collections.emptyList();
    Map<String, String> extraProperties = new HashMap<>();
    StandaloneAnalysisConfiguration config = new StandaloneAnalysisConfiguration(baseDir, workDir, inputFiles, extraProperties);

    IssueListener issueListener = new IssueListener() {
      @Override
      public void handle(Issue issue) {

      }
    };
    LogOutput logOutput = new LogOutput() {
      @Override
      public void log(String formattedMessage, Level level) {

      }
    };
    ProgressMonitor monitor = new ProgressMonitor() {};
    AnalysisResults results = engine.analyze(config, issueListener, logOutput, monitor);
    System.out.println(results.failedAnalysisFiles());
    System.out.println(results.fileCount());
  }

  private static Path newDir(Path path) throws IOException {
    return Files.createDirectories(path);
  }

  private static Path newTempDir() throws IOException {
    return Files.createTempDirectory("sonarlint-");
  }
}
