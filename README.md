# Web-Crawler

## Getting Started

1. Install [Java 11 (or higher) JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
2. Install [Maven](https://maven.apache.org/download.cgi)
3. Use:
   * ```mvn comile``` to compile the code.
   * ```mvn test``` to execute the tests.
   * ```mvn exec:java -Dexec.args="site [path]"``` to run the program. 
      * Replace ```site``` with the url of the website you want the crawler to analyze. 
      * Optional: Replace ```[path]``` with the path to a file where the output should be saved or leave it empty to print to the standard output.

