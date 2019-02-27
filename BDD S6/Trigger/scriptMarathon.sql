
--MARATHON 

drop table mar_classement;
drop table mar_course;
drop table mar_coureur;
drop table mar_categorie;
drop table mar_capteur;
drop table mar_parametre;


create table MAR_PARAMETRE (
	nom varchar2(20) constraint  MAR_PARAMETRE_PK PRIMARY KEY,
        valeur varchar2(20) NOT NULL
);

insert into  MAR_PARAMETRE values ('date', '23/02/2018');
insert into  MAR_PARAMETRE values ('nbInscrits', 0);


Create table MAR_CATEGORIE (
	libelle varchar2(20) constraint  MAR_CATEGORIE_PK PRIMARY KEY,
	ageMin number(3),
	ageMax number(3)
);

insert into MAR_CATEGORIE values ('Poussin', 0,9);
insert into MAR_CATEGORIE values ('Pupille', 10,11);
insert into MAR_CATEGORIE values ('Benjamin', 12,13);
insert into MAR_CATEGORIE values ('Minime', 14,15);
insert into MAR_CATEGORIE values ('Cadet', 16,17);
insert into MAR_CATEGORIE values ('Junior', 18,19);
insert into MAR_CATEGORIE values ('Espoir', 20,21);
insert into MAR_CATEGORIE values ('Senior', 23,39);
insert into MAR_CATEGORIE values ('Veteran', 40,200);


create table MAR_COUREUR (
	idCoureur number(3) constraint  MAR_COUREUR_PK PRIMARY KEY,
	nom varchar2(20) NOT NULL,
	age number(3) NOT NULL,
	categorie varchar2(20) NOT NULL constraint  MAR_CATEGORIE_IN_COUREUR_FK references MAR_CATEGORIE
);

create table MAR_CAPTEUR (
	idCapteur varchar2(5) constraint  MAR_CAPTEUR_PK PRIMARY KEY,
	distance number(6) not null
);

insert into MAR_CAPTEUR values ('C134', 1000);
insert into MAR_CAPTEUR values ('C234', 3000);
insert into MAR_CAPTEUR values ('C334', 5000);
insert into MAR_CAPTEUR values ('C434', 10000);

create table MAR_COURSE (
	idCoureur number(3) constraint  MAR_COUREUR_IN_COURSE_FK  references MAR_COUREUR,
	idCapteur varchar2(5) constraint  MAR_CAPTEUR_IN_COURSE_FK  references MAR_CAPTEUR,
	chrono number (6) not null,
	constraint MAR_COURSE_PK PRIMARY KEY (idCoureur, idCapteur)
);

create table MAR_CLASSEMENT (
	idCoureur number(3) constraint  MAR_COUREUR_IN_CLASSEMENT_FK references MAR_COUREUR ON DELETE CASCADE,
	distance number(6) ,
	chrono number (6),
        rang number(4)
);


create sequence mar_gen START WITH 1;


create or replace trigger calcul_id_categorie
before insert or update of age,idCoureur
on Mar_Coureur
for each row 
begin 
  if inserting then 
    :new.idCoureur := mar_gen.nextval;
  else
    :new.idCoureur := :old.idCoureur;
  end if;
  select libelle into :new.categorie
  from Mar_categorie where :new.age between agemin and agemax;
end;
/


create or replace trigger maj_add_classement
after insert or delete 
on Mar_Coureur
for each row
begin
  if inserting then
    insert into MAR_CLASSEMENT (idCoureur) 
    values ( :new.idCoureur);
    Update mar_parametre set valeur = valeur + 1
    where nom='nbInscrits'; 
  else
        Update mar_parametre set valeur = valeur - 1
          where nom='nbInscrits'; 

  end if;
end;
/

create or replace trigger maj_maj_classement
after insert 
on Mar_Course
for each row
begin 
  Update MAR_CLASSEMENT m1
  set rang = (select count(*) + 1
  from mar_classement m2 
  where m1.chrono < m2.chrono)
  where m1.idCoureur = idCoureur;
end;
    
    

  


