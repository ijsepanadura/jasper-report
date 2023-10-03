package lk.ijse.dep11;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.util.HashMap;

public class MainViewController {
    public Button btnJasper;

    public void btnJasperOnAction(ActionEvent actionEvent)throws JRException {
        // jasperSoft Studio : this where we design our reports
        //jasper report library : this is what helps to integrate the jasper report with our app

        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("/Reports/hello-jasper.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,new HashMap<>(),new JREmptyDataSource(8));

//        JasperViewer.viewReport(jasperPrint,false);
//
//        JasperPrintManager.printReport(jasperPrint,true);

        JasperExportManager.exportReportToPdfFile(jasperPrint,"/home/ishan/Documents/abc.pdf");
    }
}
