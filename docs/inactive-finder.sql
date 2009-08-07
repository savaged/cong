select m.lastname, m.firstname
from SERVICE_REPORT r
join MEMBER m on r.publisher_id = m.id and r.YEAR > 2008 and r.MONTH > 2
group by r.publisher_id, m.lastname, m.firstname
having sum(r.hours) < 1
