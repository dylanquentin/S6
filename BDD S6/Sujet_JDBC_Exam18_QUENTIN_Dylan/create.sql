--QUENTIN Dylan 

drop table Election;

create table Election (
  liste VARCHAR(20) constraint election_pkey PRIMARY KEY,
  nbVoix integer not null,
  nbSieges integer default 0

);


insert into Election (liste,nbVoix) values ( 'liste A', 32000); 
insert into Election (liste,nbVoix) values ( 'liste B', 25000);
insert into Election (liste,nbVoix) values ( 'liste C', 16000);
insert into Election (liste,nbVoix) values ( 'liste D', 12000);
insert into Election (liste,nbVoix) values ( 'liste E', 8000);
insert into Election (liste,nbVoix) values ( 'liste F', 4500);
insert into Election (liste,nbVoix) values ( 'liste G', 2500);

