package com.zybooks.simplecalculator;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//Referenced from Developer Android
//https://developer.android.com/reference/androidx/recyclerview/widget/RecyclerView

public class EquationAdapter extends RecyclerView.Adapter<EquationAdapter.ViewHolder> {

    private final List<String> equations;
    private final Context context;

    public EquationAdapter(List<String> equations, Context context) {
        this.equations = equations;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_equation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String equation = equations.get(position);
        holder.equationTextView.setText(equation);
    }

    @Override
    public int getItemCount() {
        return equations.size();
    }

    public void saveEquation(String equation) {
        SharedPreferences prefs = context.getSharedPreferences("EquationPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> savedEquations = prefs.getStringSet("equations", new HashSet<>());
        savedEquations.add(equation);
        editor.putStringSet("equations", savedEquations);

        editor.apply();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView equationTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            equationTextView = itemView.findViewById(R.id.equationTextView);
        }
    }
}
