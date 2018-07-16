package com.example.a16022774.demofilereadwriting;

import android.Manifest;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {

    Button btnWrite, btnRead;
    String folderLocation;
    File folder;
    TextView tvRead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnWrite = (Button) findViewById(R.id.btnWrite);
        btnRead = (Button) findViewById(R.id.btnRead);
        tvRead = (TextView) findViewById(R.id.tvRead);

        folderLocation = Environment.getExternalStorageDirectory().getAbsolutePath() + "/myFolder";
        folder = new File(folderLocation);

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermissionWrite() == true) {

                    if (folder.exists() == false) {
                        Boolean result = folder.mkdir();
                        if (result == true) {
                            Log.i("File Read/Write", "Folder created");
                        }
                    }else{
                        try{
                            File targetFile = new File(folderLocation, "data.txt");
                            FileWriter writer = new FileWriter(targetFile, true);
                            writer.write("Hello world" + "\n");
                            writer.flush();
                            writer.close();

                        }catch (Exception e){
                            Toast.makeText(MainActivity.this, "Failed to write!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                }else{
                    Toast.makeText(MainActivity.this, "No permission", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPermissionRead() == true){
                    File targetFile = new File(folderLocation, "data.txt");
                    if (targetFile.exists() == true){
                        String data = "";
                        try{

                            FileReader reader = new FileReader(targetFile);
                            BufferedReader br = new BufferedReader(reader);

                            String line = br.readLine();
                            while(line != null){
                                data += line + "\n";
                                line = br.readLine();
                            }

                            tvRead.setText(data);

                            br.close();
                            reader.close();

                        }catch (Exception e){
                            Toast.makeText(MainActivity.this, "Failed to read!", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        Log.i("Content", data);
                    }

                }else{
                    Toast.makeText(MainActivity.this, "No permission", Toast.LENGTH_SHORT).show();
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }
            }
        });

    }

    private boolean checkPermissionWrite() {
        int permissionCheck_Write = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck_Write == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private boolean checkPermissionRead() {
        int permissionCheck_Read = ContextCompat.checkSelfPermission(
                MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionCheck_Read == PermissionChecker.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }
}
