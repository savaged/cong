select g.name, m.lastname, m.firstname from member m
join group_unit g on m.group_unit_id = g.id
order by g.name, m.lastname, m.firstname

select s.name, m.lastname, m.firstname, s.starting, s.ending
from member m
join state s on m.id = s.member_id
order by s.name, m.lastname, m.firstname

