set serveroutput on;

Select * from user_tab_columns where table_name like 'TD2%';

execute lire_simple('user_tables');