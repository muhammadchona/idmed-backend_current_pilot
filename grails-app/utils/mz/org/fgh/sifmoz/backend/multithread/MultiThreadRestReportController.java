package mz.org.fgh.sifmoz.backend.multithread;

import grails.rest.RestfulController;
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutorService;

public abstract class MultiThreadRestReportController<T> extends RestfulController<T> implements ReportExecutor {
    protected ReportSearchParams searchParams;
    private static ExecutorService executor;
    protected long qtyRecordsToProcess;

    public MultiThreadRestReportController(Class<T> resource) {
        super(resource);
        executor = ExecutorThreadProvider.getInstance().getExecutorService();
    }

    public ReportSearchParams getSearchParams() {
        return searchParams;
    }

    protected void initReportParams(ReportSearchParams searchParams) {
        switch (searchParams.getPeriodType()) {
            case "1":
                break;
            case "2":
                int month = Integer.parseInt(searchParams.getPeriod());
                Date startDateTemp = DateUtils.addMonths(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, month, searchParams.getYear()), -1);
                int yearStartDate = DateUtils.toCalendar(startDateTemp).get(Calendar.YEAR);
                int monthStartDate = DateUtils.toCalendar(startDateTemp).get(Calendar.MONTH);
                searchParams.setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, monthStartDate, yearStartDate));
                searchParams.setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, month, searchParams.getYear()));
                break;
            case "3":
                switch (searchParams.getPeriod()) {
                    case "1":
                        searchParams.setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, 12, searchParams.getYear() - 1));
                        searchParams.setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, 4, searchParams.getYear()));
                        break;
                    case "2":
                        searchParams.setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, 4, searchParams.getYear() - 1));
                        searchParams.setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, 8, searchParams.getYear()));
                        break;
                    case "3":
                        searchParams.setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, 8, searchParams.getYear() - 1));
                        searchParams.setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, 12, searchParams.getYear()));
                        break;
                }
                break;
            case "4":
                switch (searchParams.getPeriod()) {
                    case "1":
                        searchParams.setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, 12, searchParams.getYear() - 1));
                        searchParams.setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, 6, searchParams.getYear()));
                        break;
                    case "2":
                        searchParams.setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, 6, searchParams.getYear() - 1));
                        searchParams.setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, 12, searchParams.getYear()));
                        break;
                }
                break;
            case "5":
                searchParams.setStartDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(21, 12, searchParams.getYear() - 1));
                searchParams.setEndDate(ConvertDateUtils.getDateFromDayAndMonthAndYear(20, 12, searchParams.getYear()));
                break;

        }
        this.searchParams = searchParams;
        this.qtyRecordsToProcess = getRecordsQtyToProcess();
    }


    /**
     * Processa o relatório
     */
    protected void doProcessReport() {
        executor.execute(this);
    }

    public abstract long getRecordsQtyToProcess();

    public abstract void getProcessedRecordsQty(String reportId);

    public abstract void printReport(String reportId, String fileType);
}
