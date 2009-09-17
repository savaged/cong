-- manual_year_analysis.sql

-- meetings
-- TODO input full year
-- TODO compare this to the GUI
select meeting, sum(total) / sum(meetings) as average from meeting_attendance
group by meeting


-- active publishers
-- TODO compare month report: 102
select count(m.*) from member m
where m.immersion is not null
--and m.immersion > '1920-01-01'
and m.is_publisher = 1
and m.lastname + m.firstname not in (
select m.lastname + m.firstname from member m
join member_state s on m.id = s.member_id and (s.ending is null or s.ending < '1980-01-01')
where s.name in ('INACTIVE', 'DISFELLOWSHIPPED')
group by m.lastname, m.firstname
)


-- active baptized publishers
select count(m.*) from member m
where m.immersion is not null
and m.immersion > '1920-01-01'
and m.is_publisher = 1
and m.lastname + m.firstname not in (
select m.lastname + m.firstname from member m
join member_state s on m.id = s.member_id and (s.ending is null or s.ending < '1980-01-01')
where s.name in ('INACTIVE', 'DISFELLOWSHIPPED')
group by m.lastname, m.firstname
)


-- regular publishers
--select category, min(publishers) from service_report_totals group by category
select count(m.*) from member m
join service_report aug on m.id = aug.publisher_id and aug.year = 2009 and aug.month = 8
join service_report jul on m.id = jul.publisher_id and jul.year = 2009 and jul.month = 7
join service_report jun on m.id = jun.publisher_id and jun.year = 2009 and jun.month = 6
join service_report may on m.id = may.publisher_id and may.year = 2009 and may.month = 5
join service_report apr on m.id = apr.publisher_id and apr.year = 2009 and apr.month = 4
join service_report mar on m.id = mar.publisher_id and mar.year = 2009 and mar.month = 3


-- auxiliary pioneers
select m.lastname, m.firstname from member m
join service_report s on m.id = s.publisher_id 
where s.comments like '%Aux%pio%'
group by m.lastname, m.firstname


-- new publishers
-- from memory & cards: 0


-- inactive publishers
select m.lastname, m.firstname, s.starting from member m
join member_state s on m.id = s.member_id 
    and s.name = 'INACTIVE'
    and (s.ending is null or s.ending < '1980-01-01')
    and s.starting > '2008-04-01'


-- reactivated publishers
select m.lastname, m.firstname, s.ending from member m
join member_state s on m.id = s.member_id 
    and s.name = 'INACTIVE'
    and (s.ending is not null and s.ending > '2009-08-31')


-- regular pioneers
-- TODO input Ana's full year
select m.lastname, m.firstname, s.name, sum(r.hours) from member m
join member_state s on m.id = s.member_id and (s.ending is null or s.ending < '1980-01-01')
join service_report r on m.id = r.publisher_id
group by m.lastname, m.firstname, s.name
having s.name = 'REGULAR_PIONEER'


-- elders
select m.lastname, m.firstname, s.name from member m
join member_state s on m.id = s.member_id and (s.ending is null or s.ending < '1980-01-01')
join service_report r on m.id = r.publisher_id
group by m.lastname, m.firstname, s.name
having s.name = 'ELDER'


-- servants
select m.lastname, m.firstname, s.name from member m
join member_state s on m.id = s.member_id and (s.ending is null or s.ending < '1980-01-01')
join service_report r on m.id = r.publisher_id
group by m.lastname, m.firstname, s.name
having s.name = 'SERVANT'
