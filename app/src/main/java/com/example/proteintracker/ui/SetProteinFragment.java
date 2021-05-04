package com.example.proteintracker.ui;

import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.proteintracker.R;
import com.example.proteintracker.ui.adapter.DecimalDigitsInputFilter;

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

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetProteinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetProteinFragment newInstance(String param1, String param2) {
        SetProteinFragment fragment = new SetProteinFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
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
                double protein = Double.parseDouble(proteinEditText.getText().toString().trim());
                Log.d("protein", String.valueOf(protein)); //testing
                Navigation.findNavController(requireView()).navigate(R.id.action_setProteinFragment_to_homeFragment);
            }
        });
        return v;
    }
}