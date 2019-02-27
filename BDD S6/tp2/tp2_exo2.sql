--2.3--
--1--
Select * from TD2_Film where FI_ANNEE=2014;

--2
Select ci.CI_NOM, se.SE_HORAIRE
from TD2_SEANCE se 
inner join TD2_CINEMA ci on ci.CI_REF= se.CI_REF
inner join TD2_Film fi on fi.FI_REF = se.FI_REF
where fi.FI_TITRE = 'Gravity';

--3
Select count(*) 
from TD2_ASSISTE 
where SE_REF='se10';

--4
Select avg(count(*))
from TD2_ASSISTE
group by SE_REF;

--5
Select sp.SP_REF, sp.SP_NOM, sp.SP_PRENOM ,count(ass.SP_REF)
from TD2_ASSISTE ass 
inner join TD2_SPECTATEUR sp on ass.SP_REF = sp.SP_REF
group by ass.SP_REF,sp.SP_REF,sp.SP_NOM,sp.SP_PRENOM;

-- Pour vérifier les réponses
Select * from TD2_ASSISTE inner join TD2_SPECTATEUR 
on TD2_SPECTATEUR.SP_REF = TD2_ASSISTE.SP_REF
order by TD2_ASSISTE.SP_REF ASC;

--6
Select distinct TD2_SPECTATEUR.SP_NOM, TD2_SPECTATEUR.SP_PRENOM
from TD2_ASSISTE inner join TD2_SPECTATEUR 
on TD2_SPECTATEUR.SP_REF = TD2_ASSISTE.SP_REF
where TD2_ASSISTE.SP_REF not in (
Select TD2_ASSISTE.SP_REF
from TD2_ASSISTE
where TD2_ASSISTE.SE_AVIS != 'mauvais' 
);

--9
Select SP_REF, SP_NOM, SP_PRENOM, FI_TITRE 
from TD2_ASSISTE ass
inner join TD2_SPECTATEUR sp using (SP_REF)
inner join TD2_SEANCE se using (SE_REF)
inner join TD2_FILM fi using (FI_REF)
group by SP_REF, SP_NOM, SP_PRENOM, FI_TITRE
having count(distinct SE_AVIS)>1 ;

--10
Select CI_REF,CI_NOM, CI_VILLE, sum(Decode(to_char(SE_Horaire, 'D'),
5,1,
6,1,
2,1,
3,1,
4,1,0)) as semaine,
sum(Decode(to_char(SE_Horaire, 'D'),
1,1,
7,1,0)) as weekend
from TD2_CINEMA 
inner join TD2_SEANCE using (CI_REF)
group by CI_REF,CI_Nom, Ci_Ville;

--12
with req12 as(
              select to_char(SE_Horaire, 'D')as jour,count(*)as nbrAvis 
              from TD2_ASSISTE 
              inner join TD2_SEANCE on TD2_ASSISTE.SE_REF=TD2_SEANCE.SE_REF
              where TD2_ASSISTE.SE_AVIS='tres bon'
              group by to_char(SE_Horaire, 'D')
)
select jour from req12
where nbrAvis=(select max(nbrAvis) from req12);

--13 

with req13 as(

            select TD2_CINEMA.CI_VILLE as VILLE,TD2_CINEMA.CI_NOM as NOM ,to_char(SE_Horaire, 'D')as jour,count(*)as nbrAvis 
            from TD2_ASSISTE 
            inner join TD2_SEANCE on TD2_ASSISTE.SE_REF=TD2_SEANCE.SE_REF
            inner join TD2_CINEMA on TD2_SEANCE.CI_REF = TD2_CINEMA.CI_REF
            
            where TD2_ASSISTE.SE_AVIS='tres bon'
            group by to_char(SE_Horaire, 'D'),TD2_CINEMA.CI_VILLE,TD2_CINEMA.CI_NOM

)
select req.VILLE,req.NOM,req.jour from req13 req
group by req.VILLE,req.NOM,req.jour,req.nbrAvis
having req.nbrAvis in (select max(nbrAvis) from req13 where req.VILLE = req13.VILLE);

--14

with req14 as ( Select fi_titre as titre, sum(decode(SE_AVIS, 'tres bon', 1, 'pas mal',1,0)) as positif,
              count(*) as total
              from TD2_SEANCE
              inner join TD2_FILM USING(FI_REF)
              inner join TD2_ASSISTE USING (SE_REF)
              group by fi_titre)
Select titre
from req14
where (positif/total)>=0.8;

--15
with req15Paris as ( Select fi_titre as titre, sum(decode(SE_AVIS, 'tres bon', 1,0)) as positifParis,
                    count(*) as totalParis
                    from TD2_SEANCE
                    inner join TD2_FILM USING(FI_REF)
                    inner join TD2_ASSISTE USING (SE_REF)
                    inner join TD2_CINEMA USING (CI_REF)
                    where CI_VILLE = 'PARIS' 
                    group by fi_titre),
     req15Lille as ( Select fi_titre as titre, sum(decode(SE_AVIS, 'tres bon', 1,0)) as positifLille,
                    count(*) as totalLille
                    from TD2_SEANCE
                    inner join TD2_FILM USING(FI_REF)
                    inner join TD2_ASSISTE USING (SE_REF)
                    inner join TD2_CINEMA USING (CI_REF)
                    where CI_VILLE = 'LILLE' 
                    group by fi_titre)
Select titre, (positifLille / totalLille) as ratioLille, 
(positifParis / totalParis) as ratioParis
from req15Paris inner join req15Lille USING (titre);











