package com.example.casper;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class GameFragment extends Fragment {


    public GameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        GameView gameview = new GameView(this.getContext());
        gameview.setMinimumWidth(view.getWidth());
        gameview.setMinimumHeight(view.getHeight());
        ((FrameLayout) view).addView(gameview);
        return view;
    }

}
