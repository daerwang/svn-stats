package com.oneandone.sales.svnstats;

import com.oneandone.sales.svnstats.model.ChangedPath;
import com.oneandone.sales.svnstats.model.File;
import com.oneandone.sales.svnstats.model.NodeKind;
import com.oneandone.sales.svnstats.model.Revision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileStats {

    private Map<String, File> files = new HashMap<String, File>();
    private Map<String, List<File>> fileTypes = new HashMap<String, List<File>>();

    public FileStats(List<Revision> revisions) {
        for (Revision revision : revisions) {
            for (ChangedPath path : revision.changedPaths()) {
                if (path.kind() != NodeKind.FILE) {
                    continue;
                }

                if (files.containsKey(path.path())) {
                    files.get(path.path()).update(path);
                } else {
                    File file = new File(path);
                    files.put(path.path(), file);
                }
            }
        }

        for (File file : files.values()) {
            if (fileTypes.containsKey(file.type())) {
                fileTypes.get(file.type()).add(file);
            } else {
                List<File> fileTypeFiles = new ArrayList<File>();
                fileTypeFiles.add(file);
                fileTypes.put(file.type(), fileTypeFiles);
            }
        }
    }

    public Map<String, File> files() {
        return files;
    }

    public Map<String, List<File>> fileTypes() {
        return fileTypes;
    }

}
