package util.hashGenerator.services;

public class BytesToHex {
    public static String bytesToHex(byte[] bytes){
        StringBuilder out = new StringBuilder();
        for(byte b : bytes){
            out.append(String.format("%02x",b));
        }
        return out.toString();
    }

}
