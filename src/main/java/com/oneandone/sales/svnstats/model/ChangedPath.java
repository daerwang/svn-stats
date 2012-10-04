package com.oneandone.sales.svnstats.model;

public class ChangedPath {

    private String path;
    private ChangeType type;
    private NodeKind kind;

    public ChangedPath(String path) {
        this.path = path;
    }

    public String path() {
        return path;
    }

    public void type(ChangeType type) {
        this.type = type;
    }

    public ChangeType type() {
        return type;
    }

    public void kind(NodeKind kind) {
        this.kind = kind;
    }

    public NodeKind kind() {
        return kind;
    }

}
