package com.example.audioyvideo22_23;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button buttonPlay, buttonStop, buttonPause;
    MediaPlayer mediaPlayer;
    TextView textViewEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPause = findViewById(R.id.buttonPause);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonStop = findViewById(R.id.buttonStop);
        textViewEstado = findViewById(R.id.textViewEstado);

        mediaPlayer = MediaPlayer.create(this, R.raw.musica);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    textViewEstado.setText("Ya está sonando");
                } else {
                    mediaPlayer.start();
                    textViewEstado.setText("La canción está sonando");
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                    textViewEstado.setText("Canción en STOP");
                    try {
                        mediaPlayer.prepare(); //Para poder hacer un play primero debo llamar a prepare
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    textViewEstado.setText("Ninguna canción sonando");
                }
            }
        });
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    textViewEstado.setText("Pausada");
                }else{
                    textViewEstado.setText("Ninguna canción suena");
                }
            }
        });


    }
}