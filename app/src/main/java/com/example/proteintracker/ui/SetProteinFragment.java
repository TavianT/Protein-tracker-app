package com.example.proteintracker.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.proteintracker.R;
import com.example.proteintracker.ui.adapter.DecimalDigitsInputFilter;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetProteinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetProteinFragment extends Fragment {

    EditText proteinEditText;
    Button submitButton;

    public SetProteinFragment() {
        // Required empty public constructor
    }


    public static SetProteinFragment newInstance(String param1, String param2) {
        SetProteinFragment fragment = new SetProteinFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_set_protein, container, false);
        proteinEditText = v.findViewById(R.id.setProteinEditText);
        proteinEditText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(5,2)});
        submitButton = v.findViewById(R.id.submitProteinButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String protein = proteinEditText.getText().toString().trim();
                    SharedPreferences prefs = requireActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("proteinTarget", protein);
                    editor.apply();

                } catch (NumberFormatException e) {
                    Log.e("NumberFormatException", Objects.requireNonNull(e.getMessage()));
                }
                Navigation.findNavController(requireView()).navigate(R.id.action_setProteinFragment_to_homeFragment);
            }
        });
        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        super.onCreateOptionsMenu(menu, inflater);
    }
}