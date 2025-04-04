package com.example.bt_bluetooth;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

public class BluetoothControl extends AppCompatActivity {
    ImageButton btnTb1,btnTb2,btnDis;
    TextView txt1,txtMAC;
    BluetoothAdapter myBluetooth = null;
    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    Set<BluetoothDevice> pairedDevices1;
    String address = null;
    private ProgressDialog progress;
    int flaglamp1;
    int flaglamp2;
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent newint = getIntent();
        address = newint.getStringExtra(MainActivity.EXTRA_ADDRESS);
        System.out.println("Address bluecontrol:" + address);
        setContentView(R.layout.activity_control);

        pairedDevicesList1();

        btnTb1 = findViewById(R.id.btnTb1);
        btnTb2 = findViewById(R.id.btnTb2);
        txt1 = findViewById(R.id.textV1);
        txtMAC = findViewById(R.id.textViewMAC);
        btnDis = findViewById(R.id.btnDisc);

        txtMAC.setText(address);

        new ConnectBT().execute(); //Call the class to connect

        btnTb1.setOnClickListener(v -> thietTbi1());
        btnTb2.setOnClickListener(v -> thietTbi7());
        btnDis.setOnClickListener(v -> Disconnect());
    }

    private void Disconnect() {
        if (btSocket != null) {
            try {
                btSocket.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }

    private void thietTbi1() {
        if (btSocket != null) {
            try{
                if (this.flaglamp1 == 0){
                    this.flaglamp1 = 1;
                    this.btnTb1.setBackgroundResource(R.drawable.tb1on);
                    btSocket.getOutputStream().write("1".toString().getBytes());
                    txt1.setText("thiết bị 1 đang kết nối");
                    return;
                }
                else{
                    if(this.flaglamp1 != 1) return;
                    {
                        this.flaglamp1 = 0;
                        this.btnTb1.setBackgroundResource(R.drawable.tb1off);
                        btSocket.getOutputStream().write("0".toString().getBytes());
                        txt1.setText("thiết bị 1 đã tắt");
                        return;
                    }
                }
            }catch (IOException e){
                Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void thietTbi7() {
        if (btSocket != null) {
            try{
                if (this.flaglamp2 == 0){
                    this.flaglamp2 = 1;
                    this.btnTb2.setBackgroundResource(R.drawable.tb1on);
                    btSocket.getOutputStream().write("1".toString().getBytes());
                    txt1.setText("thiết bị 7 đang kết nối");
                    return;
                }
                else{
                    if(this.flaglamp2 != 1) return;
                    {
                        this.flaglamp2 = 0;
                        this.btnTb2.setBackgroundResource(R.drawable.tb1off);
                        btSocket.getOutputStream().write("0".toString().getBytes());
                        txt1.setText("thiết bị 7 đã tắt");
                        return;
                    }
                }
            }catch (IOException e){
                Toast.makeText(getApplicationContext(), "Lỗi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class ConnectBT extends AsyncTask<Void, Void, Void> // UI thread
    {

        private boolean ConnectSuccess = true;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(BluetoothControl.this, "Đang kết nối", "Vui lòng chờ...");
        }

        @Override
        protected Void doInBackground(Void... devices) {
            if(btSocket == null || !isBtConnected){
                myBluetooth = BluetoothAdapter.getDefaultAdapter();
                if (myBluetooth == null) {
                    Toast.makeText(BluetoothControl.this, "Thiết bị không hỗ trợ Bluetooth", Toast.LENGTH_LONG).show();
                    finish();
                }
                BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);
                if (ActivityCompat.checkSelfPermission(BluetoothControl.this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                    try {
                        btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    try {
                        btSocket.connect();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result){
            super.onPostExecute(result);
            if (!ConnectSuccess) {
                Toast.makeText(getApplicationContext(), "Kết nối thất bại", Toast.LENGTH_SHORT).show();
                finish();
            }
            else {
                Toast.makeText(getApplicationContext(), "Kết nối thành công", Toast.LENGTH_SHORT).show();
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    private void pairedDevicesList1(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.BLUETOOTH_CONNECT}, 1);
                return;
            }

            pairedDevices1 = myBluetooth.getBondedDevices();

            if (pairedDevices1.size() > 0){
                for (BluetoothDevice bt: pairedDevices1){
                    txtMAC.setText(bt.getName() + " _ " + bt.getAddress());
                }
            }
            else{
                Toast.makeText(getApplicationContext(), "Không tìm thấy thiết bị kết nối!", Toast.LENGTH_SHORT).show();
            }
        }
    }

}