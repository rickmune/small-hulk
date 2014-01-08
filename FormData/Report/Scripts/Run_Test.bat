@Echo Off

FOR /f %%i IN ('DIR *.Sql /B') do call :RunScript %%i

GOTO :END

 

:RunScript

Echo Executing %1

SQLCMD -S barberry.arvixe.com -d tdrdb -U icoders -P icoders2012 -i %1

Echo Completed %1

 

:END