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
import org.sonar.api.batch.sensor.highlighting.TypeOfText;

public class PhpExecutionTest extends AnalyzerExecutionTest {
  @BeforeClass
  public static void beforeClass() {
    analyzerFilename = "sonar-php-plugin-2.10.0.2087.jar";

    analyzerFilesExtension = "php";

    expected = expected()
      .fileCount(3)
      .failedFileCount(1)
      .issueCount(8)
      .highlight(TypeOfText.COMMENT, range(2, 0, 2, 21))
      .symbolRef(range(17, 2, 17, 4),
        ranges())
      .symbolRef(range(16, 9, 16, 10),
        ranges())
      ;

    result = analyzerHelper();
  }

}
