# SVN Stats

**SVN Stats** is meant to perform static analysis of Subversion managed code repositories.
It shows what kind of files are changed in a given period.

## Usage

    $ java -jar ... [-username | -password | -start | -end | -changed-files] path


### Options

All options are optional.

```
-username [USERNAME]          # SVN username

-password [PASSWORD]          # SVN password

-start r[REVISION NUMBER]     # start at given revision
       -[NUMBER OF MONTHS]    # start at last revision at the given date
       yyyy-mm-dd             #start at last revision at the given date

-end r[REVISION NUMBER]       # end at given revision
     -[NUMBER OF MONTHS]      # end at last revision at the given date
     yyyy-mm-dd               # end at last revision at the given date

-changed-files top            # shows top ten files for each filetype
               [NUMBER]       # shows given number of files for each filetype
```