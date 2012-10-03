package com.oneandone.sales.svnstats;

import org.tmatesoft.svn.core.SVNLogEntryPath;

public class ChangedFile {
    private final String fileType;
    private final String path;
    private int added;
    private int modified;
    private int deleted;

    public ChangedFile(SVNLogEntryPath svnPath) {
        path = svnPath.getPath();
        String[] tmp = path.split("[.]");
        fileType = tmp[tmp.length - 1];
        countChange(svnPath.getType());
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

    public String getFileType() {
        return fileType;
    }

    public String getPath() {
        return path;
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
