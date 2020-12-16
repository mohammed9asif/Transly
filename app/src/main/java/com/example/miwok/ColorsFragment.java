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
 * Use the {@link ColorsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ColorsFragment extends Fragment {

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

    public ColorsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ColorsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ColorsFragment newInstance(String param1, String param2) {
        ColorsFragment fragment = new ColorsFragment();
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
        numbers.add(new Word("Laal","Red",R.drawable.color_red,R.raw.color_red));
        numbers.add(new Word("Hara","Green",R.drawable.color_green,R.raw.color_green));
        numbers.add(new Word("Nasiya","Brown",R.drawable.color_brown,R.raw.color_brown));
        numbers.add(new Word("Bhura","Grey",R.drawable.color_gray,R.raw.color_gray));
        numbers.add(new Word("Kaala","Black",R.drawable.color_black,R.raw.color_black));
        numbers.add(new Word("Safed","White",R.drawable.color_white,R.raw.color_white));
        numbers.add(new Word("Gold colour","Dust Yellow",R.drawable.color_dusty_yellow,R.raw.color_dusty_yellow));
        numbers.add(new Word("Pista colour","Mustard Yellow",R.drawable.color_mustard_yellow,R.raw.color_mustard_yellow));

        NumbersAdapter itemsAdapter = new NumbersAdapter(getActivity(),  numbers,R.color.category_colors);

        ListView listView = (ListView) rootView.findViewById(R.id.list);

        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseMediaPlayer();

                Word word =  numbers.get(position);

                int result = audioManager.requestAudioFocus(changeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);

                if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    mediaPlayer = MediaPlayer.create(getActivity(),word.getRawId());
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