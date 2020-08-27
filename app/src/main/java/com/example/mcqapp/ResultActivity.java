package com.example.mcqapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;

public class ResultActivity extends AppCompatActivity {
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Intent intent = getIntent();
        textView=findViewById(R.id.result);
        if(intent!=null){

            int count=intent.getIntExtra("COUNT",0);
            textView.setText("Your Total Score is : "+count+"/"+QuestionBank.coun+"\n Percentage is: "+(new DecimalFormat(".#").format((float)((float)count/(float)QuestionBank.coun)*100))+"%");
//            try {
//                QuizActivity.resultDatabase.resultDao().create(count, QuestionBank.coun, Float.parseFloat(new DecimalFormat(".#").format((float) ((float) count / (float) QuestionBank.coun) * 100)));
//                for (int i = 0; i < QuizActivity.resultDatabase.resultDao().getSize(); i++) {
//                    Log.i("Pranshu Database: ", "Mark :" + QuizActivity.resultDatabase.resultDao().getMark(i));
//                    Log.i("Pranshu Database: ", "Percent :" + QuizActivity.resultDatabase.resultDao().getPercent(i));
//                    Log.i("Pranshu Database: ", "Total :" + QuizActivity.resultDatabase.resultDao().getTotal(i));
//                }
//            }catch(Exception e){
//
//            }
        }
        findViewById(R.id.viewd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(ResultActivity.this,McqActivity.class);
                intent1.putExtra("Check",1);
                ResultActivity.this.startActivity(intent1);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent= new Intent(ResultActivity.this,LoginActivity.class);
        ResultActivity.this.startActivity(intent);
        super.onBackPressed();
    }
}
