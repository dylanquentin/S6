Drop table Livre cascade constraints;
Drop table Auteur cascade constraints;
Drop table Ecrit cascade constraints;
Drop table Enseignant cascade constraints;
Drop table Diplome cascade constraints;
Drop table Matiere cascade constraints;
Drop table Recommendation cascade constraints;


create table Livre (
idLivre Number,
titre varchar (40),
editeur varchar (40),
constraint Livre_PK primary key(idLivre)
);

create table Auteur (
idAuteur Number,
nom varchar(20),
prenom varchar(20),
constraint nomNotNull check(nom is not null),
constraint Auteur_PK primary key (idAuteur) 
);

create table Ecrit (
idAuteur Number,
idLivre Number,
constraint Ecrit_PK primary key (idAuteur, idLivre),
constraint Ecrit_Auteur_FK foreign key(idAuteur) references Auteur (idAuteur),
constraint Ecrit_Livre_FK foreign key(idLivre) references Livre (idLivre)
);

create table Enseignant(
idEnseignant number,
nom varchar(20),
prenom varchar(20),
email varchar(30),
telephone varchar(15),
constraint emailNotNull check((email is not null) or (telephone is not null)),
constraint Enseignant_PK primary key (idEnseignant)
);

create table Diplome(
codeDiplome number(6),
intitule varchar(20),
constraint intituleNotNull check(intitule is not null),
constraint Diplome_Pk primary key(codeDiplome)
);

create table Matiere(
codeDiplome number(6),
numMatiere number,
titre varchar(20),
constraint titreNotNull check(titre is not null),
constraint Matiere_PK primary key(numMatiere,codeDiplome),
constraint Matiere_Diplome_FK 
foreign key(codeDiplome) references Diplome (codeDiplome)
);



create table Recommendation(

idLivre Number,
numMatiere Number,
idEnseignant Number,
codeDiplome Number(6),

constraint Recommendation_PK PRIMARY KEY (idLivre,numMatiere,codeDiplome),

constraint Recommendation_Livre_FK
foreign key (idLivre) references Livre(idLivre),

constraint Recommendation_Matiere_FK
foreign key (numMatiere, codeDiplome) references Matiere(numMatiere, codeDiplome),

constraint Recommendation_Ens_FK
foreign key (idEnseignant) references Enseignant(idEnseignant)
);




