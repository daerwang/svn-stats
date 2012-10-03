# SVN Stats

**SVN Stats** analyses changes in Subversion managed code repositories.
The goal is to get a quick overview of what happens to a project.
Therefore SVN Stats shows what kind of files are changed in a given period.

## Usage

```
$ java -jar ... [-username | -password | -start | -end | -changed-files] path
```

### Options

All options are optional.

```
-username [USERNAME]          # SVN username

-password [PASSWORD]          # SVN password

-start r[REVISION NUMBER]     # start at given revision
       yyyy-mm-dd             # start at last revision at the given date
       -[NUMBER OF MONTHS]    # start at last revision at the current day
                              # before the given amount of months

-end r[REVISION NUMBER]       # end at given revision
     yyyy-mm-dd               # end at last revision at the given date
     -[NUMBER OF MONTHS]      # end at last revision at the current day
                              # before the given amount of months

-changed-files top            # shows top ten files for each filetype
               [NUMBER]       # shows given number of files for each filetype
```