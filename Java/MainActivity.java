package com.cs2415.pingipaddress;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private EditText ipAddressEditText;
    private TextView pingResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ipAddressEditText = findViewById(R.id.ipAddressEditText);
        pingResultTextView = findViewById(R.id.pingResultTextView);

        Button pingButton = findViewById(R.id.pingButton);
        pingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ipAddress = ipAddressEditText.getText().toString();
                if (!ipAddress.isEmpty()) {
                    String pingResult = executePingCommand(ipAddress);
                    pingResultTextView.setText(pingResult);
                }
            }
        });
    }

    private String executePingCommand(String ipAddress) {
        try {
            Process process = Runtime.getRuntime().exec("ping -c 4 " + ipAddress);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            return output.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
