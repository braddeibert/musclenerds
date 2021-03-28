package com.example.musclenerds.ui.home;

import android.os.Bundle;
import android.widget.TextView;

import com.example.musclenerds.R;

import java.util.Random;

public class MotivationalQuoteDisplay {
    Random random = new Random();
    TextView textQuot;


    public void displayQuote(){
        //Picks random quote between quote 1-20
        int randNum = random.nextInt((20+1)-1) + 1;
        String randQuote = "";


        switch (randNum){
            case 1 :
                randQuote = getString(R.string.quote1);
                break;
            case 2 :
                randQuote = getString(R.string.quote2);
                break;
            case 3 :
                randQuote = getString(R.string.quote3);
                break;
            case 4 :
                randQuote = getString(R.string.quote4);
                break;
            case 5 :
                randQuote = getString(R.string.quote5);
                break;
            case 6 :
                randQuote = getString(R.string.quote6);
                break;
            case 7 :
                randQuote = getString(R.string.quote7);
                break;
            case 8 :
                randQuote = getString(R.string.quote8);
                break;
            case 9 :
                randQuote = getString(R.string.quote9);
                break;
            case 10 :
                randQuote = getString(R.string.quote10);
                break;
            case 11 :
                randQuote = getString(R.string.quote11);
                break;
            case 12 :
                randQuote = getString(R.string.quote12);
                break;
            case 13 :
                randQuote = getString(R.string.quote13);
                break;
            case 14 :
                randQuote = getString(R.string.quote14);
                break;
            case 15 :
                randQuote = getString(R.string.quote15);
                break;
            case 16 :
                randQuote = getString(R.string.quote16);
                break;
            case 17 :
                randQuote = getString(R.string.quote17);
                break;
            case 18 :
                randQuote = getString(R.string.quote18);
                break;
            case 19 :
                randQuote = getString(R.string.quote19);
                break;
            case 20 :
                randQuote = getString(R.string.quote20);
                break;
        }

        textQuot.setText(randQuote);
    }

}
