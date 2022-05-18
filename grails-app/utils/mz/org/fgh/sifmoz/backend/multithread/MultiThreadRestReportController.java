package mz.org.fgh.sifmoz.backend.multithread;

import grails.rest.RestfulController;
import mz.org.fgh.sifmoz.backend.convertDateUtils.ConvertDateUtils;
import mz.org.fgh.sifmoz.report.ReportGenerator;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.SessionFactoryUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public abstract class MultiThreadRestReportController<T> extends RestfulController<T> implements ReportExecutor {
    protected ReportSearchParams searchParams;
    private static ExecutorService executor;
    protected String processStage;
    @Autowired
    protected SessionFactory sessionFactory;
    protected ReportProcessStatus processStatus;
    public static final String PROCESS_STATUS_INITIATING = "Iniciando processamento";
    public static final String PROCESS_STATUS_PROCESSING_FINISHED = "Processamento terminado";

    public MultiThreadRestReportController(Class<T> resource) {
        super(resource);
        executor = ExecutorThreadProvider.getInstance().getExecutorService();
    }

    public ReportSearchParams getSearchParams() {
        return searchParams;
    }

    protected void initReportParams(ReportSearchParams searchParams) {
        this.searchParams = searchParams;
        this.searchParams.determineStartEndDate();
        this.processStatus = new ReportProcessStatus(getSearchParams().getId(), getProcessingStatusMsg(), 0, countRecordsToProcess(), this.searchParams);

        getSession().setAttribute(getSearchParams().getId(), this.processStatus);
    }


    /**
     * Processa o relatório
     */
    protected void doProcessReport() {
        executor.execute(this);
        processStage = PROCESS_STATUS_PROCESSING_FINISHED;
        updateProcessingStatus();
    }

    protected void updateProcessingStatus() {
        this.processStatus.setProcessedRecs(countProcessedRecs());
        this.processStatus.setMsg(getProcessingStatusMsg());
        //getSession().setAttribute(getSearchParams().getId(), this.processStatus);
    }

    public ReportProcessStatus getProcessStatus() {
        return processStatus;
    }

    protected abstract int countProcessedRecs();

    protected abstract int countRecordsToProcess();

    protected abstract String getProcessingStatusMsg();

    protected byte[] printReport(String reportId, String fileType, String path, String report) throws SQLException {
        Map<String, Object> map = new HashMap<>();
        Connection connection = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();

        //InputStream subInputStream = getClass().getResourceAsStream(path+"/MmiaRegimesReport.jasper");
        File initialFile = new File(path+"/StockInfo.jasper");
        try {

            InputStream targetStream = new FileInputStream(initialFile);
            JasperReport subJasperReport = (JasperReport) JRLoader.loadObject(targetStream);


        map.put("path", path);
        map.put("reportId", reportId);
        map.put("regimenSubReport", subJasperReport);
        map.put("username", "Test_user");
        map.put("dataelaboracao", ConvertDateUtils.getCurrentDate());
        } catch (JRException | FileNotFoundException e) {
            e.printStackTrace();
        }

        return ReportGenerator.generateReport(map,path, report, connection);
    }
}
