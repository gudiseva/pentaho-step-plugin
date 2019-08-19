# Setup Pentaho Kettle Debug with Eclipse

1. Rename Spoon.bat under PENTAHO_HOME/data-integration/ as spoon-debugmode.bat
2. Copy the line starting with %SPOON_START_OPTION%
3. Append REM to the first line as:
	REM %SPOON_START_OPTION%
4. Add the below line between "%_PENTAHO_JAVA%" and %OPT%:
	"%_PENTAHO_JAVA%" -agentlib:jdwp=transport=dt_socket,server=y,address=8000,suspend=y %OPT%
	
5. Start Spoon.  PDI UI will not show up.

6. Open Eclispe / STS and import the "Pentaho Plugin" project. Set the debug points.
	a. Goto Run -> Debug Configurations... -> Remote Java Application
	b. Right click -> New Configuration
	c. In the "Connect" tab, browse and select the Pentaho Plugin project:
		- Connection Type: Standard (Socket Attach)
		- Host: localhost
		- Port: 8000
	d. Click Debug button
	
7. PDI UI will show up.

8. Create Pentaho Transformations using the newly created Pentaho Plugin

9. Run the Pentaho Transformation.  PDI execution will pause at the debug point in Eclipse.