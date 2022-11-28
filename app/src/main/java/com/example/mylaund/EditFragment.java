package com.example.mylaund;

import static android.view.View.GONE;

import static com.example.mylaund.Adapter.alamat_adp;
import static com.example.mylaund.Adapter.durasi_adp;
import static com.example.mylaund.Adapter.jenisCucian_adp;
import static com.example.mylaund.Adapter.order_id_adp;
import static com.example.mylaund.Adapter.seterika_adp;


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
import android.widget.Switch;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylaund.databinding.FragmentEditBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EditFragment extends Fragment {

    private static String JSON_URL_EDIT = "http://10.200.224.23/basic-slim/orderlist/edit";

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

    private FragmentEditBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        //View myView = inflater.inflate(R.layout.fragment_second, container, false);
        view2 = inflater.inflate(R.layout.fragment_order_laundry, container, false);
        binding = FragmentEditBinding.inflate(inflater, container, false);
        binding.checkBoxSeterika.setVisibility(GONE);
        binding.layoutButtonOrder.setVisibility(GONE);
        beforeEdit();
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
                editData();
                getActivity().onBackPressed();
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

    public void beforeEdit(){
        binding.inputAlamat.setText(alamat_adp);

        if (jenisCucian_adp.contains("Pakaian")){
            pressed_pakaian = true;
        } else pressed_pakaian = false;

        if (jenisCucian_adp.contains("Selimut/Sprei")){
            pressed_selimut = true;
        } else pressed_selimut = false;

        if (jenisCucian_adp.contains("Sepatu")){
            pressed_sepatu = true;
        } else pressed_sepatu = false;

        if (jenisCucian_adp.contains("Helm")){
            pressed_helm = true;
        } else pressed_helm = false;

        if (jenisCucian_adp.contains("Tas")){
            pressed_tas = true;
        } else pressed_tas = false;

        if (seterika_adp==2){
            binding.checkBoxSeterika.setChecked(true);
        } else binding.checkBoxSeterika.setChecked(false);


        switch (durasi_adp) {
            case 1:
                binding.reguler.setChecked(true);
                break;
            case 2:
                binding.swift.setChecked(true);
                break;
            case 3:
                binding.sameday.setChecked(true);
                break;

        }



    }

    public void editData() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, JSON_URL_EDIT, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Toast.makeText(getContext(), "response : "+ response, Toast.LENGTH_SHORT).show();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                //Toast.makeText(getContext(), "Ferror : " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();

                addPressedToList();
                alamat_passed = binding.inputAlamat.getText().toString();
                jenis_cucian_passed = TextUtils.join(", ", jenis_cucian_list);
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

                params.put("order_id", String.valueOf(order_id_adp));
                params.put("alamat", alamat_passed);
                params.put("jenis_cucian", jenis_cucian_passed);
                params.put("seterika", String.valueOf(seterika_passed));
                params.put("durasi", String.valueOf(durasi_passed));

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }



}