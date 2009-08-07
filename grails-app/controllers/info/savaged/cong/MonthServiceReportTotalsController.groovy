package info.savaged.cong

class MonthServiceReportTotalsController {

    def monthServiceReportTotalsService

    def index = {
        def cal = Calendar.getInstance()
        if (!flash.starting) {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1)
            flash.starting = cal.getTime()
        }
            
        if (request.method == 'POST') {
            def year = Integer.parseInt(params.starting_year)
            def month = Integer.parseInt(params.starting_month)

            def serviceReportTotals = monthServiceReportTotalsService.build(month, year)

            cal.set(year, month-1, 1)
            flash.starting = cal.getTime()
            
            render view:'show', model:[serviceReportTotals:serviceReportTotals]
        }
    }

    def show = {}
}
