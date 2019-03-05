-- creation du schema 'Rapido' --
---------------------------------

/*
drop sequence gen_clef ;
drop table ff_consommation ;
drop table ff_magasin ;
drop table ff_constitue ;
drop table ff_menu ;
drop table ff_simple ;
drop table ff_produit ;
drop package paq_produits ;
*/

---------------------------------
-- creation du schema 'Rapido' --
---------------------------------
create table FF_PRODUIT(
  p_ref number(4) constraint ff_produit_pkey PRIMARY KEY,
  nom varchar2(30) not null,
  prix number(5,2),
  taille varchar2(5) not null,
  constraint nom_unique unique(nom), -- pas 2 produits avec le meme nom
  constraint enum_taille check (taille in ('petit','moyen','grand')),
  constraint prix_positif check (prix > 0)
);

create table FF_SIMPLE(
  s_ref number(4) constraint ff_simple_pkey PRIMARY KEY
  constraint ff_simple_ff_produit_fkey REFERENCES FF_PRODUIT on delete cascade,
  categ varchar2(15),
  constraint enum_categ check (categ in ('boisson','dessert','salade','accompagnement','sandwich'))
);

create table FF_MENU(
  m_ref number(4) constraint ff_menu_pkey PRIMARY KEY
  constraint ff_menu_ff_produit_fkey REFERENCES FF_PRODUIT on delete cascade,
  promo varchar2(20)
);

create table FF_CONSTITUE(
  ref_menu number(4) constraint ff_constitue_ff_menu_fkey REFERENCES FF_MENU on delete cascade,
  ref_simple number(4) constraint ff_constitue_ff_simple_fkey REFERENCES FF_SIMPLE,
  constraint ff_constitue_pkey PRIMARY KEY(ref_menu, ref_simple)
);

create table FF_MAGASIN(
  m_ref number(4) constraint ff_magasin_pkey PRIMARY KEY,
  nom varchar2(10) not null,
  ville varchar2(10) not null
);

create table FF_CONSOMMATION(
  estampille TIMESTAMP not null,
  ref_produit number(4) not null constraint consom_ff_produit_fkey REFERENCES FF_PRODUIT,
  ref_magasin number(4) not null constraint consom_ff_magasin_fkey REFERENCES FF_MAGASIN
);

-- sequence pour les clefs des produits
create sequence gen_clef
increment by 1
start with 1 ;


-- stock 

create table FF_STOCK(
  quantite number(9),
  ref_produit number(4) not null constraint stock_ff_produit_fkey REFERENCES FF_PRODUIT,
  ref_magasin number(4) not null constraint stock_ff_magasin_fkey REFERENCES FF_MAGASIN,
  constraint ff_stock_pkey PRIMARY KEY(ref_produit , ref_magasin)
);
/

create or replace trigger triggerInsertAfterProduit
  after insert on ff_simple 
  for each row
  begin
    insert into ff_stock values(0,(select m_ref from ff_magasin), :new.s_ref);
  end;
/

create or replace trigger triggerInsertAfterMag 
  after insert on ff_magasin for each row
  begin 
    insert into ff_stock values(0, (select s_ref from ff_simple), :new.m_ref);
  end;
/

create or replace trigger triggerAfterConso
  after insert on ff_consommation for each row
  begin 
    PAQ_STOCK.diminuerQuantite_produitSimple(:new.ref_produit, :new.ref_magasin, 1);
  end;
/