Active Directory Informer

ver 1.3.0 Stable Release

The program is intended for users and system administrators of enterprises and
organizations with large IT network infrastructure. 
The program scans the specified ranges network and displays the information
on the host at the current time, namely, who logged into the host.
If a user is logged on the remote host is looking for information about it
on the server LDAP. The results can be saved in a database of MySQL or 
data formats XML or CSV.

Database
MySQL

Столбец         Тип             Null	По умолчанию            Комментарии
id              int(5)          Нет 	 	 
time            timestamp	Нет 	CURRENT_TIMESTAMP 	value time when was update 
ip              varchar(15)	Нет                             value ip address host 
login           varchar(255)	Нет                             value Domain login 
full_name	varchar(255)	Нет                             value domain attribute - name 
dns_name	varchar(255)	Нет                             value fqdn the host 
telephonenumber	varchar(30)	Нет                             value domain attribute - telephonenumber
mobile          varchar(30)	Нет                             value domain attribute - mobile 
mail            varchar(255)	Нет                             value domain attribute - mail 
ipphone         varchar(30)	Нет                             value domain attribute - ipphone 
description	varchar(255)	Нет                             value domain attribute - description 
title           varchar(255)	Нет                             value domain attribute - title 
department	varchar(255)	Нет                             value domain attribute - department 
company         varchar(255)	Нет                             value domain attribute - company 


