package com.example.miwok_app;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class FamilyFragment extends Fragment {
    private MediaPlayer mMediaPlayer;
    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ){
                // the AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case mean
                // our app is allowed to continue playing sound but at a lower volume.we
                // both cases the same way because our app is playing short sound files
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }else
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN){
                //Resume playback
                //The AUDIOFOCUS_FAIN CASE means we have regained focus and can resume
                //playback
                mMediaPlayer.start();
            }else
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
                //stop playback
            }
        }
    }    ;

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View rootView = inflater.inflate(R.layout.fragment_family, container, false);

        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> word = new ArrayList<Word>();
        word.add(new Word("father", "әpә",R.drawable.family_father,R.raw.family_father));
        word.add(new Word("mother", "әṭa",R.drawable.family_mother,R.raw.family_mother));
        word.add(new Word("son", "angsi",R.drawable.family_son,R.raw.family_son));
        word.add(new Word("daughter", "tune",R.drawable.family_daughter,R.raw.family_daughter));
        word.add(new Word("older brother", "taachi",R.drawable.family_older_brother,R.raw.family_older_brother));
        word.add(new Word("younger brother", "chalitti",R.drawable.family_younger_brother,R.raw.family_younger_brother));
        word.add(new Word("older sister", "teṭe",R.drawable.family_older_sister,R.raw.family_older_sister));
        word.add(new Word("younger sister", "kolliti",R.drawable.family_younger_sister,R.raw.family_younger_sister));
        word.add(new Word("grandmother", "ama",R.drawable.family_grandmother,R.raw.family_grandmother));
        word.add(new Word("grandfather", "paapa",R.drawable.family_grandfather,R.raw.family_grandfather));

        WordAdapter adapter = new WordAdapter(getActivity(),word,R.color.category_family);
        ListView listView = rootView.findViewById(R.id.listFamily);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Word words = word.get(position);
                // release all audio resources because new audio resource is going to allocate
                releaseMediaPlayer();
                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current word
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT); //because audio need for small interval of time

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    mMediaPlayer = MediaPlayer.create(getActivity(), words.getAudioResourceId());

                    // Start the audio file
                    mMediaPlayer.start();
                    Toast.makeText(getActivity(),"played",Toast.LENGTH_SHORT).show();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }}
        });






         return rootView;
    }
    private void releaseMediaPlayer(){
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. this also
            // unregisters the AudioFocuschangelistener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}