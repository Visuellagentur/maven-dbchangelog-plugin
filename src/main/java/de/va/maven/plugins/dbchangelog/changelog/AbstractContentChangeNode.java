package de.va.maven.plugins.dbchangelog.changelog;

import java.util.Collections;
import java.util.Map;

public abstract class AbstractContentChangeNode extends AbstractChangeNode {

    private final Map<String, String> oldSelector;
    private final Map<String, String> newSelector;
    private final Map<String, String> oldState;
    private final Map<String, String> newState;

    protected AbstractContentChangeNode(final NodeType nodeType, final Map<String, String> oldSelector,
                                        final Map<String, String> newSelector, final Map<String, String> oldState,
                                        final Map<String, String> newState) {
        super(nodeType);
        this.oldSelector = oldSelector;
        this.newSelector = newSelector;
        this.oldState = oldState;
        this.newState = newState;
    }

    public Map<String, String> getOldSelector() {
        return this.toUnmodifyableMap(this.oldSelector);
    }

    public Map<String, String> getNewSelector() {
        return this.toUnmodifyableMap(this.newSelector);
    }

    public Map<String, String> getOldState() {
        return this.toUnmodifyableMap(this.oldState);
    }

    public Map<String, String> getNewState() {
        return this.toUnmodifyableMap(this.newState);
    }

    private Map<String, String> toUnmodifyableMap(final Map<String, String> map) {
        Map<String, String> result = null;

        if (map != null) {
            result = Collections.unmodifiableMap(map);
        }

        return result;
    }
}
