package com.zybooks.simplecalculator;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {


    private EquationAdapter equationAdapter;

    private TextView calculateMainText;

    private TextView calculateFunctionText; // Declare the TextView here

    private double num1 = 0.0;
    private double num2 = 0.0;
    private double result = 0.0;
    private String currentOperator = "";
    private boolean reset = false; // Add this line
    private boolean hasDecimal = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        equationAdapter = new EquationAdapter(new ArrayList<>(), this);
        calculateFunctionText = findViewById(R.id.calculateFunctionText); // Initialize the TextView here
        calculateMainText = findViewById(R.id.calculateMainText); // Initialize calculateMainText


        calculateFunctionText.setText(String.valueOf(num1)
                + currentOperator + String.valueOf(num2));

        String equation = String.format("%s %s %s = %s", num1, currentOperator, num2, result);
        equationAdapter.saveEquation(equation);

        calculateMainText.setText(String.valueOf(result));

        reset = true;
        hasDecimal = true;


        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            AppBarConfiguration appBarConfig = new AppBarConfiguration.Builder(
                    R.id.navigation_calculator, R.id.navigation_common, R.id.navigation_previous)
                    .build();

            //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
            NavigationUI.setupWithNavController(navView, navController);
        }
    }


}