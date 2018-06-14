package com.example.chaitanya.speechthetext;

import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import java.util.Locale;
public class MainActivity extends AppCompatActivity {
private TextToSpeech mTTS;
private EditText mEdittext;
private SeekBar pitch;
private SeekBar speed;
private Button btnspeak;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnspeak=findViewById(R.id.button);
        mTTS=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
           if (status==TextToSpeech.SUCCESS){
               int result=mTTS.setLanguage(Locale.UK);
               if (result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED){
                   Log.e("TTS","Language not Supported");
                   }
               else
               { btnspeak.setEnabled(true); }
           }else {
               Log.e("TTS","Initialisation Failed");
               } }});
        mEdittext=findViewById(R.id.edit_text);
        pitch=findViewById(R.id.pitchseek);
        speed=findViewById(R.id.speedseek);
        btnspeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak();
            }}); }
    private void speak(){
        String text=mEdittext.getText().toString();
        float mpitch=(float)pitch.getProgress()/50;
        if (mpitch<0.1)mpitch=0.1f;
        float mspeed=(float)speed.getProgress()/50;
        if (mspeed<0.1)mspeed=0.1f;
        mTTS.setPitch(mpitch);
        mTTS.setSpeechRate(mspeed);
        mTTS.speak(text,TextToSpeech.QUEUE_FLUSH,null);
        }
        @Override
    protected void onDestroy() {
        if (mTTS!=null){
            mTTS.stop();
            mTTS.shutdown(); }
        super.onDestroy(); }}
