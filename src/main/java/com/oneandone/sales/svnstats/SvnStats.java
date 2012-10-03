package com.oneandone.sales.svnstats;

import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class SvnStats {
    private SVNRepository repository;
    private int numberOfChangedFiles;
    private FileStats fileStats = new FileStats();

    public SvnStats(String path) throws SVNException {
        setupLibrary();
        repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(path));
    }

    private void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();
        FSRepositoryFactory.setup();
    }

    public void login(String username, String password) {
        ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(username, password);
        repository.setAuthenticationManager(authManager);
    }

    public void analyseChanges(String start, String end) throws SVNException {
        for (SVNLogEntry logEntry : fetchLogEntries(start, end)) {
            for (Object entryPath : logEntry.getChangedPaths().values()) {
                fileStats.analysePath((SVNLogEntryPath) entryPath);
            }
        }
    }

    private Collection<SVNLogEntry> fetchLogEntries(long startRevision, long endRevision) {
        Collection<SVNLogEntry> logEntries = null;
        try {
            logEntries = (Collection<SVNLogEntry>) repository.log(new String[]{""}, null, startRevision, endRevision, true, true);
        } catch (SVNException svne) {
            System.err.println("error while fetching the repository revision: " + svne.getMessage());
            System.exit(1);
        }
        return logEntries;
    }

    private Collection<SVNLogEntry> fetchLogEntries(String start, String end) throws SVNException {
        long startRevision = parseRevision(start, 0);
        long endRevision = parseRevision(end, repository.getLatestRevision());

        return this.fetchLogEntries(startRevision, endRevision);
    }

    private long parseRevision(String revisionString, long fallback) throws SVNException {
        if (revisionString == null) {
            return fallback;
        }

        long revision;

        if (revisionString.startsWith("r")) {
            revision = Long.parseLong(revisionString.substring(2));
        } else if (revisionString.startsWith("-")) {
            Calendar cal = Calendar.getInstance();
            int beforeMonths = Integer.parseInt(revisionString.substring(2));
            cal.add(Calendar.MONTH, -beforeMonths);
            revision = repository.getDatedRevision(cal.getTime());
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date date = formatter.parse(revisionString);
                revision = repository.getDatedRevision(date);
            } catch (ParseException e) {
                revision = fallback;
            }
        }

        return revision;
    }

    public void numberOfChangedFiles(int i) {
        numberOfChangedFiles = i;
    }

    public String toString() {
        return fileStats.toString(numberOfChangedFiles);
    }
}
