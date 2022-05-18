package mz.org.fgh.sifmoz.report

import net.sf.dynamicreports.jasper.base.export.JasperCsvExporter
import net.sf.dynamicreports.jasper.base.export.JasperExcelApiXlsExporter
import net.sf.jasperreports.engine.JRExporter
import net.sf.jasperreports.engine.JRExporterParameter
import net.sf.jasperreports.engine.JasperCompileManager
import net.sf.jasperreports.engine.JasperExportManager
import net.sf.jasperreports.engine.JasperFillManager
import net.sf.jasperreports.engine.JasperReport
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource
import net.sf.jasperreports.engine.export.JRXlsExporter
import net.sf.jasperreports.engine.export.JRXlsExporterParameter
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter
import net.sf.jasperreports.export.Exporter
import net.sf.jasperreports.export.ExporterInput
import net.sf.jasperreports.export.SimpleExporterInput
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput
import net.sf.jasperreports.export.SimpleXlsReportConfiguration
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration

import java.sql.Connection

class ReportGenerator {

    static byte[] generateReport(Map<String, Object> parameters, List<Map<String, Object>> reportObjects, String reportPath ) {
        try {
            JRMapCollectionDataSource mapCollectionDataSource = new JRMapCollectionDataSource(reportObjects)
            JasperReport jasperReport = JasperCompileManager.compileReport(reportPath)
            def jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mapCollectionDataSource)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
           // render(file: byteArrayOutputStream.toByteArray(), contentType: 'application/pdf')
            //     response.setContentType("application/pdf")  //<-- you'll have to handle this dynamically at some point
            //    response.setHeader("Content-disposition", "attachment;filename=${11}")
            //   response.outputStream <<  byteArrayOutputStream.toByteArray()
        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        }
    }

    static byte[] generateReport(Map<String, Object> parameters, List objects, String reportPath, String report ) {
        try {
            JRBeanCollectionDataSource mapCollectionDataSource = new JRBeanCollectionDataSource(objects)
           // String reportUri = reportPath+"/"+report
            JasperReport jasperReport = JasperCompileManager.compileReport(report)
            def jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mapCollectionDataSource)
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
            JasperExportManager.exportReportToPdfStream(jasperPrint, byteArrayOutputStream)
            return byteArrayOutputStream.toByteArray()
        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        }
    }

    static byte[] generateReportExcel(Map<String, Object> parameters, List objects, String reportPath, String report ) {
        try {
                JRBeanCollectionDataSource mapCollectionDataSource = new JRBeanCollectionDataSource(objects)
             // String reportUri = reportPath+"/"+report
               JasperReport jasperReport = JasperCompileManager.compileReport(report)
               def jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, mapCollectionDataSource)
               SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
               configuration.setOnePagePerSheet(true);
               configuration.setIgnoreGraphics(false);

                File outputFile = new File("output.xlsx");
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()
                OutputStream fileOutputStream = new FileOutputStream(outputFile)
                Exporter exporter = new JRXlsxExporter();
                exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
                exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArrayOutputStream));
                exporter.setConfiguration(configuration);
                exporter.exportReport();
                byteArrayOutputStream.writeTo(fileOutputStream);

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

           /* JRXlsExporter xlsExporter = new JRXlsExporter();
            xlsExporter.setExporterInput(new SimpleExporterInput(xlsPrint));
            xlsExporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outXlsName));
            SimpleXlsReportConfiguration xlsReportConfiguration = new SimpleXlsReportConfiguration();
            xlsReportConfiguration.setOnePagePerSheet(false);
            xlsReportConfiguration.setRemoveEmptySpaceBetweenRows(true);
            xlsReportConfiguration.setDetectCellType(false);
            xlsReportConfiguration.setWhitePageBackground(false);
            xlsExporter.setConfiguration(xlsReportConfiguration);

            xlsExporter.exportReport();*/

            return byteArrayOutputStream.toByteArray()
        } catch (Exception e) {
            throw new RuntimeException("It's not possible to generate the pdf report.", e);
        }
    }
}
