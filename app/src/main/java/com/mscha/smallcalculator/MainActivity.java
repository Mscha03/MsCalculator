package com.mscha.smallcalculator;

import static java.lang.Double.sum;
import static java.lang.Math.pow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mscha.smallcalculator.databinding.ActivityMainBinding;

import java.util.Objects;
import java.util.Stack;
import java.util.Vector;

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
        mainBinding.answerFormula.setText("0");

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
            if (!lastInput.empty()) {
                if (Objects.equals(mathProblem, lastInput.peek())){
                    lastInput.pop();
                }
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
            if ((mathProblem.isEmpty() || isOperator(lastInput(mathProblem))) &&
                    !lastInput(mathProblem).equals(")")) {
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

            mainBinding.answerFormula.setText(solvePrefix(infixToPrefix(mathProblem)));
        });

    }


    private void numberButtonFunction(String number,Stack<String> lastInput) {

        if (mathProblem.isEmpty() || !lastInput(mathProblem).equals(")")) {
            mathProblem += number;
        }else{
            mathProblem += "×("+number;
            openBrackets++;
        }
        mainBinding.formulaTextview.setText(mathProblem);
        lastInput.push(mathProblem);
    }

    public static String[] infixToPrefix(String infixString) {
        Stack<String> stack = new Stack<>();
        String[] infix = dividingEquation(infixString);
        reverseArray(infix);
        Vector<String> prefix = new Vector<>();

        for (int i = 0; i < infix.length; i++) {
            String currentChar = infix[i];

            if (isOperand(currentChar)) {
                prefix.add(currentChar);
            } else if (Objects.equals(currentChar, ")")) {
                stack.push(currentChar);
            } else if (Objects.equals(currentChar, "(")) {
                while (!stack.isEmpty() && !Objects.equals(stack.peek(), ")")) {
                    prefix.add(stack.pop());
                }
                stack.pop(); // Pop the '('

            } else if (isOperator(currentChar)) {
                while (!stack.isEmpty() && precedence(currentChar) < precedence(stack.peek())) {
                    prefix.add(stack.pop());
                }
                stack.push(currentChar);
            }
        }

        while (!stack.isEmpty()) {
            prefix.add(stack.pop());
        }


        String[] prefixHelper = prefix.toArray(new String[prefix.size()]);
        reverseArray(prefixHelper);
        return prefixHelper;
    }

    private static boolean isOperand(String s) {
//        return Character.isLetterOrDigit(c);
        return android.text.TextUtils.isDigitsOnly(s);
    }

    private static boolean isOperator(String c) {
        return Objects.equals(c, "+") || Objects.equals(c, "-") ||
                Objects.equals(c, "×") || Objects.equals(c, "/") || Objects.equals(c, "^");
    }

    private static int precedence(String c) {
        if (Objects.equals(c, "^")) {
            return 3;
        } else if (Objects.equals(c, "×") || Objects.equals(c, "/")) {
            return 2;
        } else if (Objects.equals(c, "+") || Objects.equals(c, "-")) {
            return 1;
        } else {
            return 0; // Assuming all other characters have lowest precedence
        }
    }

    public static String[] dividingEquation(String Entry) {
        String[] arrangedString = new String[((2*numberOfOperator(Entry))+1) + (numberOfBrackets(Entry))];
        char[] cha = Entry.toCharArray();
        String member = "";
        int j = 0;
        for (int i = 0; i < Entry.length(); i++) {
            if (!isOperator(String.valueOf(cha[i])) && !isBracket(String.valueOf(cha[i]))){

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

    private static boolean isBracket(String s) {
        return String.valueOf(s).equals("(") || String.valueOf(s).equals(")");
    }

    public static int numberOfBrackets(String Entry) {
        int answer=0;
        char[] cha = Entry.toCharArray();
        for (int i = 0; i < Entry.length(); i++){
            if(isBracket(String.valueOf(cha[i]))) {
                answer++;
            }
        }
        return answer;
    }

    public static int numberOfOperator(String Entry) {
        int answer=0;
        char[] cha = Entry.toCharArray();
        for (int i = 0; i < Entry.length(); i++){
            if(isOperator(String.valueOf(cha[i]))) {
                answer++;
            }
        }
        return answer;
    }

    public static String lastInput(String Entry) {
        if (!Entry.isEmpty()){
        char[] E = Entry.toCharArray();
        return (String.valueOf(E[Entry.length()-1])) ;
        } else {
            return " ";
        }
    }

    public static void reverseArray(String[] validData) {
        for(int i = 0; i < validData.length / 2; i++)
        {
            String temp = validData[i];
            validData[i] = validData[validData.length - i - 1];
            validData[validData.length - i - 1] = temp;
        }
    }

    public static String solvePrefix(String[] mathProblem) {
        Stack<String> stack = new Stack<>();
        String lastOne = "";
        String answer = "";

        for (int i = 0; i < mathProblem.length; i++){
                lastOne = mathProblem[i];
            pushPre(stack, lastOne);
            answer = stack.peek();
        }

        return answer;
    }

    public static void pushPre(Stack<String> stack, String lastOne) {
        if (isOperator(lastOne)) {
            stack.push(lastOne);
        } else {
                if (stack.isEmpty() || isOperator(stack.peek())) {
                    stack.push(lastOne);

                } else {
                    lastOne = solveMathProblem(lastOne, stack.pop(), stack.pop());
                    pushPre(stack, lastOne);
                }
        }
    }

    public static String solveMathProblem(String firstNumber, String twiceNumber, String  operator){
        switch (operator) {
            case "+":
                return String.valueOf(Double.parseDouble(firstNumber)+Double.parseDouble(twiceNumber));
            case "-":
                return String.valueOf((Double.parseDouble(twiceNumber)-Double.parseDouble(firstNumber)));
            case "×":
                return String.valueOf((Double.parseDouble(firstNumber)*Double.parseDouble(twiceNumber)));
            case "/":
                return String.valueOf((Double.parseDouble(twiceNumber)/Double.parseDouble(firstNumber)));
            case "^":
                return String.valueOf(pow(Double.parseDouble(twiceNumber),Double.parseDouble(firstNumber)));
            default:
                return "0";
        }
    }

}