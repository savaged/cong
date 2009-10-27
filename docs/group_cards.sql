select 
m.lastname + ', ' + m.firstname as fullname,
r.yyyymm,
r.books,
r.brochures,
r.hours,
r.magazines,
r.return_visits,
r.studies,
r.comments
from service_report r
join member m on r.publisher_id = m.id
where m.group_unit = 'VULCAN_WAY'
order by fullname, r.yyyymm
