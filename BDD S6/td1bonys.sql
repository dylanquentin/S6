--Marins qui n'ont pas réservé
Select nom,count(*) as nb_reservation
from Marin natural join Reserve
group by mid,mnom;

--Si on veut tous les marins 
Select mnom, (select count(*) from reserve r where r.mid=m.mid)
from Marin m;

--Si on veut tous les marins -- SOlution 2
Select mnom, count(bid) as nb_reservations 
from Marin m left outer join reserve r on m.mid = r.mid
group by m.mid, mnom;
