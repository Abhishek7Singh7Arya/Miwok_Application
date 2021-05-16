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

import java.util.ArrayList;


public class PhrasesFragment extends Fragment {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ){
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
         View rootView = inflater.inflate(R.layout.fragment_phrases, container, false);

        mAudioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<Word> phrase = new ArrayList<Word>();

        phrase.add(new Word("Where are you going?","minto wuksus",R.raw.phrase_where_are_you_going));

        phrase.add(new Word("What is your name?"," tinnә oyaase'nә",R.raw.phrase_what_is_your_name));
        phrase.add(new Word("My name is...","oyaaset...",R.raw.phrase_my_name_is));
        phrase.add(new Word("How are you feeling?","michәksәs?",R.raw.phrase_how_are_you_feeling));
        phrase.add(new Word("I’m feeling good.","kuchi achit",R.raw.phrase_im_feeling_good));
        phrase.add(new Word("Are you coming?","әәnәs'aa?",R.raw.phrase_are_you_coming));

        phrase.add(new Word("Yes, I’m coming.","hәә’ әәnәm",R.raw.phrase_yes_im_coming));
        phrase.add(new Word("I’m coming.","әәnәm",R.raw.phrase_im_coming));
        phrase.add(new Word("Let’s go."," yoowutis",R.raw.phrase_lets_go));
        phrase.add(new Word("Come here.","әnni'nem",R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(getActivity(),phrase,R.color.category_phrases);

        ListView listView =rootView.findViewById(R.id.listPhrases);
        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                // Create and setup the {@link MediaPlayer} for the audio resource associated
                // with the current word
                releaseMediaPlayer();

                Word words = phrase.get(position);

                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC,
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT); //because audio need for small interval of time

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mMediaPlayer = MediaPlayer.create(getActivity(), words.getAudioResourceId());

                    // Start the audio file
                    mMediaPlayer.start();
                    //  Toast.makeText(PhrasesActivity.this,"played",Toast.LENGTH_SHORT).show();
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }}
        });




    return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    private void releaseMediaPlayer(){   // If the media player is not null, then it may be currently playing a sound.
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
}