/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UDP;

import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author TK
 */
public class bai3 {
    public static void main(String[] args){
        String host = "";
        int port = 807;
        String studentCode = "B23DCCN089";
        String qCode = "";
        
        try{
            DatagramSocket sk = new DatagramSocket();
            InetAddress ad = InetAddress.getByName(host);
            sk.setSoTimeout(5000);
            
            String message = ";" + studentCode + ";" + qCode;
            byte[] sData = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sPacket = new DatagramPacket(sData, sData.length, ad, port);
            sk.send(sPacket);
            
            byte[] rData = new byte[4096];
            DatagramPacket rPacket = new DatagramPacket(rData, rData.length);
            sk.receive(rPacket);
            
            String response = new String(rPacket.getData(), rPacket.getOffset(), rPacket.getLength(), StandardCharsets.UTF_8).trim();
            
            String[] parts = response.split(";", 2);
            String requestId = parts[0];
            
            String[] item = response.split(",");
            TreeMap<Integer, String> mp = new TreeMap<>();
            
            for (String i: item){
                String[] p = i.split(":");
                String value = p[0];
                int index = Integer.parseInt(p[1].trim());
                mp.put(index, value);
            }
            
            StringBuilder sb = new StringBuilder();
            for (String v: mp.values()){
                sb.append(v);
            }
            
            String result = requestId + ";" + sb.toString();
            byte[] aData = result.getBytes(StandardCharsets.UTF_8);
            DatagramPacket aPacket = new DatagramPacket(aData, aData.length, ad, port);
            sk.send(aPacket);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
