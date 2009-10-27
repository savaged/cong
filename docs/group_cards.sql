select 
m.lastname + ', ' + m.firstname as fullname,
r.books,
r.brochures,
r.hours,
r.magazines,
r.return_visits,
r.studies
from service_report r
join member m on r.publisher_id = m.id
where m.group_unit = 'VULCAN_WAY'

