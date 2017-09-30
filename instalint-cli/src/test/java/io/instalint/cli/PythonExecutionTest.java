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

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.sonar.api.batch.sensor.highlighting.TypeOfText;

public class PythonExecutionTest extends AnalyzerExecutionTest {
  @BeforeClass
  public static void beforeClass() {
    analyzerFilename = "sonar-python-plugin-1.7.0.1195.jar";

    analyzerFilesExtension = "py";

    expected = expected()
      .fileCount(2)
      .issueCount(1)
      .highlight(TypeOfText.KEYWORD, range(1, 0, 1, 3))
      .highlight(TypeOfText.KEYWORD, range(2, 4, 2, 8))
      .highlight(TypeOfText.KEYWORD, range(4, 0, 4, 3))
      .highlight(TypeOfText.KEYWORD, range(5, 4, 5, 10))
      ;

    result = analyzerHelper();
  }

  @Ignore
  @Override
  public void should_report_symbol_refs() {
    // TODO SonarPython doesn't report symbol refs!!! :-(
  }

  @Ignore
  @Override
  public void should_report_failed_files() {
    // TODO SonarPython doesn't report analysis errors !!! :-(
  }
}
