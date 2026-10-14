
package data_character;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class bai8 {
    public static void main(String[] args) {
        String host = "36.50.135.242";
        int port = 808;
        String studentCode = "B23DCCN089";
        String qCode = "CD34EF56";

        try {
            DatagramSocket sk = new DatagramSocket();
            InetAddress ad = InetAddress.getByName(host);
            sk.setSoTimeout(5000);

            String message = ";" + studentCode + ";" + qCode;
            byte[] sData = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sPacket = new DatagramPacket(
                sData, sData.length, ad, port
            );
            sk.send(sPacket);

            byte[] rData = new byte[4096];
            DatagramPacket rPacket = new DatagramPacket(rData, rData.length);
            sk.receive(rPacket);

            String response = new String(
                rPacket.getData(),
                rPacket.getOffset(),
                rPacket.getLength(),
                StandardCharsets.UTF_8
            ).trim();

            String[] parts = response.split(";", 2);
            String requestId = parts[0];
            String data = parts[1];

            String[] words = data.trim().split("\\s+");
            StringBuilder sb = new StringBuilder();

            for (String word : words) {
                if (word.matches("[aeiouAEIOU]+")) {
                    if (sb.length() > 0) {
                        sb.append(",");
                    }
                    sb.append(word);
                }
            }

            String result = requestId + ";" + sb.toString();

            byte[] aData = result.getBytes(StandardCharsets.UTF_8);
            DatagramPacket aPacket = new DatagramPacket(
                aData, aData.length, ad, port
            );
            sk.send(aPacket);

            sk.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
