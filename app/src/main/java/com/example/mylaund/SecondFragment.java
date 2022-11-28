package com.example.mylaund;

import static android.view.View.GONE;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.transition.Fade;
import androidx.transition.Slide;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.example.mylaund.databinding.FragmentSecondBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SecondFragment extends Fragment {

    int cl_pkn = 0;
    int cl_slm = 0;
    View view2;
    static String alamat_passed;
    static String jenis_cucian_passed = "";
    static int seterika_passed = 2;
    static int durasi_passed = 0;

    static List<String> jenis_cucian_list = new ArrayList<>();
    static boolean pressed_pakaian = false;
    static boolean pressed_selimut = false;
    static boolean pressed_sepatu = false;
    static boolean pressed_helm = false;
    static boolean pressed_tas = false;
    static boolean alamatInputed = false;
    static boolean jenisCucianInputed = false;
    static boolean durasiInputed = false;

    static int seterika;
    static int durasi;

    private FragmentSecondBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        //View myView = inflater.inflate(R.layout.fragment_second, container, false);
        view2 = inflater.inflate(R.layout.fragment_order_laundry, container, false);
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        binding.checkBoxSeterika.setVisibility(GONE);
        binding.layoutButtonOrder.setVisibility(GONE);
        jenis_cucian_list.clear();
        checkPressedJenisPakaian();
        checkInput();
        toggle();
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.layoutPakaian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pressed_pakaian) {
                    pressed_pakaian = true;
                } else {
                    pressed_pakaian = false;
                }
                toggle();
                checkPressedJenisPakaian();
                checkInput();
            }
        });

        binding.layoutSelimut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!pressed_selimut) {
                    pressed_selimut = true;
                } else {
                    pressed_selimut = false;
                }
                toggle();
                checkPressedJenisPakaian();
                checkInput();
            }
        });

        binding.layoutSepatu.setOnClickListener(new View.OnClickListener() {
            int cl_pkn = 0;
            @Override
            public void onClick(View view) {
                if (!pressed_sepatu) {
                    pressed_sepatu = true;
                } else {
                    pressed_sepatu = false;
                }
                checkPressedJenisPakaian();
                checkInput();
            }
        });

        binding.layoutHelm.setOnClickListener(new View.OnClickListener() {
            int cl_pkn = 0;
            @Override
            public void onClick(View view) {
                if (!pressed_helm) {
                    pressed_helm = true;
                } else {
                    pressed_helm = false;
                }
                checkPressedJenisPakaian();
                checkInput();
            }
        });

        binding.layoutTas.setOnClickListener(new View.OnClickListener() {
            int cl_pkn = 0;
            @Override
            public void onClick(View view) {
                if (!pressed_tas) {
                    pressed_tas = true;
                } else {
                    pressed_tas = false;
                }
                checkPressedJenisPakaian();
                checkInput();
            }
        });


        binding.btOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                NavHostFragment.findNavController(SecondFragment.this)
