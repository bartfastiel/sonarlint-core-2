package org.sonarsource.sonarlint.core.client.api.common.analysis;

import java.util.List;
import org.sonar.api.batch.sensor.highlighting.internal.SyntaxHighlightingRule;

public class HighlightingListenerImpl implements HighlightingListener {
  private List<SyntaxHighlightingRule> highlightingRules;

  @Override
  public void handle(List<SyntaxHighlightingRule> highlightingRules) {
    this.highlightingRules = highlightingRules;
  }

  public List<SyntaxHighlightingRule> highlightingRules() {
    return highlightingRules;
  }
}
