package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

    MediaPlayer mediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                        mediaPlayer.pause();
                        mediaPlayer.seekTo(0);
                    }
                    else if (focusChange==AudioManager.AUDIOFOCUS_GAIN){
                        mediaPlayer.start();
                    }

                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS)
                    {
                        releaseMediaPlayer();
                    }
                }
            };
    MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);


        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words=new ArrayList <Word>();
        words.add(new Word(getString(R.string.phrases_where_are_you_going), "minto wuksus"
                ,R.raw.phrase_where_are_you_going));
        words.add(new Word(getString(R.string.phrases_what_is_your_name), "tinnә oyaase'nә"
                ,R.raw.phrase_what_is_your_name));
        words.add(new Word(getString(R.string.phrases_my_name_is), "oyaaset...",R.raw.phrase_my_name_is));
        words.add(new Word(getString(R.string.phrases_how_are_you_feeling), "michәksәs?"
                ,R.raw.phrase_how_are_you_feeling));
        words.add(new Word(getString(R.string.phrases_im_feeling_good), "kuchi achit"
                ,R.raw.phrase_im_feeling_good));
        words.add(new Word(getString(R.string.phrases_are_you_coming), "әәnәs'aa?",R.raw.phrase_are_you_coming));
        words.add(new Word(getString(R.string.phrases_yes_im_coming), "hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        words.add(new Word(getString(R.string.phrases_im_coming), "әәnәm",R.raw.phrase_im_coming));
        words.add(new Word(getString(R.string.phrases_lets_go), "yoowutis",R.raw.phrase_lets_go));
        words.add(new Word(getString(R.string.phrases_come_here), "әnni'nem",R.raw.phrase_come_here));




        WordAdapter itemAdapter=new WordAdapter(this,words,R.color.phrases);

        ListView listview=(ListView)findViewById(R.id.List);
        listview.setAdapter(itemAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {

                    mediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudio());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer!=null) {
            mediaPlayer.stop();
            releaseMediaPlayer();
        }
        else
            releaseMediaPlayer();
    }


    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}


