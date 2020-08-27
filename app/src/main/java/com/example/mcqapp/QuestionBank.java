package com.example.mcqapp;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
public class QuestionBank {
   static public ArrayList<String> answer=new ArrayList(Arrays.asList("","","","","","","","","","","","","","","","","","","",""));
   static public ArrayList<String> questions=new ArrayList<>();
   static public ArrayList<ArrayList<String>> options= new ArrayList<>();
   static public ArrayList<String> answers= new ArrayList<>();
    static public ArrayList<String> difficulty= new ArrayList<>();
    static public ArrayList<String> tag= new ArrayList<>();
    static public int coun;
    public QuestionBank(Context context){
    }
    public static ArrayList<ArrayList<String>> getOptions(){
        return options;
    }
    public static ArrayList<String> getQuestions(){
        return questions;
    }
    public static ArrayList<String> getAnswers(){
        return answers;
    }
    public static void answer(String value,int index){
        answer.set(index,value);
    }
    public static int generateReport(){
        int count=0;
        coun=0;
        for(int i=0;i<20;i++){

            if(answer.get(i).trim().equals(answers.get(i).trim())){
                if(difficulty.get(i)=="Difficult"){
                    count=count+11;
                }
                else if(difficulty.get(i)=="Medium"){
                    count=count+7;
                }
                else{
                    count=count+5;
                }
            }
            if(difficulty.get(i).equals("Difficult")){
                coun=coun+11;
            }
            else if(difficulty.get(i).equals("Medium")){
                coun=coun+7;
            }
            else{
                coun=coun+5;
            }

        }
        return count;
    }
    public static String getUserAnswer(int index){ return answer.get(index); }
}
