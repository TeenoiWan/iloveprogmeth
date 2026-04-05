package util.portScanner.services;
import logic.superClass.Tool;
import util.portScanner.dataType.ScanResult;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PortScannerService extends Tool {

    private String target;
    private int startPort;
    private int endPort;
    private ArrayList<ScanResult> scanResults;

    //constructor
    public PortScannerService(String target,int startPort,int endPort){
        this.target = target    ;
        this.setStartPort(startPort);
        this.setEndPort(endPort);
    }

    @Override
    public void execute() {
        ExecutorService executor = Executors.newFixedThreadPool(50);
        scanResults = new ArrayList<>();

        ArrayList<Future<ScanResult>> futures = new ArrayList<>();

        for (int port = startPort; port <= endPort; port++) {
            int currentPort = port;

            futures.add(executor.submit(() -> {
                try (Socket socket = new Socket()) {
                    socket.connect(new InetSocketAddress(target, currentPort), 200);
                    return new ScanResult(currentPort, "OPEN");
                } catch (java.net.SocketTimeoutException e) {
                    return new ScanResult(currentPort, "CLOSED");
                } catch (Exception e){
                    return new ScanResult(currentPort,"NO RESPONSE");
                }
            }));
        }


        for (Future<ScanResult> f : futures) {
            try {
                scanResults.add(f.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }

    public void setEndPort(int endPort) {
        this.endPort = endPort;
    }

    public void setStartPort(int startPort) {
        this.startPort = startPort;
    }

    public int getStartPort() {
        return startPort;
    }

    public int getEndPort() {
        return endPort;
    }

    public ArrayList<ScanResult> getScanResults(){
        return scanResults;
    }
}
