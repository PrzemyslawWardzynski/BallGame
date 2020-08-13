package com.example.ballgame;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class StatListFragment extends ListFragment {

    ListView lv;
    ArrayAdapter<Float> adapter;
    Float [] data;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.score_fragment,
                container, false);

        data = new Float[20];
        Arrays.fill(data,99F);
        readFile();
        Arrays.sort(data);

        lv = view.findViewById(android.R.id.list);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
        lv.setAdapter(new ArrayAdapter<Float>(getActivity(), android.R.layout.simple_list_item_1, data) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);

                if(data[position] == 99F){
                    textView.setText(position+1 + ".      <pusty>");
                }
                else{
                    textView.setText(position+1 + ".      " + data[position] + " s");
                }

                textView.setTextColor(Color.WHITE);
                textView.setBackgroundColor(getResources().getColor(R.color.muj));

                return textView;
            }});
        return view;

    }

    public void readFile() {
        try {
            FileInputStream fileInputStream = getContext().openFileInput("Times.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String[] dataS = bufferedReader.readLine().split(" ");
            int i = 0;
            while(i < dataS.length && i < data.length){
                data[i] = Float.parseFloat(dataS[dataS.length-i-1]);
                i++;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
