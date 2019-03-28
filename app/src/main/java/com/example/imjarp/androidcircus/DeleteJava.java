package com.example.imjarp.androidcircus;

import android.view.View;

import com.example.imjarp.androidcircus.kotlin.StringUtilsKt;
import com.example.imjarp.androidcircus.kotlin.WordCounter;

public class DeleteJava {

    public  void callFromJava(View v){

        //String algo = null;

        //int x = StringUtilsKt.countChar(algo,'A');

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }

        });

        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });

    }
}
