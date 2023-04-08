package com.alkaluned.chatlan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alkaluned.chatlan.Networking.Listen;
import com.alkaluned.chatlan.Networking.SendMessage;
import com.alkaluned.chatlan.databinding.ActivityMainBinding;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SendMessage sendMessage = new SendMessage();
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread messageListener = new Thread(new Runnable() {

            @Override
            public void run() {
                Listen listenHelper = new Listen();

                List<MessageModel> messageModelList = new ArrayList<>();
                RecyclerView messageView = findViewById(R.id.Messages);
                MessagesAdapter messagesAdapter = new MessagesAdapter(messageModelList);

                messageView.setAdapter(messagesAdapter);
                messageView.setLayoutManager(new LinearLayoutManager(getApplication()));

                try {
                    DatagramSocket socket = new DatagramSocket(1234);

                    DatagramPacket packet = null;
                    while (true) {
                        byte[] messageBytes = new byte[65535];
                        packet = new DatagramPacket(messageBytes, messageBytes.length);
                        socket.receive(packet);
                        String currentDateTime = Calendar.getInstance().getTime().toString();
                        String time = currentDateTime.split(" ")[3];

                        Log.w("Listen", time);

                        messageModelList.add(0, new MessageModel(listenHelper.toMessageAsString(messageBytes), time));
                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                messagesAdapter.notifyItemInserted(0);
                            }
                        });
                    }
                } catch (IOException ioException) {
                    //to-do
                }
            }
        });

        messageListener.start();

        binding.SendMessageButton.setOnClickListener(view -> {

            String message = binding.MessageContent.getText().toString();

            binding.SendMessageButton.setText(R.string.Sending);
            binding.SendMessageButton.setClickable(false);

            // send message
            Thread sendMessageThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.w("send", message);
                    sendMessage.sendMessage(message, getApplication());
                }
            });

            sendMessageThread.start();

            binding.SendMessageButton.setText(R.string.Sent);
            binding.MessageContent.setText("");

            binding.SendMessageButton.setClickable(true);
            binding.SendMessageButton.setText(R.string.Send);
        });

    }
}