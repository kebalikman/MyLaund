package com.example.mylaund;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.mylaund.databinding.FragmentOrderBerhasilBinding;
import com.example.mylaund.databinding.FragmentOrderLaundryBinding;
import com.example.mylaund.databinding.FragmentSecondBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.Random;

public class OrderBerhasilFragment extends Fragment {


    int cl_pkn = 0;
    int cl_slm = 0;

    private FragmentOrderBerhasilBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        //View myView = inflater.inflate(R.layout.fragment_second, container, false);
        binding = FragmentOrderBerhasilBinding.inflate(inflater, container, false);


        return binding.getRoot();


    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.layoutOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainMenu();
            }
        });

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                mainMenu();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    public void mainMenu(){
        NavHostFragment.findNavController(OrderBerhasilFragment.this)
                .navigate(R.id.action_OrderBerhasil_to_FirstFragment);
    }



}