//                        .navigate(R.id.action_SecondFragment_to_OrderLaundryFragment);

                alamat_passed = binding.inputAlamat.getText().toString();

                addPressedToList();

                if (binding.checkBoxSeterika.isChecked()){
                    seterika_passed = 2;
                } else seterika_passed = 1;

                if (binding.reguler.isChecked()){
                    durasi_passed = 1;
                } else if (binding.swift.isChecked()){
                    durasi_passed = 2;
                } else if (binding.sameday.isChecked()){
                    durasi_passed = 3;
                }

                jenis_cucian_passed = TextUtils.join(", ", jenis_cucian_list);

                NavHostFragment.findNavController(SecondFragment.this)
                        .navigate(R.id.action_SecondFragment_to_OrderLaundryFragment);
            }
        });

        binding.reguler.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            checkInput();
        });

        binding.swift.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            checkInput();
        });

        binding.sameday.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            checkInput();
        });

        binding.inputAlamat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInput();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });



    }



    private void toggle() {
//        Transition transition = new Fade();
//        transition.setDuration(600);
//        transition.addTarget(binding.checkBox);
//
//        TransitionManager.beginDelayedTransition(binding.getRoot(), transition);
        if (pressed_pakaian || pressed_selimut) {
            binding.checkBoxSeterika.setVisibility(View.VISIBLE);
        } else {
            binding.checkBoxSeterika.setVisibility(GONE);
        }
    }
    private void checkInput(){

        if (pressed_pakaian||pressed_selimut||pressed_sepatu||pressed_helm||pressed_tas){
            jenisCucianInputed = true;
        } else jenisCucianInputed = false;

        if (binding.inputAlamat.getText().toString().matches("")){
            alamatInputed = false;
        } else alamatInputed = true;

        if (binding.reguler.isChecked()||binding.swift.isChecked()||binding.sameday.isChecked()){
            durasiInputed = true;
        } else durasiInputed = false;


        Boolean allTrue = alamatInputed && jenisCucianInputed && durasiInputed;
        if (allTrue){
            binding.layoutButtonOrder.setVisibility(View.VISIBLE);
            binding.textViewWarning.setVisibility(View.GONE);
        }else
        {
            binding.layoutButtonOrder.setVisibility(GONE);
            binding.textViewWarning.setVisibility(View.VISIBLE);
        }

        if (!jenisCucianInputed&&!alamatInputed&&!durasiInputed){
            binding.textViewWarning.setText("Mohon lengkapi pesanan anda");
        } else if (!jenisCucianInputed){
            binding.textViewWarning.setText("Mohon pilih jenis cucian");
        } else if (!alamatInputed){
            binding.textViewWarning.setText("Mohon isi alamat anda");
        } else if (!durasiInputed){
            binding.textViewWarning.setText("Mohon pilih durasi pencucian");
        } else binding.textViewWarning.setVisibility(View.GONE);

    }

    private void pressJenisCucian(String jenisCucian){
        switch (jenisCucian) {
            case "Pakaian":
                binding.layoutPakaian.setElevation(0);
                binding.layoutPakaian.setBackgroundResource(R.drawable.whitebox_round_corners_pressed);
                cl_pkn=+1;
                break;
            case "Selimut/Sprei":
                binding.layoutSelimut.setElevation(0);
                binding.layoutSelimut.setBackgroundResource(R.drawable.whitebox_round_corners_pressed);
                cl_slm=+1;
                break;
            case "Sepatu":
                binding.layoutSepatu.setElevation(0);
                binding.layoutSepatu.setBackgroundResource(R.drawable.whitebox_round_corners_pressed);
                cl_pkn=+1;
                break;
            case "Helm":
                binding.layoutHelm.setElevation(0);
                binding.layoutHelm.setBackgroundResource(R.drawable.whitebox_round_corners_pressed);
                cl_pkn=+1;
                break;
            case "Tas":
                binding.layoutTas.setElevation(0);
                binding.layoutTas.setBackgroundResource(R.drawable.whitebox_round_corners_pressed);
                cl_pkn=+1;
                break;
        }
    }

    private void unpressJenisCucian(String jenisCucian){
        switch (jenisCucian) {
            case "Pakaian":
                binding.layoutPakaian.setElevation(10);
                binding.layoutPakaian.setBackgroundResource(R.drawable.whitebox_round_corners);
                cl_pkn = 0;
                break;
            case "Selimut/Sprei":
                binding.layoutSelimut.setElevation(10);
                binding.layoutSelimut.setBackgroundResource(R.drawable.whitebox_round_corners);
                cl_slm = 0;
                break;
            case "Sepatu":
                binding.layoutSepatu.setElevation(10);
                binding.layoutSepatu.setBackgroundResource(R.drawable.whitebox_round_corners);
                cl_pkn = 0;
                break;
            case "Helm":
                binding.layoutHelm.setElevation(10);
                binding.layoutHelm.setBackgroundResource(R.drawable.whitebox_round_corners);
                cl_pkn = 0;
                break;
            case "Tas":
                binding.layoutTas.setElevation(10);
                binding.layoutTas.setBackgroundResource(R.drawable.whitebox_round_corners);
                cl_pkn = 0;
                break;
        }
    }

    private void checkPressedJenisPakaian(){
        if (pressed_pakaian){
            pressJenisCucian("Pakaian");
        } else {
            unpressJenisCucian("Pakaian");
        }

        if (pressed_selimut){
            pressJenisCucian("Selimut/Sprei");
        } else {
            unpressJenisCucian("Selimut/Sprei");
        }

        if (pressed_sepatu){
            pressJenisCucian("Sepatu");
        } else {
            unpressJenisCucian("Sepatu");
        }

        if (pressed_helm){
            pressJenisCucian("Helm");
        } else {
            unpressJenisCucian("Helm");
        }

        if (pressed_tas){
            pressJenisCucian("Tas");
        } else {
            unpressJenisCucian("Tas");
        }
    }

    public void addPressedToList(){
        if (pressed_pakaian){
            jenis_cucian_list.add("Pakaian");
        } else {
            jenis_cucian_list.remove("Pakaian");
        }

        if (pressed_selimut){
            jenis_cucian_list.add("Selimut/Sprei");
        } else {
            jenis_cucian_list.remove("Selimut/Sprei");
        }

        if (pressed_sepatu){
            jenis_cucian_list.add("Sepatu");
        } else {
            jenis_cucian_list.remove("Sepatu");
        }

        if (pressed_helm){
            jenis_cucian_list.add("Helm");
        } else {
            jenis_cucian_list.remove("Helm");
        }

        if (pressed_tas){
            jenis_cucian_list.add("Tas");
        } else {
            jenis_cucian_list.remove("Tas");
        }
    }



}