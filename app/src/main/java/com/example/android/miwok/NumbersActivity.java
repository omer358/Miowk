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

public class NumbersActivity extends AppCompatActivity {
    private AudioManager mAudioManager;

    private MediaPlayer mediaPlayer;

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

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
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


        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(this.getString(R.string.numbers_one), "lutti", R.drawable.number_one,
                R.raw.number_one));
        words.add(new Word(this.getString(R.string.numbers_two), "otiiko", R.drawable.number_two,
                R.raw.number_two));
        words.add(new Word(this.getString(R.string.numbers_three), "tolookosu", R.drawable.number_three,
                R.raw.number_three));
        words.add(new Word(this.getString(R.string.numbers_four), "oyyisa", R.drawable.number_four,
                R.raw.number_four));
        words.add(new Word(this.getString(R.string.numbers_five), "massokka", R.drawable.number_five,
                R.raw.number_five));
        words.add(new Word(this.getString(R.string.numbers_six), "temmokka", R.drawable.number_six,
                R.raw.number_six));
        words.add(new Word(this.getString(R.string.numbers_seven), "kenekaku", R.drawable.number_seven,
                R.raw.number_seven));
        words.add(new Word(this.getString(R.string.numbers_eight), "kawinta", R.drawable.number_eight,
                R.raw.number_eight));
        words.add(new Word(this.getString(R.string.numbers_nine), "wo'e", R.drawable.number_nine,
                R.raw.number_nine));
        words.add(new Word(this.getString(R.string.numbers_ten), "na'aacha", R.drawable.number_ten,
                R.raw.number_ten));


        WordAdapter itemAdapter = new WordAdapter(this, words, R.color.numbers);

        ListView listview = (ListView)findViewById(R.id.List);
        listview.setAdapter(itemAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word word = words.get(position);

                releaseMediaPlayer();
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED)  {


                    mediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getAudio());
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
