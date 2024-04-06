package com.zybooks.simplecalculator;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;




//Reference for Calculator
//https://www.geeksforgeeks.org/how-to-build-a-simple-calculator-app-using-android-studio/
public class CalculatorFragment extends Fragment implements View.OnClickListener {




    public TextView calculateMainText;
    public TextView calculateFunctionText;
    private Button zeroButton;
    private Button oneButton;
    private Button twoButton;
    private Button threeButton;
    private Button fourButton;
    private Button fiveButton;
    private Button sixButton;
    private Button sevenButton;
    private Button eightButton;
    private Button nineButton;
    private Button divideButton;
    private Button addButton;
    private Button multiplyButton;
    private Button subtractButton;
    private Button enterButton;
    private Button decimalButton;

    private boolean hasDecimal;
    private boolean operating;
    private boolean reset;
    private double num1;
    private double num2;

    private double result;
    private String currentOperator;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parentView = inflater.inflate(R.layout.fragment_calculator, container,false);
        // Inflate the layout for this fragment
        inflater.inflate(R.layout.fragment_calculator, container, false);

        zeroButton = parentView.findViewById(R.id.zeroButton);
        zeroButton.setOnClickListener(this);
        oneButton = parentView.findViewById(R.id.oneButton);
        oneButton.setOnClickListener(this);
        twoButton = parentView.findViewById(R.id.twoButton);
        twoButton.setOnClickListener(this);
        threeButton = parentView.findViewById(R.id.threeButton);
        threeButton.setOnClickListener(this);
        fourButton = parentView.findViewById(R.id.fourButton);
        fourButton.setOnClickListener(this);
        fiveButton = parentView.findViewById(R.id.fiveButton);
        fiveButton.setOnClickListener(this);
        sixButton = parentView.findViewById(R.id.sixButton);
        sixButton.setOnClickListener(this);
        sevenButton = parentView.findViewById(R.id.sevenButton);
        sevenButton.setOnClickListener(this);
        eightButton = parentView.findViewById(R.id.eightButton);
        eightButton.setOnClickListener(this);
        nineButton = parentView.findViewById(R.id.nineButton);
        nineButton.setOnClickListener(this);
        divideButton = parentView.findViewById(R.id.divideButton);
        divideButton.setOnClickListener(this);
        addButton = parentView.findViewById(R.id.addButton);
        addButton.setOnClickListener(this);
        subtractButton = parentView.findViewById(R.id.subtractButton);
        subtractButton.setOnClickListener(this);
        multiplyButton = parentView.findViewById(R.id.multiplyButton);
        multiplyButton.setOnClickListener(this);
        enterButton = parentView.findViewById(R.id.enterButton);
        enterButton.setOnClickListener(this);
        decimalButton = parentView.findViewById(R.id.decimalButton);
        decimalButton.setOnClickListener(this);

        calculateMainText = parentView.findViewById(R.id.calculateMainText);
        calculateMainText.setText("0");
        calculateFunctionText= parentView.findViewById(R.id.calculateFunctionView);

        return parentView;
    }

    @Override
    public void onClick(View v){

        if(v.getId() == R.id.zeroButton) {
            evaluate('0');
        } else if(v.getId() == R.id.oneButton) {
            evaluate('1');
        }else if(v.getId() == R.id.twoButton) {
            evaluate('2');
        }else if(v.getId() == R.id.threeButton) {
            evaluate('3');
        }else if(v.getId() == R.id.fourButton) {
            evaluate('4');
        }else if(v.getId() == R.id.fiveButton) {
            evaluate('5');
        }else if(v.getId() == R.id.sixButton) {
            evaluate('6');
        }else if(v.getId() == R.id.sevenButton) {
            evaluate('7');
        }else if(v.getId() == R.id.eightButton) {
            evaluate('8');
        }else if(v.getId() == R.id.nineButton) {
            evaluate('9');
        }else if(v.getId() == R.id.addButton) {
            evaluate('+');
        }else if(v.getId() == R.id.subtractButton) {
            evaluate('-');
        }else if(v.getId() == R.id.multiplyButton) {
            evaluate('*');
        }else if(v.getId() == R.id.divideButton) {
            evaluate('/');
        }else if(v.getId() == R.id.decimalButton) {
            evaluate('.');
        }else if(v.getId() == R.id.enterButton) {
            evaluate('=');
        }

    }

    public void evaluate(char c)
    {
        String token = String.valueOf(c);
        String currentMainField = calculateMainText.getText().toString();
        String currentMiniFiled = calculateFunctionText.getText().toString();
        if(token.equals(".") && !currentMainField.isEmpty()){
            if(!hasDecimal) {
                calculateMainText.setText(currentMainField + token);
            }
            hasDecimal = true;
            operating = false;
            return;
        }
        if(isNumeric(token)){
            if(reset){
                calculateMainText.setText(token);
                currentMainField = calculateMainText.getText().toString();
                reset = false;
                hasDecimal = false;
                return;
            }
            if((hasDecimal || !currentMainField.equals("0") )&& !operating){
                calculateMainText.setText(currentMainField + token);
            }else{
                calculateMainText.setText(token);
                operating = false;
                hasDecimal = false;
            }
            //calculateMainText.setText(token);
            //calcStack.push(token);
            return;
        }

        if(isOperator(token)){
            if(reset){
                num1 = result;
                reset = false;
            }else{
                num1 = Double.parseDouble(currentMainField);
            }
            currentOperator = token;

            operating = true;
        }

        if(token.equals("=")){
            if(currentOperator!=null) {
                num2 = Double.parseDouble(currentMainField);





                //Log.d("num1", String.valueOf(num1));
                //Log.d("current operator", currentOperator);
                //Log.d("num2", String.valueOf(num2));
                switch (currentOperator) {
                    case ("+"):
                        result = num1 + num2;
                        break;
                    case ("-"):
                        result = num1 - num2;
                        break;
                    case ("*"):
                        result = num1 * num2;
                        break;
                    case ("/"):
                        result = num1 / num2;
                        break;
                }

                String equation = String.format("%s %s %s = %s", num1, currentOperator, num2, result);
                EquationAdapter equationAdapter = new EquationAdapter(new ArrayList<>(), requireContext());
                equationAdapter.saveEquation(equation);

                DecimalFormat decimalFormat = new DecimalFormat("#.######"); // Adjust the number of decimal places as needed
                String formattedResult = decimalFormat.format(result);
                calculateMainText.setText(formattedResult);


                calculateFunctionText.setText(String.valueOf(num1)
                        + currentOperator + String.valueOf(num2));
                calculateMainText.setText(String.valueOf(result));

                reset = true;
                hasDecimal = true;
            }
        }
        //Log.d("stackPush", "onClick: " + calcStack.peek() );
    }



    public boolean isNumeric(String s)
    {
        try{
            Double.parseDouble(s);
            return true;
        }catch (NumberFormatException nfe){
            return false;
        }
    }

    public boolean isOperator(String s){
        if(s.equals("+") || s.equals("-") || s.equals("*") || s.equals("/")){
            return true;
        }
        return false;
    }
}