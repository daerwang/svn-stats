package com.oneandone.sales.svnstats;

import org.tmatesoft.svn.core.SVNLogEntryPath;

public class FileStats {
    private String type;
    private String name;
    private int added;
    private int modified;
    private int deleted;

    public FileStats(SVNLogEntryPath path) {
        name = path.getPath();
        String[] tmp = name.split("[.]");
        type = tmp[tmp.length - 1];
        countChange(path.getType());
    }

    public void update(SVNLogEntryPath path) {
        countChange(path.getType());
    }

    private void countChange(char type) {
        switch (type) {
            case 'A':
                added += 1;
                break;
            case 'M':
                modified += 1;
                break;
            case 'D':
                deleted += 1;
                break;
        }
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getTotalChanges() {
        return added + modified + deleted;
    }

    public int getAdded() {
        return added;
    }

    public int getModified() {
        return modified;
    }

    public int getDeleted() {
        return deleted;
    }
}
