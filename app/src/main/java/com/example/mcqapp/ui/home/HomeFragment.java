package com.example.mcqapp.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.example.mcqapp.McqActivity;
import com.example.mcqapp.QuestionBank;
import com.example.mcqapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    public Context context;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final ProgressBar progressBar= root.findViewById(R.id.progressbar);
         context=container.getContext();
        root.findViewById(R.id.startquiz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                root.findViewById(R.id.startquiz).setVisibility(View.INVISIBLE);
                AndroidNetworking.initialize(context);
                AndroidNetworking.get("https://thequizi.herokuapp.com/listquiz")
                        .setPriority(Priority.LOW)
                        .build()
                        .getAsJSONArray(new JSONArrayRequestListener() {
                            @Override
                            public void onResponse(JSONArray response) {

                                try {
                                    for(int i=0;i<response.length();i++){
                                        JSONObject object= response.getJSONObject(i);
                                        QuestionBank.questions.add(object.getString("question"));
                                        QuestionBank.answers.add(object.getString("ca"));
                                        QuestionBank.difficulty.add(object.getString("difficulty"));
                                        QuestionBank.tag.add(object.getString("tag"));
                                        ArrayList<String> option= new ArrayList<String>();

                                        for(int j=0;j<4;j++){
                                            option.add(object.getJSONArray("options").getString(j));
                                        }
                                        QuestionBank.options.add(option);
                                    }
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Intent intent = new Intent(context, McqActivity.class);
                                    context.startActivity(intent);
                                }
                                catch(Exception e){
                                }
                            }
                            @Override
                            public void onError(ANError error) {

                            }
                        });

            }
        });
        return root;
    }

}
