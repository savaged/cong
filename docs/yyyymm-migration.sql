update ACTIVE_PUBLISHER_COUNT set YYYYMM = (YEAR*100)+ MONTH
update MEETING_ATTENDANCE set YYYYMM = (YEAR*100)+ MONTH
update SERVICE_REPORT_TOTALS set YYYYMM = (YEAR*100)+ MONTH
update SERVICE_REPORT set YYYYMM = (YEAR*100)+ MONTH

alter table active_publisher_count drop column month
alter table active_publisher_count drop column year
alter table meeting_attendance drop column month
alter table meeting_attendance drop column year
alter table service_report_totals drop column month
alter table service_report_totals drop column year
alter table service_report drop column month
alter table service_report drop column year
