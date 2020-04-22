#!/bin/bash
rm -rf DictionaryServer
rm -rf DictionaryClient

mkdir DictionaryServer
mkdir DictionaryClient

cd src/
javac -d . DictionaryServer.java
echo "Main-Class: DictionaryServer" >> MANIFEST.MF
jar cvmf MANIFEST.MF DictionaryServer.jar *.class
mv DictionaryServer.jar ../DictionaryServer
mv *.class ../DictionaryServer
mv MANIFEST.MF ../DictionaryServer

cd ..

cd src/
rm *.class
javac -d . DictionaryClient.java
echo "Main-Class: DictionaryClient" >> MANIFEST.MF
jar cvmf MANIFEST.MF DictionaryClient.jar *.class
mv DictionaryClient.jar ../DictionaryClient
mv *.class ../DictionaryClient
mv MANIFEST.MF ../DictionaryClient

echo "jar files are now in DictionaryClient/ and DictionaryServer/"
