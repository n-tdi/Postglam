So initially, I was getting super tired of writing janky SQL and having the strings be in my regular java code, so I figured it would be time for an upgrade.

1. The first step was picking with type of SQL database should I chose to make a wrapper for Postgresql, Postgresql is a super light weight and really feature fledged fork of MySQL that makes it perfect for developement. And supports ARM64 achritecture with is my favorite :D

2. Next I was debtating whether I make it one big class with a bunch of stringable functions or OOP with different classes representative different parts of the database, e.g. Table, Row, Column.

3. I decided to go for the OOP approach as this is java, and we love verbosity so!!

4. I split my project into two parts, the actual API that developers would use to create objects and such, and then a backend that translates the provided outputs into functional SQL statements. This appraoch allowed for a cleaner code output and run time.

5. After I finished writing everything, I learned about Reposilite which is an open source JVM artifact hoster that I set up running at [Ntdi's Repos](https://repo.ntdi.world), personally it was super easy to set up with its standalone Jar and an Apache reverse proxy.

6. Lastly I paid some dude on Fiverr to make me a logo and voila! Postglam.
