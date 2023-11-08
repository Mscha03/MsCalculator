package com.mscha.smallcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mscha.smallcalculator.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;

    String mathProblem = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        //textview
        mainBinding.formulaTextview.setText(mathProblem);

        //number button
        mainBinding.number0Button.setOnClickListener(view -> {
            mathProblem += "0";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.number1Button.setOnClickListener(view -> {
            mathProblem += "1";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.number2Button.setOnClickListener(view -> {
            mathProblem += "2";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.number3Button.setOnClickListener(view -> {
            mathProblem += "3";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.number4Button.setOnClickListener(view -> {
            mathProblem += "4";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.number5Button.setOnClickListener(view -> {
            mathProblem += "5";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.number6Button.setOnClickListener(view -> {
            mathProblem += "6";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.number7Button.setOnClickListener(view -> {
            mathProblem += "7";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.number8Button.setOnClickListener(view -> {
            mathProblem += "8";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.number9Button.setOnClickListener(view -> {
            mathProblem += "9";
            mainBinding.formulaTextview.setText(mathProblem);
        });

        //clear button
        mainBinding.allClearButton.setOnClickListener(view -> {
            mathProblem = "";
            mainBinding.formulaTextview.setText(mathProblem);
            mainBinding.answerFormula.setText("0");
        });
        mainBinding.clearButton.setOnClickListener(view -> {

        });

        //operator button
        mainBinding.plusButton.setOnClickListener(view -> {
            mathProblem += "+";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.minusButton.setOnClickListener(view -> {
            mathProblem += "-";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.multiplyButton.setOnClickListener(view -> {
            mathProblem += "×"; //alt+0215
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.divideButton.setOnClickListener(view -> {
            mathProblem += "/";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.percentButton.setOnClickListener(view -> {
            mathProblem += "%";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.powerButton.setOnClickListener(view -> {
            mathProblem += "^";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.squareRootButton.setOnClickListener(view -> {
            mathProblem += "√"; //alt+251
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.bracketButton.setOnClickListener(view -> {
            mathProblem += "/";
            mainBinding.formulaTextview.setText(mathProblem);
        });
        mainBinding.equalButton.setOnClickListener(view -> {

//            mainBinding.answerFormula.setText(b);
            //mainBinding.answerFormula.setText(mathProblem);
        });

    }


    //تابعی که
    // آرایه ای از استرینگ که هر علامت و خونه یه دونه شو اشغال میکنه
    // به اپراتوی رسیدیم خودشو و چپ وراستشو مرتب و یکی میکنیم
    // به غیر از پرانتز که باید جدا استک بشه

    public static String[] dividingEquation(String Entry) {
        String[] arrangedString = new String[((2*numberOfOperator(Entry))+1) - (numberOfBrackets(Entry))];
        char[] cha = Entry.toCharArray();
        String member = "";
        int j = 0;
        for (int i = 0; i < Entry.length(); i++) {
            if (!isOperator(cha[i])) {
                member += cha[i];
                if(i == Entry.length() -1) {
                    arrangedString[j] = member;
                }
            } else {
                if(!member.isEmpty()) {
                    arrangedString[j] = member;
                    arrangedString[j+1] = String.valueOf(cha[i]);
                    j += 2;
                }else {
                    arrangedString[j] = String.valueOf(cha[i]);
                    j += 1;
                }
                member = "";
            }
        }
        return arrangedString;
    }



    public static boolean isOperator(char o) {
        return o == '+' || o == '-' || o == '×' || o == '/' ||
                o == '(' || o == ')' || o == '^' || o == '√' || o == '%';
    }

    public static int numberOfBrackets(String Entry) {
        int answer=0;
        char[] cha = Entry.toCharArray();
        for (int i = 0; i < Entry.length(); i++){
            if(cha[i]=='(' || cha[i]==')') {
                answer++;
            }
        }
        return answer;
    }


    public static int numberOfOperator(String Entry) {
        int answer=0;
        char[] cha = Entry.toCharArray();
        for (int i = 0; i < Entry.length(); i++){
            if(isOperator(cha[i])) {
                answer++;
            }
        }
        return answer;
    }



    public static void change(char[] ca, int ci){

    }


    public static String toPrefix(String infix) {
        char[] problem = infix.toCharArray();
        char[] prefix = new char[5];
        for (int i = 4; i > 0; i--){
            for (int j=0; j<infix.length(); j++){
                if (precedence(problem[j]) == i){
                    //solve problem
                }
            }
        }

        return prefix.toString();
    }

    private static int precedence(char c) {
        if (c == '(') {
            return 4;
        }else if (c == '^') {
            return 3;
        } else if (c == '*' || c == '/') {
            return 2;
        } else if (c == '+' || c == '-') {
            return 1;
        } else {
            return 0; // Assuming all other characters have lowest precedence
        }
    }

}

