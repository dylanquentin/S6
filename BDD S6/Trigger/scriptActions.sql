create table Action (
  id         Number (5),
  nom        Varchar2 (20), -- nom de l'entreprise
  total      Number (10), -- nombre total de titres
  disponible Number (10), -- nombre de titres disponibles à l'achat
  constraint Action_PK primary key (id) ) ;
  
create table Historique (
  id_action  Number (5),
  jour       Date,
  prix       Number (10, 2) not null, -- prix à la cloture de la journée
  constraint Historique_PK primary key (id_action, jour),
  constraint Historique_Action_FK
     foreign key (id_action) references Action (id) ) ;

--Q1
alter table action add constraint verif_titre
check (total >= 0 and disponible >=0 and disponible <= total);

--Q2
alter table action add constraint check_null
check (total is not null or disponible is null);

--Q3
Create or replace trigger historique_constant
before update on Historique
begin
  raise_application_error(-20000, 'interdiction de modifier');
end;
/

--Q4
Create or replace trigger nom_maj 
before insert or update of nom on Action
for each row
begin
  :new.nom := UPPER(:new.nom);
end;
/