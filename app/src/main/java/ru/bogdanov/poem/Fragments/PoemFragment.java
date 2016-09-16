package ru.bogdanov.poem.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import ru.bogdanov.poem.R;
import ru.bogdanov.poem.Storage;

/**
 * A simple {@link Fragment} subclass.
 */
public class PoemFragment extends Fragment implements View.OnClickListener{
Button buttonEasier, buttonRefresh, buttonHarder;
    TextView textViewTop,textViewPoemText;
    SeekBar seekBar;

    public PoemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_poem, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        seekBar=(SeekBar) getActivity().findViewById(R.id.seekBarDifficult);
        buttonEasier=(Button) getActivity().findViewById(R.id.buttonPoemEasier);
        buttonRefresh=(Button) getActivity().findViewById(R.id.buttonPoemRefresh);
        buttonHarder=(Button) getActivity().findViewById(R.id.buttonPoemHarder);
        buttonEasier.setOnClickListener(this);
        buttonRefresh.setOnClickListener(this);
        buttonHarder.setOnClickListener(this);
        textViewTop=(TextView) getActivity().findViewById(R.id.textViewTopPoemText);
        textViewPoemText=(TextView) getActivity().findViewById(R.id.textViewPoemText);
        seekBar.setProgress(20);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                morphPoem(Storage.getPoemText());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        morphPoem(Storage.getPoemText());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.buttonPoemRefresh :{
                morphPoem(Storage.getPoemText());
                break;
            }
            case R.id.buttonPoemEasier:{
                changeSeek(-10);
                break;
            }
            case R.id.buttonPoemHarder:{
                changeSeek(10);
                break;
            }
        }
    }

    private void changeSeek(int i) {
        int pos=seekBar.getProgress();
        seekBar.setProgress(pos+i);
    }

    private void morphPoem(String text) {
        LinearLayout linearLayoutAttention = (LinearLayout) getActivity().findViewById(R.id.linearLayoutAttentionPoem);
        if (text.equals(""))
            linearLayoutAttention.setVisibility(View.VISIBLE);
        else {
            linearLayoutAttention.setVisibility(View.INVISIBLE);
            String[] words = text.split(" ");
            int leng = words.length;
            int morphDiff = seekBar.getProgress();
            int hideWords = leng * morphDiff / 100;
            Random random = new Random();
            ArrayList<Integer> morphList = new ArrayList<>();
            for (int i = 0; i < hideWords; i++) {
                do {
                    int current = random.nextInt(leng);
                    if (!morphList.contains(current)) {
                        if (!words[current].contains("\n")) {
                            morphList.add(current);
                            words[current] = words[current].replaceAll("(?s).", "*");
                            break;
                        }
                    }
                } while (true);
            }
            String morphString = Arrays.toString(words);
            morphString = morphString.substring(1, morphString.length() - 1).replaceAll(",", "");
            textViewPoemText.setText(morphString);
        }
    }
}
