package util.portScanner.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import logic.interfaces.Resettable;
import logic.superClass.BaseController;
import util.portScanner.services.PortScannerService;
import util.portScanner.dataType.ScanResult;

import java.util.ArrayList;


public class PortScannerController extends BaseController implements Resettable {


    //  Input fields
    @FXML private TextField scannerIP;
    @FXML private TextField scannerStartPort;
    @FXML private TextField scannerEndPort;

    //  Table
    @FXML private TableView<ScanResult> resultTable;
    @FXML private TableColumn<ScanResult, Integer> portColumn;
    @FXML private TableColumn<ScanResult, String> statusColumn;
    @FXML private Label statusLabel;

    //Filter
    private ArrayList <ScanResult> allResults;
    @FXML private Label allLabel;
    @FXML private Label openLabel;
    @FXML private Label closeLabel;
    @FXML private Label filteredLabel;



    /// ///////////////////////////////////////////////////////////////////////////////////////////////////////
    @FXML private void handleScan() {
        try {
            String ip = scannerIP.getText().trim();

            if (ip.isEmpty() || !(ip.matches("^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$")|| ip.equals("localhost")) ) {
                //

                showError("Invalid IP Address", "Please enter a valid IP or hostname.");
                return;
            }

            int start = Integer.parseInt(scannerStartPort.getText());
            int end = Integer.parseInt(scannerEndPort.getText());


            if (start < 0 || end > 65535 || start > end) {
                showError("Invalid Port Range", "Ports must be between 0-65535");
                return;
            }


            //Background thread
            javafx.concurrent.Task<java.util.List<ScanResult>> scanTask = new javafx.concurrent.Task<>() {
                @Override
                protected java.util.List<ScanResult> call() throws Exception {

                    PortScannerService scanner = new PortScannerService(ip, start, end);
                    scanner.execute();
                    allResults = scanner.getScanResults();
                    return scanner.getScanResults();
                }
            };


            scanTask.setOnSucceeded(e -> {
                resultTable.getItems().setAll(scanTask.getValue());
                statusLabel.setText("Scan Complete!");
                statusLabel.setTextFill(javafx.scene.paint.Color.GREEN);

                // Filter Label
                allLabel.setText("Port Scanned: "+
                        allResults.size());

                openLabel.setText("Open: " +
                        allResults.stream().filter(r -> r.getStatus().equals("OPEN")).count());

                closeLabel.setText("Closed: " +
                        allResults.stream().filter(r -> r.getStatus().equals("CLOSED")).count());

                filteredLabel.setText("Filtered: " +
                        allResults.stream().filter(r -> r.getStatus().equals("FILTERED")).count());
            });


            scanTask.setOnFailed(e -> {
                statusLabel.setText("Scan Failed: " + scanTask.getException().getMessage());
                statusLabel.setTextFill(javafx.scene.paint.Color.RED);
            });


            Thread thread = new Thread(scanTask);
            thread.setDaemon(true);
            thread.start();

            statusLabel.setText("Scanning...");
            statusLabel.setTextFill(javafx.scene.paint.Color.BLUE);

        } catch (NumberFormatException e) {
            showError("Input Error", "Port fields must contain only numbers.");
        }
    }

    /// /////////////////////////////////////////////////////////////////////////////////////////////////


    //Filter
    @FXML private void showAll() {
        resultTable.getItems().setAll(allResults);
    }

    @FXML private void showOpen() {
        resultTable.getItems().setAll(
                allResults.stream()
                        .filter(r -> r.getStatus().equals("OPEN"))
                        .toList()
        );
    }

    @FXML
    private void showClose() {
        resultTable.getItems().setAll(
                allResults.stream()
                        .filter(r -> r.getStatus().equals("CLOSED"))
                        .toList()
        );
    }

    @FXML
    private void showFilter() {
        resultTable.getItems().setAll(
                allResults.stream()
                        .filter(r -> r.getStatus().equals("FILTERED"))
                        .toList()
        );
    }



    @FXML
    public void initialize() {
        portColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getPort()).asObject()
        );

        statusColumn.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus())
        );


    }

    @Override
    public void reset(){
        scannerIP.clear();
        scannerStartPort.clear();
        scannerEndPort.clear();

        resultTable.getItems().clear();

        statusLabel.setText("");

        allLabel.setText("");
        openLabel.setText("");
        closeLabel.setText("");
        filteredLabel.setText("");

        allResults = new ArrayList<>();
    }

    @FXML
    public void handleReset(){
        reset();
    }
}

