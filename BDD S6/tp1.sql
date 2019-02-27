create table Client (
id Number (5), -- entier de 5 chiffres d´ecimaux
nom Varchar2 (20), -- cha^ıne d'au plus 20 caract`eres
solde Number (6, 2), -- de -9999.99 `a +9999.99
constraint Client_PK primary key (id)
-- garantit l'unicit´e de id pour chaque ligne
) ;


select * from tab;

insert into Client values ( 1, 'toto' , 200.0) ;

insert into Client values ( 2, 'titi' , 20.0) ;
insert into Client values ( 3, 'titi' , 20.0) ;
insert into Client values ( 5, 'tata' , 120.0) ;
insert into Client values ( 15, 'bof' , 150.0) ;
insert into Client values ( 16, 'bif' , 150.0) ;


select solde from Client;

select nom, solde from Client;

select distinct solde from Client;





--Q1.3

--1

select nom from Client where solde >= 150;

--2
select * from CLIENT
where mod(id, 2) = 0 ;

--3
select * from Client where nom like '%o%';

--1.4

insert into client values (20,'riri',null);
insert into client(id,nom) values (21,'fifi');
insert into client(id,solde) values (22,150.0) ;


--1.5
insert into client(nom) values ('loulou');
-- Il manque l'ID

--1.6
select count(*) from client ;
select count(nom) from client ;
select avg(solde) from client ;


--1.1
update Client set solde = solde + 10.0 ;


--1.7
update Client
set solde = solde - 30.0, nom='toto'
where id = 2 ;

update Client
set solde = solde + 30.0
where solde between 100.0 and 180.0 ;
--L'id fait la diff

--1.8

delete from client where id = 4 ;
delete from client where id = 1 ;


select * from Client;

--EXERCICE 2 ----------------------------------------

create table Producteur (
id Number (5),
nom Varchar2 (20),
constraint Producteur_PK primary key (id)
) ;


create table Produit (
id Number (5),
nom Varchar2 (20),
prix_unitaire Number (8, 2),
producteur Number (5),
constraint Produit_Producteur_FK
foreign key (producteur) references Producteur (id),
constraint Produit_PK primary key (id)
) ;

insert into Producteur values ( 1, 'Bricolot') ;
insert into Producteur values ( 2, 'Fruit''n Fibre') ;
insert into Produit (id, nom, prix_unitaire, producteur)
values ( 1, 'clou', 11.0, 1) ;
insert into Produit (id, nom, prix_unitaire, producteur)
values ( 2, 'robinet', 30.0, 1) ;
insert into Produit (id, nom, prix_unitaire, producteur)
values ( 3, 'kiwi', 0.3, 2) ;
--2.1.1
Select * from CLIENT c cross join PRODUCTEUR p;

--2.1.2
select p1.*, p2.*
from Producteur p1 cross join Producteur p2 ;

--2.2.1
select p.nom, pr.nom 
from Produit p inner join Producteur pr on p.PRODUCTEUR = pr.ID;

--2.2.2
select p.NOM,p1.nom,p.PRIX_UNITAIRE,p1.PRIX_UNITAIRE
from PRODUIT p
cross join PRODUIT p1 
where p1.PRIX_UNITAIRE > p.PRIX_UNITAIRE;


------------EXERCICE 3 ----------------------------

create table Commande (
idClient Number (5),
idProduit Number (5),
quantite Number (5),
constraint Commande_PK primary key (idClient, idProduit),
constraint Commande_Client_FK
foreign key (idClient) references Client (id),
constraint Commande_Produit_FK
foreign key (idProduit) references Produit (id)
) ;

Insert into COMMANDE values (2,2,3);
Insert into COMMANDE values (2,3,20);
Insert into COMMANDE values (5,1,100);
Insert into COMMANDE values (5,3,10);

--3.1--
Select cl.nom,p.nom, c.QUANTITE
from COMMANDE c
inner join CLIENT cl on c.IDCLIENT = cl.ID
inner join PRODUIT p on c.IDPRODUIT = p.ID;

--2 solution 1 --

Select distinct cl.NOM
from Commande c 
inner join CLIENT cl on c.IDCLIENT = cl.ID;


--2 solution 2--
Select distinct cl.nom 
from client cl 
where cl.ID in ( 
  Select c.IDCLIENT
  from COMMANDE c
);

--3.2--
Select count(*), min(PRIX_UNITAIRE), max(PRIX_UNITAIRE)
from Produit;

--3.3--
Select cl.nom, count(c.quantite)
from COMMANDE c
inner join Client cl on c.IDCLIENT = cl.id
group by cl.NOM
order by cl.NOM asc;

Select cl.nom , count(C.QUANTITE)
from Commande C
right join Client cl on c.IDCLIENT = cl.id
group by cl.NOM
order by cl.NOM asc;

--3.3--
insert into Produit (id, nom, prix_unitaire, producteur)
values ( 11, 'clou', 11.0, 2) ;
insert into Produit (id, nom, prix_unitaire, producteur)
values ( 15, 'clou', 11.0, 2) ;
Select pr.nom,count(*)
from PRODUIT p 
inner join PRODUCTEUR pr on p.PRODUCTEUR = pr.ID
group by pr.NOM
having count(*) >= 2;




Select * from Producteur;
Select * from Produit;
Select * from COMMANDE;
Select * from client;



































