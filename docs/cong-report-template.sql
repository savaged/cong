select 
    m.group_unit,
    m.lastname + ', ' + m.firstname as publisher,
    0 as books,
    0 as brochures,
    0 as hours,
    0 as magazines,
    0 as return_visits,
    0 as studies,
    '' as comments
from member m
where m.IS_PUBLISHER = 1 
and m.id not in (
    select s.member_id 
    from member_state s 
    where s.NAME in ('INACTIVE', 'DISFELLOWSHIPPED')
    and s.ENDING is null
) 
order by m.group_unit, m.lastname, m.firstname
