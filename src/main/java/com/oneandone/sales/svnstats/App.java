package com.oneandone.sales.svnstats;

import net.sf.beezle.sushi.cli.Cli;
import net.sf.beezle.sushi.cli.Command;
import net.sf.beezle.sushi.cli.Option;
import net.sf.beezle.sushi.cli.Value;
import org.tmatesoft.svn.core.SVNException;

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

    public void invoke() throws SVNException {
        console.info.println("command invoked with ");
        console.info.println("   path = " + path);
        console.info.println("   username = " + username);
        console.info.println("   password = " + password);
        console.info.println("   start = " + start);
        console.info.println("   end = " + end);
        console.info.println("   changed-files = " + changedFiles);
        console.info.println("");

        SvnStats stats = new SvnStats(path);

        if (username != null && password != null) {
            stats.login(username, password);
        }

        if (changedFiles != null) {
            if (changedFiles.equalsIgnoreCase("top")) {
                stats.numberOfChangedFiles(10);
            } else {
                int numberOfChangedFiles = Integer.parseInt(changedFiles);
                stats.numberOfChangedFiles(numberOfChangedFiles);
            }
        }

        stats.analyseChanges(start, end);

        System.out.println(stats);
    }

    @Override
    public void printHelp() {
        console.info.println("usage: [-username | -password | -start rn||yyyy-mm-dd | -end rn||yyyy-mm-dd | -changed-files top||n] path");
    }
}
