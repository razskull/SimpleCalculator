package com.zybooks.simplecalculator;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Reference for calculator
//https://www.geeksforgeeks.org/how-to-build-a-simple-calculator-app-using-android-studio/

public class PreviousEquations extends Fragment {

    private final List<String> previousEquations = new ArrayList<>();
    private static final String PREFS_NAME = "EquationPrefs"; // Name for SharedPreferences

    private List<String> retrieveHistoricalEquations() {
        SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, 0);
        Set<String> savedEquations = prefs.getStringSet("equations", new HashSet<>());
        return new ArrayList<>(savedEquations);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_previous_equations, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        // Create and set up the adapter
        EquationAdapter adapter = new EquationAdapter(previousEquations, requireContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Retrieve and populate previousEquations with historical data
        //retrieveHistoricalEquations();
        previousEquations.addAll(retrieveHistoricalEquations());


        return view;
    }


   // private void retrieveHistoricalEquations() {
     //   SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, 0);
       // Set<String> savedEquations = prefs.getStringSet("equations", new HashSet<>());

        // Populate previousEquations with historical data
       // previousEquations.addAll(savedEquations);
    //}

    // If you have a saveEquation function, you can use it here to save new equations
    // private void saveEquation(String equation) {
    //    SharedPreferences prefs = requireActivity().getSharedPreferences(PREFS_NAME, 0);
    //    Set<String> savedEquations = prefs.getStringSet("equations", new HashSet<>());
    //    savedEquations.add(equation);
    //    SharedPreferences.Editor editor = prefs.edit();
    //    editor.putStringSet("equations", savedEquations);
    //    editor.apply();
    //}
}
