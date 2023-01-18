package com.alkaluned.chatlan.Networking;

import android.content.Context;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendMessage {

    private final String ipAddress = "255.255.255.255";

    public boolean sendMessage(String message, Context context) {

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress ip = InetAddress.getByName(ipAddress);

            byte[] messageBytes = message.getBytes();
            DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, ip, 1234);
            socket.send(packet);
            return true;
        } catch (IOException ioException) {
            // to-do
            return false;
        }

    }
}
