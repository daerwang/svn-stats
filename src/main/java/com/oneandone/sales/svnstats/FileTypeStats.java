package com.oneandone.sales.svnstats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FileTypeStats {
    private String type;
    private int added;
    private int modified;
    private int deleted;
    private List<FileStats> files = new ArrayList<FileStats>();

    public FileTypeStats(String type) {
        this.type = type;
    }

    public void addFile(FileStats file) {
        files.add(file);
        added += file.getAdded();
        modified += file.getModified();
        deleted += file.getDeleted();
    }

    public FileStats[] files() {
        FileStats[] result = files.toArray(new FileStats[0]);
        Arrays.sort(result, new Comparator<FileStats>() {
            @Override
            public int compare(FileStats o1, FileStats o2) {
                return o2.getTotalChanges() - o1.getTotalChanges();
            }
        });
        return result;
    }

    public String getType() {
        return type;
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

    public List<FileStats> getFiles() {
        return files;
    }

}
