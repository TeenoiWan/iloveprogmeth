package util.portScanner.dataType;

public class ScanResult {
    private int port;
    private String status;

    public ScanResult(int port, String status) {
        this.port = port;
        this.status = status;
    }

    public ScanResult(){
        this.port = 0;
        this.status = "";
    }

    public int getPort() {
        return port;
    }

    public String getStatus() {
        return status;
    }


}
