package org.sonarsource.sonarlint.core.client.api.common.analysis;

import java.util.Map;
import java.util.Set;
import org.sonar.api.batch.fs.TextRange;

public class SymbolRefsListenerImpl implements SymbolRefsListener {
  private Map<TextRange, Set<TextRange>> referencesBySymbol;

  @Override
  public void handle(Map<TextRange, Set<TextRange>> referencesBySymbol) {
    this.referencesBySymbol = referencesBySymbol;
  }

  public Map<TextRange, Set<TextRange>> referencesBySymbol() {
    return referencesBySymbol;
  }
}
