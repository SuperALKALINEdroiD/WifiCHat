package com.alkaluned.chatlan.Networking;

public class Listen {
    public static final String ipAddress = "255.255.255.255";

    public String toMessageAsString(byte[] bytes) {

        if (bytes == null) {
            return null;
        }

        StringBuilder message = new StringBuilder();
        int i = 0;

        while (bytes[i] != 0) {
            message.append((char) bytes[i]);
            ++i;
        }
        return message.toString();
    }
}

