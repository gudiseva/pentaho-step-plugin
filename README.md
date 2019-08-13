# Pentaho Step Plugin

## Pre-requisites for building the project:
* Maven, version 3+
* Java JDK 1.8
* This [settings.xml](https://github.com/pentaho/maven-parent-poms/blob/master/maven-support-files/settings.xml) in your <user-home>/.m2 directory

## References:

### Pentaho Data Integration – Writing Custom Step Plugin
https://www.sigmainfo.net/pentaho-data-integration-kettle-writing-custom-step-plug-inextension-point/

### Pentaho Community Edition - Java Project Archetype
https://javalibs.com/archetype/org.pentaho/pdi-step-plugin-archetype

### Logos used in the Plugin
https://www.flaticon.com/free-icon/internet_181526

## Building the Project

### Create binary file:
C:\> mvn clean assembly:assembly
#### This command generates Zip file, which needs to be copied into Spoon Plugins Steps directory

### Installation:
Unzip the file under PENTAHO_HOME/data-integration/plugins/steps/pentaho-step-plugin folder.