package com.sbro.clientsocketapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private EditText edt;
    private Button button;
    private String INET_ADDR = "224.0.0.3";
    private int PORT = 8888;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt = (EditText) findViewById(R.id.main_txt);
        button = (Button) findViewById(R.id.main_send);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InetAddress group = InetAddress.getByName(INET_ADDR);
                    MulticastSocket s = new MulticastSocket(PORT);
                    s.joinGroup(group);
                    byte[] bytes = new byte[0];
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                        bytes = edt.getText().toString().getBytes(StandardCharsets.UTF_8);
                    }
                    DatagramPacket hi = new DatagramPacket(bytes, bytes.length,
                            group, PORT);
                    s.send(hi);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
