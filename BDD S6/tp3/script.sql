drop table tp_livre;
drop table tp_personne;
drop sequence seq_personne;
drop sequence seq_livre;
drop package paq_biblio;

create sequence Seq_Personne ;
create sequence Seq_livre ;

create table TP_Personne (
  id number(2) constraint tp_personne_pk primary key,
  nom varchar2(20) not null,
  prenom varchar2(20) not null
) ;

-- id_emprunte is null si le tp_livre n'est pas emprunté
-- idem pour id_reserve
create table Tp_livre (
  id number(2) constraint tp_livre_pk primary key,
  titre varchar2(50),
  id_emprunte constraint personne_emprunte_le_livre_fk references tp_Personne(id), 
  id_reserve  constraint personne_reserve_le_livre_fk references tp_Personne(id)
) ;

insert into tp_personne values (Seq_Personne.nextval,'Canet','Guillaume') ;
insert into tp_personne values (Seq_Personne.nextval,'Debbouze','Jamel') ;
insert into tp_personne values (Seq_Personne.nextval,'Tautou','Audrey') ;
insert into tp_personne values (Seq_Personne.nextval,'Moreau','Yolande') ;

insert into tp_livre (id, titre) values (Seq_Livre.nextval,'Asterix le gaulois') ;
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'Tintin au tibet') ;
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'La zizanie') ;
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'XIII - la nuit du 3 août') ;
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'Un goût de rouille et d''os') ;
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'Introduction aux bases de données') ;
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'Voyage au centre de la terre') ;
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'Le Papyrus de César');
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'Au bonheur des dames');
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'Germinal');
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'Le Horla');
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'Une vie');
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'Madame Bovary');
insert into tp_livre (id, titre) values (Seq_Livre.nextval,'La cuisine pour les nuls');