# Practical Assignment 4
## Installing the database
To install the database to a new system use the following commands in a terminal:
```bash
mysql -u root -p -e "create database u21435872_upbank";
mysql -u root -p u21435872_upbank < u21435872_upbank.sql
```
## Running the Program
The easiest way to run the program is by using the makefile located at:
> `./prac04/src/main/java/makefile`
You can specify paramaters to the program using command line arguments that you can modify in the makefile according too your needs.
You can also use the default settings but they may not work.
Do not move the makefile.
> Arguments are:
> -p --password : specify a password to access the database with
> -u --username : specify a username to access the database with
> -d --database : specify a database name to access the database with
> --port : specify a port to access the database with
> example: App -p pwd2logo -u daniel -d database --port 3306
