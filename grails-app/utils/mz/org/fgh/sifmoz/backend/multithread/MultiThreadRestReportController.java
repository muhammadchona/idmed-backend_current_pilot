package mz.org.fgh.sifmoz.backend.multithread;

import grails.rest.RestfulController;

import java.util.Date;
import java.util.concurrent.ExecutorService;

public abstract class MultiThreadRestReportController<T> extends RestfulController<T> implements ReportExecutor{
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

    protected void initReportParams (ReportSearchParams searchParams) {
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
