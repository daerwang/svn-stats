package com.oneandone.sales.svnstats;

import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNNodeKind;

import java.util.HashMap;
import java.util.Map;

public class FileStats {

    private Map<String, ChangedFile> changedFiles = new HashMap<String, ChangedFile>();
    private Map<String, ChangedFileType> changedFileTypes = new HashMap<String, ChangedFileType>();

    public void analysePath(SVNLogEntryPath path) {
        if (!path.getKind().equals(SVNNodeKind.FILE)) {
            return;
        }

        ChangedFile file;
        if (changedFiles.containsKey(path.getPath())) {
            file = changedFiles.get(path.getPath());
            file.update(path);
        } else {
            file = new ChangedFile(path);
            changedFiles.put(path.getPath(), file);
        }

        ChangedFileType fileType;
        if (changedFileTypes.containsKey(file.getFileType())) {
            fileType = changedFileTypes.get(file.getFileType());
        } else {
            fileType = new ChangedFileType(file.getFileType());
            changedFileTypes.put(fileType.getType(), fileType);
        }
        fileType.addFile(file);
    }

    public String toString() {
        return toString(0);
    }

    public String toString(int numberOfChangedFiles) {
        StringBuilder result = new StringBuilder();
        result.append("Changed Files: ").append(changedFiles.size()).append("\n");
        result.append("\n");
        for (ChangedFileType type : changedFileTypes.values()) {
            result.append(type.getType()).append("\n");
            result.append("   Affected Files: ").append(type.getFiles().size()).append("\n");
            result.append("   Changes in Total: ").append(type.getTotalChanges()).append("\n");
            result.append("   Added: ").append(type.getAdded()).append("\n");
            result.append("   Modified: ").append(type.getModified()).append("\n");
            result.append("   Deleted: ").append(type.getDeleted()).append("\n");
            result.append("\n");
            if (numberOfChangedFiles > 0) {
                result.append("   Changed Files:\n");
                int fileCount = 0;
                for (ChangedFile file : type.files()) {
                    if (fileCount >= numberOfChangedFiles) {
                        break;
                    }
                    fileCount += 1;
                    result.append("      ").append(file.getTotalChanges()).append(" - ").append(file.getPath()).append("\n");
                }
                result.append("\n");
            }

        }
        return result.toString();
    }
}
