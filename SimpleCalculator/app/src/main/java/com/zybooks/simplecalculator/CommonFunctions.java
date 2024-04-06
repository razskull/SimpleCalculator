package com.zybooks.simplecalculator;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Stack;
import java.util.StringTokenizer;

public class CommonFunctions extends Fragment {

    public TextView funcResult;

    private static final String[] PRESET_FUNCTIONS = {
            "2 + 2",
            "5 / 5",
            "10 * 10",
            "100 - 10",
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_functions, container, false);

        funcResult = view.findViewById(R.id.functionresult);

        ListView functionListView = view.findViewById(R.id.functionListView);


        ArrayAdapter<String> functionAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                PRESET_FUNCTIONS
        );


        functionListView.setAdapter(functionAdapter);

        functionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected pre-set function
                String selectedFunction = PRESET_FUNCTIONS[position];

                // Start the AsyncTask to solve the selected pre-set function
                new SolveFunctionTask().execute(selectedFunction);
            }
        });

        return view;
    }
    private class SolveFunctionTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String selectedFunction = params[0];
            double result = Calculate(selectedFunction);
            return "Result of " + selectedFunction + " = " + result;
        }

        @Override
        protected void onPostExecute(String result) {
            showResultInTextView(result);
        }
    }

    public void showResultInTextView(String result) {
        funcResult.setText(result);
    }

    public double Calculate(String s) {
        Stack<Double> values = new Stack<>();
        Stack<Character> operators = new Stack<>();
        int i = 0;

        while (i < s.length()) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                StringBuilder numBuilder = new StringBuilder();
                while (i < s.length() && (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')) {
                    numBuilder.append(s.charAt(i));
                    i++;
                }
                double number = Double.parseDouble(numBuilder.toString());
                values.push(number);
            } else if (c == '+' || c == '-' || c == '*' || c == '/') {
                while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                    evaluateTop(values, operators);
                }
                operators.push(c);
                i++;
            } else {
                i++;
            }
        }

        while (!operators.isEmpty()) {
            evaluateTop(values, operators);
        }

        if (!values.isEmpty()) {
            return values.pop();
        }

        return 0.0; // Return 0.0 if there was an error or the equation is empty.
    }

    private boolean hasPrecedence(char op1, char op2) {
        return (op2 == '*' || op2 == '/') && (op1 == '+' || op1 == '-');
    }

    private void evaluateTop(Stack<Double> values, Stack<Character> operators) {
        char operator = operators.pop();
        double operand2 = values.pop();
        double operand1 = values.pop();
        double result = 0;
        switch (operator) {
            case '+':
                result = operand1 + operand2;
                break;
            case '-':
                result = operand1 - operand2;
                break;
            case '*':
                result = operand1 * operand2;
                break;
            case '/':
                result = operand1 / operand2;
                break;
        }
        values.push(result);
    }
}






