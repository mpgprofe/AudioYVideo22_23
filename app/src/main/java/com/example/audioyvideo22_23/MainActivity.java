package com.example.audioyvideo22_23;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button buttonPlay, buttonStop, buttonPause;
    MediaPlayer mediaPlayer;
    TextView textViewEstado;
    VideoView videoView;
    MediaController mediaController;
    BarraMusica barraMusica=null;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonPause = findViewById(R.id.buttonPause);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonStop = findViewById(R.id.buttonStop);
        textViewEstado = findViewById(R.id.textViewEstado);
        videoView = findViewById(R.id.videoView);
        seekBar = findViewById(R.id.seekBar);

        mediaPlayer = MediaPlayer.create(this, R.raw.musica); //Para el audio

        //Para el vídeo
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);
        String uri = "android.resource://" + getPackageName() + "/" + R.raw.magia;
        videoView.setVideoURI(Uri.parse(uri));
        videoView.setMediaController(mediaController);
        //videoView.start(); Si quiero que empiece automáticamente debo asegurarme de que está el vídeo preparado

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videoView.start();
                mediaController.show();
            }
        });


        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    textViewEstado.setText("Ya está sonando");
                } else {
                    if (barraMusica==null){
                        barraMusica = new BarraMusica(seekBar,mediaPlayer);
                        barraMusica.execute();
                    }
                    mediaPlayer.start();
                    textViewEstado.setText("La canción está sonando");
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    textViewEstado.setText("Canción en STOP");
                    try {
                        mediaPlayer.prepare(); //Para poder hacer un play primero debo llamar a prepare
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    textViewEstado.setText("Ninguna canción sonando");
                }
            }
        });
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    textViewEstado.setText("Pausada");
                } else {
                    textViewEstado.setText("Ninguna canción suena");
                }
            }
        });


    }

    class BarraMusica extends AsyncTask<String, String, String> {
        SeekBar miSeekBar;
        MediaPlayer miMediaPlayer;

        public BarraMusica(SeekBar miSeekBar, MediaPlayer miMediaPlayer) {
            this.miSeekBar = miSeekBar;
            this.miMediaPlayer = miMediaPlayer;
        }

        @Override
        protected void onPreExecute() {
            miSeekBar.setMax(miMediaPlayer.getDuration());

        }

        @Override
        protected void onProgressUpdate(String... values) {
            miSeekBar.setProgress(miMediaPlayer.getCurrentPosition());
        }

        @Override
        protected String doInBackground(String... strings) {
            while (true) {
                if (miMediaPlayer.isPlaying()) {
                    publishProgress();
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}