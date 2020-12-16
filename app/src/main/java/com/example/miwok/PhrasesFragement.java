package com.example.miwok;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PhrasesFragement#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PhrasesFragement extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    private MediaPlayer mediaPlayer;

    private MediaPlayer.OnCompletionListener completionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };


    AudioManager audioManager ;

    private AudioManager.OnAudioFocusChangeListener changeListener = new AudioManager.OnAudioFocusChangeListener(){
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Permanent loss of audio focus
                releaseMediaPlayer();
            }
            else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // Pause playback

                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Your app has been granted audio focus again
                // Raise volume to normal, restart playback if necessary

                mediaPlayer.start();

            }

        }
    };

    public PhrasesFragement() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PhrasesFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static PhrasesFragement newInstance(String param1, String param2) {
        PhrasesFragement fragment = new PhrasesFragement();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        audioManager =(AudioManager) getActivity().getSystemService(getActivity().AUDIO_SERVICE);


        ArrayList<Word> numbers = new ArrayList<>();
        numbers.add(new Word("Tum Kidhar jaa rahe ho?","Where are you going?",R.raw.phrase_where_are_you_going));
        numbers.add(new Word("Tumhara Naam Kya hai?","What is your name?",R.raw.phrase_what_is_your_name));
        numbers.add(new Word("Mera Naam hai...","My name is...",R.raw.phrase_my_name_is));
        numbers.add(new Word("Tumhe kaisa mehsoos ho raha hai?","How are you feeling?",R.raw.phrase_how_are_you_feeling));
        numbers.add(new Word("Mujhe acha lagra hai.","I’m feeling good.",R.raw.phrase_im_feeling_good));
        numbers.add(new Word("Kya tum Aa rahe ho?","Are you coming?",R.raw.phrase_are_you_coming));
        numbers.add(new Word("Haa mai aa raha hu.","Yes, I’m coming.",R.raw.phrase_yes_im_coming));
        numbers.add(new Word("Mai aa raha hu.","I’m coming.",R.raw.phrase_im_coming));
        numbers.add(new Word("Chalo","Let’s go.",R.raw.phrase_lets_go));
        numbers.add(new Word("Idhar aao","Come here.",R.raw.phrase_come_here));

        NumbersAdapter itemsAdapter = new NumbersAdapter(getActivity(),  numbers,R.color.category_phrases);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseMediaPlayer();
                Word word =  numbers.get(position);

                int result = audioManager.requestAudioFocus(changeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mediaPlayer = MediaPlayer.create(getActivity(), word.getRawId());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(completionListener);
                }
            }
        });
        return rootView;
    }

    public void releaseMediaPlayer(){
        if(mediaPlayer!=null){
            mediaPlayer.release();

            mediaPlayer = null;


            audioManager.abandonAudioFocus(changeListener);
        }
    }


    public void onPause(){

        //calling the super method of activity life cycle method to release mediaplayer
        //when paused
        super.onPause();

        releaseMediaPlayer();
    }
}