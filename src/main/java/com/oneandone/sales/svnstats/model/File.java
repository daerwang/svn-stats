package com.oneandone.sales.svnstats.model;

public class File {

    private String path;
    private String type;
    private int timesAdded;
    private int timesModified;
    private int timesDeleted;

    public File(ChangedPath changedPath) {
        this.path = changedPath.path();
        String[] tmp = path.split("[.]");
        type = tmp[tmp.length - 1];
        countChange(changedPath.type());
    }

    public void update(ChangedPath changedPath) {
        countChange(changedPath.type());
    }

    private void countChange(ChangeType changeType) {
        switch (changeType) {
            case ADDED: timesAdded += 1; break;
            case MODIFIED: timesModified += 1; break;
            case DELETED: timesDeleted += 1; break;
        }
    }

    public String path() {
        return path;
    }

    public String type() {
        return type;
    }

    public int timesChanged() {
        return timesAdded + timesModified + timesDeleted;
    }

    public int timesAdded() {
        return timesAdded;
    }

    public int timesModified() {
        return timesModified;
    }

    public int timesDeleted() {
        return timesDeleted;
    }

}
