echo off
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql" -u root -p < .\"scriptMAP.sql"
set /p delExit=Creazione Database riuscita, premere ENTER per uscire...: