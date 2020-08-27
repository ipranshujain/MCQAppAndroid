package com.example.mcqapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
public class McqActivity extends AppCompatActivity {
    Button option0,option1,option2,option3,next,previous,submit;
    TextView questiono,timer,question,tag,difficulty;
    public static int count=0;
    public static final int deadline=20;
    public CountDownTimer countDownTimer;
    ArrayList<String> questions;
    Dialog myDialog;
    int special;
    ArrayList<ArrayList<String>> options;
    ArrayList<String> answers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq);
        assert getSupportActionBar()!=null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        special=0;
        Intent intent=getIntent();
        if(intent!=null&&intent.getIntExtra("Check",0)==1){
            special=1;
        }
        myDialog = new Dialog(this);

        //Accessing and listening
        option0=findViewById(R.id.option0);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        next=findViewById(R.id.next);
        difficulty=findViewById(R.id.difficulty);
        tag=findViewById(R.id.tag);
        previous=findViewById(R.id.previous);
        submit=findViewById(R.id.submit);
        questiono=findViewById(R.id.questiono);
        question=findViewById(R.id.question);
        timer=findViewById(R.id.timer);
        if(special==1){
            timer.setVisibility(View.INVISIBLE);
            submit.setVisibility(View.INVISIBLE);
            colors(count);
        }
        else{
            timer.setVisibility(View.VISIBLE);
            submit.setVisibility(View.VISIBLE);
        }
        changeColor(QuestionBank.answer.get(count),count);
//        final QuestionBank questionBank= new QuestionBank(this);
        options= QuestionBank.getOptions();
        questions= QuestionBank.getQuestions();
        answers= QuestionBank.getAnswers();
        setValues(count);
        if(special!=1) {
            countDownTimer = new CountDownTimer(1000 * 60 * 30, 1000) {
                @Override
                public void onTick(long l) {
//                Toast.makeText(McqActivity.this, "L is: "+l, Toast.LENGTH_SHORT).show();
                    int minute = (int) (l / 60000);
                    int seconds = (int) (l / 1000 - minute * 60);
                    timer.setText(minute + ":" + seconds);
                }

                @Override
                public void onFinish() {
                    int count=QuestionBank.generateReport();

                    Intent intent = new Intent(McqActivity.this,ResultActivity.class);
                    intent.putExtra("COUNT",count);
                    McqActivity.this.startActivity(intent);
                }
            }.start();
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count<deadline-1){

                        count++;
                        setValues(count);
                        reset();
                        String x = QuestionBank.getUserAnswer(count);
                        changeColor(x, count);
                   if(special==1){

                       colors(count);
                   }
                }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(count>0){
                  count--;
                  setValues(count);
                  reset();
                  String x=QuestionBank.getUserAnswer(count);
                  changeColor(x,count);
                    if(special==1){

                        colors(count);
                    }
                }
            }
        });
        option0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                view.setBackgroundColor(Color.rgb(0,200,0));
                QuestionBank.answer(option0.getText().toString(),count);
            }
        });
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                view.setBackgroundColor(Color.rgb(0,200,0));
                QuestionBank.answer(option1.getText().toString(),count);
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                view.setBackgroundColor(Color.rgb(0,200,0));
                QuestionBank.answer(option2.getText().toString(),count);
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
                view.setBackgroundColor(Color.rgb(0,200,0));

                QuestionBank.answer(option3.getText().toString(),count);
            }
        });
        submit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int count=QuestionBank.generateReport();
                showpopup(count);
//                Toast.makeText(McqActivity.this, Integer.toString(count), Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setValues(int index){
        try {
            questiono.setText("Question " + (index + 1));
            question.setText(questions.get(index));
            option0.setText(options.get(index).get(0));
            option1.setText(options.get(index).get(1));
            option2.setText(options.get(index).get(2));
            option3.setText(options.get(index).get(3));
            difficulty.setText(QuestionBank.difficulty.get(index));
            tag.setText(QuestionBank.tag.get(index));
        }catch (Exception e){

        }
    }

    public void showpopup(final int l){
        TextView txtClose;
        Button btn;
        Button btnno;
        myDialog.setContentView(R.layout.custompop);
        txtClose=myDialog.findViewById(R.id.textView);
        btn = myDialog.findViewById(R.id.btn);
        btnno= myDialog.findViewById(R.id.btnno);
        btnno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();

            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(McqActivity.this,ResultActivity.class);
                intent.putExtra("COUNT",l);
                McqActivity.this.startActivity(intent);
            }
        });
        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
    }
    void reset(){
        option0.setBackgroundColor(Color.rgb(0,142,255));
        option1.setBackgroundColor(Color.rgb(0,142,255));
        option2.setBackgroundColor(Color.rgb(0,142,255));
        option3.setBackgroundColor(Color.rgb(0,142,255));

    }

    @Override
    protected void onPause() {
            Toast.makeText(this, "Quiz submitted!", Toast.LENGTH_LONG).show();
            countDownTimer.cancel();
            int count = QuestionBank.generateReport();
            Intent intent = new Intent(McqActivity.this, ResultActivity.class);
            intent.putExtra("COUNT", count);
            McqActivity.this.startActivity(intent);
        super.onPause();
    }

    @Override
    protected void onResume() {
//        Toast.makeText(this, "Welcome back pranshu", Toast.LENGTH_SHORT).show();
        super.onResume();
    }
    void changeColor(String x,int count){
        try {
            for (int i = 0; i < 4; i++) {
                if (x.equals(QuestionBank.options.get(count).get(i))) {
                    switch (i) {
                        case 0:
                            option0.setBackgroundColor(Color.rgb(0, 200, 0));
                            break;
                        case 1:
                            option1.setBackgroundColor(Color.rgb(0, 200, 0));
                            break;
                        case 2:
                            option2.setBackgroundColor(Color.rgb(0, 200, 0));
                            break;
                        case 3:
                            option3.setBackgroundColor(Color.rgb(0, 200, 0));
                            break;
                    }
                    break;
                }
            }
        }catch(Exception e){

        }

    }
    void colors(int count){
        String x= QuestionBank.answers.get(count);
        for(int i=0;i<4;i++){
            if(x.equals(QuestionBank.options.get(count).get(i))){
                switch(i){
                    case 0:option0.setBackgroundColor(Color.rgb(200,100,0));
                        break;
                    case 1:option1.setBackgroundColor(Color.rgb(200,100,0));
                        break;
                    case 2:option2.setBackgroundColor(Color.rgb(200,100,0));
                        break;
                    case 3:option3.setBackgroundColor(Color.rgb(200,100,0));
                        break;
                }
                break;
            }
        }
    }
//    void showAnswer()
//    {
////        Toast.makeText(this, "Hello I am returned", Toast.LENGTH_SHORT).show();
//
//    }

    @Override
    public void onBackPressed() {
//        if(special==1){
//            Intent intent= new Intent(McqActivity.this,Greeting.class);
//           intent.putExtra("COUNT",count);
//            McqActivity.this.startActivity(intent);
//        }
        finishAffinity();

        super.onBackPressed();
    }
}



