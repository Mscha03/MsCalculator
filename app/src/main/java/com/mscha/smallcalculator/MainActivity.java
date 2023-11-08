package com.mscha.smallcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mscha.smallcalculator.databinding.ActivityMainBinding;

import java.util.Arrays;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;

    String mathProblem = "";
    int openBrackets = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());


        Stack<String> lastInput = new Stack<>();

        //textview
        mainBinding.formulaTextview.setText(mathProblem);

        //number button
        mainBinding.number0Button.setOnClickListener(view -> {
            numberButtonFunction("0",lastInput);
        });
        mainBinding.number1Button.setOnClickListener(view -> {
            numberButtonFunction("1",lastInput);
        });
        mainBinding.number2Button.setOnClickListener(view -> {
            numberButtonFunction("2",lastInput);
        });
        mainBinding.number3Button.setOnClickListener(view -> {
            numberButtonFunction("3",lastInput);
        });
        mainBinding.number4Button.setOnClickListener(view -> {
            numberButtonFunction("4",lastInput);
        });
        mainBinding.number5Button.setOnClickListener(view -> {
            numberButtonFunction("5",lastInput);
        });
        mainBinding.number6Button.setOnClickListener(view -> {
            numberButtonFunction("6",lastInput);
        });
        mainBinding.number7Button.setOnClickListener(view -> {
            numberButtonFunction("7",lastInput);
        });
        mainBinding.number8Button.setOnClickListener(view -> {
            numberButtonFunction("8",lastInput);
        });
        mainBinding.number9Button.setOnClickListener(view -> {
            numberButtonFunction("9",lastInput);
        });
        mainBinding.piButton.setOnClickListener(view -> {
            numberButtonFunction("π",lastInput);
        });

        //clear button
        mainBinding.allClearButton.setOnClickListener(view -> {
            mathProblem = "";
            mainBinding.formulaTextview.setText(mathProblem);
            mainBinding.answerFormula.setText("0");
            while (!lastInput.empty()){
                lastInput.pop();
            }
        });

        mainBinding.clearButton.setOnClickListener(view -> {
            if (mathProblem == lastInput.peek()){
                lastInput.pop();
            }
            if (!lastInput.empty()) {
                mathProblem = lastInput.pop();
                mainBinding.formulaTextview.setText(mathProblem);
            } else if (!mathProblem.isEmpty()) {
                mathProblem = "";
                mainBinding.formulaTextview.setText(mathProblem);
            }
        });

        //operator button
        mainBinding.plusButton.setOnClickListener(view -> {
            mathProblem += "+";
            mainBinding.formulaTextview.setText(mathProblem);
            lastInput.push(mathProblem);
        });
        mainBinding.minusButton.setOnClickListener(view -> {
            mathProblem += "-";
            mainBinding.formulaTextview.setText(mathProblem);
            lastInput.push(mathProblem);
        });
        mainBinding.multiplyButton.setOnClickListener(view -> {
            mathProblem += "×"; //alt+0215
            mainBinding.formulaTextview.setText(mathProblem);
            lastInput.push(mathProblem);
        });
        mainBinding.divideButton.setOnClickListener(view -> {
            mathProblem += "/";
            mainBinding.formulaTextview.setText(mathProblem);
            lastInput.push(mathProblem);
        });
        mainBinding.percentButton.setOnClickListener(view -> {
            mathProblem += "%";
            mainBinding.formulaTextview.setText(mathProblem);
            lastInput.push(mathProblem);
        });
        mainBinding.powerButton.setOnClickListener(view -> {
            mathProblem += "^";
            mainBinding.formulaTextview.setText(mathProblem);
            lastInput.push(mathProblem);
        });
        mainBinding.squareRootButton.setOnClickListener(view -> {
            mathProblem += "√"; //alt+251
            mainBinding.formulaTextview.setText(mathProblem);
            lastInput.push(mathProblem);
        });
        mainBinding.bracketButton.setOnClickListener(view -> {
            if ((mathProblem.isEmpty() || isOperator(lastInput(mathProblem))) && lastInput(mathProblem) != ')') {
                mathProblem += "(";
                openBrackets++;
            } else if (openBrackets == 0) {
                mathProblem += "×(";
                openBrackets++;
            }else {
                mathProblem += ")";
                openBrackets--;
            }
            mainBinding.formulaTextview.setText(mathProblem);
            lastInput.push(mathProblem);
        });
        mainBinding.equalButton.setOnClickListener(view -> {
            mainBinding.answerFormula.setText(mathProblem);
        });

    }

    private void numberButtonFunction(String number,Stack<String> lastInput) {

        if (mathProblem.isEmpty() || lastInput(mathProblem) != ')') {
            mathProblem += number;
        }else{
            mathProblem += "×("+number;
            openBrackets++;
        }
        mainBinding.formulaTextview.setText(mathProblem);
        lastInput.push(mathProblem);
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

    public static char lastInput(String Entry) {
        if (!Entry.isEmpty()){
        char[] E = Entry.toCharArray();
        return E[Entry.length()-1];
        } else {
            return ' ';
        }
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

