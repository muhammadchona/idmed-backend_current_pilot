package mz.org.fgh.sifmoz.report

import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource

import java.sql.Connection

class ReportGenerator {

    static byte[] generateReport(Map<String, Object> parameters, Collection reportObjects, String reportPath) {
        try {

            JRBeanCollectionDataSource mapCollectionDataSource = new JRBeanCollectionDataSource(reportObjects)
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath)
            def jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mapCollectionDataSource)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        }
    }

    static byte[] generateReport(Map<String, Object> parameters, String reportPath, String report, Connection connection) {
        try {
            String reportUri = reportPath+"/"+report
            JasperReport jasperReport = JasperCompileManager.compileReport(reportUri)
            def jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        }
    }
}
