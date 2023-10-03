package lk.ijse.dep11;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lk.ijse.dep11.to.Item;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class MainViewController {
    public Spinner<Integer> spn;
    public Button btnView;
    public Button btnExport;
    public AnchorPane root;

    private ArrayList<Item> itemList = new ArrayList<>();

    public void initialize(){
        spn.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 1));

        itemList.add(new Item("I-1234","Logitech mouse",5,new BigDecimal("850")));
        itemList.add(new Item("I-2234","Logitech KeyBoard",2,new BigDecimal("250")));
        itemList.add(new Item("I-3234","Casio Watch",7,new BigDecimal("2350")));
        itemList.add(new Item("I-4234","HD WEb camere",3,new BigDecimal("4350")));
    }

    public void btnViewOnAction(ActionEvent actionEvent)throws JRException {
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("/reports/sampleProject.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        JasperPrint jasperPrint = getJasperPrint();

//        HashMap<String, Object> parameter = new HashMap<>();
//        parameter.put("invoice ID","Invoice#5");
//        parameter.put("Name","kasun");
//        parameter.put("Date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MMMM-dd")));
//
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter, new JREmptyDataSource(spn.getValue()));
//
        JasperViewer.viewReport(jasperPrint,false);

    }
    private JasperPrint getJasperPrint()throws JRException{
        JasperDesign jasperDesign = JRXmlLoader.load(getClass().getResourceAsStream("/reports/sampleProject.jrxml"));
        JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put("invoice ID","Invoice#5");
        parameter.put("Name","kasun");
        parameter.put("Date", LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MMMM-dd")));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,parameter,new JRBeanCollectionDataSource(itemList));
        return jasperPrint;
    }

    public void btnExportOnAction(ActionEvent actionEvent)throws JRException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("select file path");
        FileChooser.ExtensionFilter extensionFilterPDF = new FileChooser.ExtensionFilter("PDF file (*.pdf)","*.pdf");
        FileChooser.ExtensionFilter extensionFilterHTML = new FileChooser.ExtensionFilter("HTML File (*.html)","*.html");
        fileChooser.getExtensionFilters().addAll(extensionFilterPDF,extensionFilterHTML);
        File openFile = fileChooser.showSaveDialog(root.getScene().getWindow());
        if(openFile == null)return;

        JasperPrint jasperPrint = getJasperPrint();

        String filePath= openFile.getAbsolutePath();
        if(fileChooser.getSelectedExtensionFilter()== extensionFilterPDF){
            if(!filePath.endsWith(".pdf"))filePath = openFile.getAbsolutePath()+".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint,filePath);

        }else if(fileChooser.getSelectedExtensionFilter()== extensionFilterHTML){
            if(!filePath.endsWith(".html"))filePath = filePath+".html";
            JasperExportManager.exportReportToHtmlFile(jasperPrint,filePath);
        }
        new Alert(Alert.AlertType.INFORMATION,"Exported").show();
    }
}
