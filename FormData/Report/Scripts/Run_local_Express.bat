@Echo Off

FOR /f %%i IN ('DIR *.Sql /B') do call :RunScript %%i

GOTO :END

 

:RunScript

Echo Executing %1

SQLCMD -S .\sqlexpress -d topimagefdDB -U dev -P sys -i %1

Echo Completed %1

 

:END