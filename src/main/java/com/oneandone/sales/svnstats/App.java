package com.oneandone.sales.svnstats;

import com.oneandone.sales.svnstats.connectors.Repository;
import com.oneandone.sales.svnstats.connectors.svnkit.SvnRepository;
import com.oneandone.sales.svnstats.model.Revision;
import com.oneandone.sales.svnstats.output.ConsoleOutput;
import net.sf.beezle.sushi.cli.Cli;
import net.sf.beezle.sushi.cli.Command;
import net.sf.beezle.sushi.cli.Option;
import net.sf.beezle.sushi.cli.Value;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class App extends Cli implements Command {

    @Value(name = "path", position = 1)
    private String path;
    @Option("username")
    private String username;
    @Option("password")
    private String password;
    @Option("start")
    private String start;
    @Option("end")
    private String end;
    @Option("changed-files")
    private String changedFiles;

    public static void main(String[] args) {
        System.exit(new App().run(args));
    }

    public void invoke() {
        console.info.println("command invoked with ");
        console.info.println("   path = " + path);
        console.info.println("   username = " + username);
        console.info.println("   password = " + password);
        console.info.println("   start = " + start);
        console.info.println("   end = " + end);
        console.info.println("   changed-files = " + changedFiles);
        console.info.println("");

        Repository repository = new SvnRepository(path, username, password);

        long startRevision = parseRevision(repository, start, 0);
        long endRevision = parseRevision(repository, end, -1);

        int numberOfChangedFiles = 0;
        if (changedFiles != null) {
            if (changedFiles.equalsIgnoreCase("top")) {
                numberOfChangedFiles = 10;
            } else {
                numberOfChangedFiles = Integer.parseInt(changedFiles);
            }
        }

        List<Revision> revisions = repository.fetchRevisions(startRevision, endRevision);
        FileStats fileStats = new FileStats(revisions);
        ConsoleOutput output = new ConsoleOutput();
        System.out.println(output.print(fileStats, numberOfChangedFiles));
    }

    private long parseRevision(Repository repository, String revisionString, long fallback) {
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

    @Override
    public void printHelp() {
        console.info.println("usage: [-username | -password | -start rn||yyyy-mm-dd | -end rn||yyyy-mm-dd | -changed-files top||n] path");
    }
}
