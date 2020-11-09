# Welcome to Enkryptor

Let's get you secure.

## Pre-requisites

First you will need a Java runtime environment if you do not have one already. Check out <https://www.java.com/en/>.

For development you will also need a JDK, or Java Development Kit. The most recent JDK can be found here: <https://www.oracle.com/java/technologies/javase-jdk15-downloads.html>

## Application Download

To run the application you can either look for the .jar file to download and run. This is all you will need to run the application. Direct link is here: <https://github.com/atxarib99/CS4389Project/blob/master/Enkryptor.jar>

psst! If this gives you issue try running the .jar from the command line with:
```
java -jar Enkryptor.jar
```
## Build from source

If that is not available you can build from source. Download this repository from the download button above. You can then open this in an IDE and run Main.java. Your IDE should be able to build this provided that you have a JDK and a JRE.

If you don't want to use an IDE you can build the project using

```
javac com/Enkryptor/*.java
```

Then run the program with
```
java Main.java
```

## Build Binary

Optionally you can also build the binary with
```
jar cmvf META-INF/MANIFEST.MF Enkryptor.jar com/Enkryptor
```

And then run the binary with 
```
java -jar Enkryptor.jar
```
