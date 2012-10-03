package com.oneandone.sales.svnstats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class ChangedFileType {
    private final String type;
    private int added;
    private int modified;
    private int deleted;
    private List<ChangedFile> files = new ArrayList<ChangedFile>();

    public ChangedFileType(String type) {
        this.type = type;
    }

    public void addFile(ChangedFile file) {
        files.add(file);
        added += file.getAdded();
        modified += file.getModified();
        deleted += file.getDeleted();
    }

    public ChangedFile[] files() {
        ChangedFile[] result = files.toArray(new ChangedFile[files.size()]);
        Arrays.sort(result, new Comparator<ChangedFile>() {
            @Override
            public int compare(ChangedFile o1, ChangedFile o2) {
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

    public List<ChangedFile> getFiles() {
        return files;
    }

}
