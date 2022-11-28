package com.example.mylaund;

import static android.view.View.GONE;

import static com.example.mylaund.SecondFragment.alamat_passed;
import static com.example.mylaund.SecondFragment.durasi_passed;
import static com.example.mylaund.SecondFragment.jenis_cucian_passed;
import static com.example.mylaund.SecondFragment.seterika_passed;

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
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylaund.databinding.FragmentOrderLaundryBinding;
import com.example.mylaund.databinding.FragmentSecondBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class OrderLaundry extends Fragment {

    private static String JSON_URL_ADD = "http://10.200.224.23/basic-slim/orderlist/add";


    int cl_pkn = 0;
    int cl_slm = 0;

    private FragmentOrderLaundryBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        //View myView = inflater.inflate(R.layout.fragment_second, container, false);
        binding = FragmentOrderLaundryBinding.inflate(inflater, container, false);

        binding.textViewPassedAlamat.setText(alamat_passed);
        binding.textViewPassedJenisCucian.setText(jenis_cucian_passed);

        if (seterika_passed==2){
            binding.textViewPassedSeterika.setText("Iya");
        } else binding.textViewPassedSeterika.setText("Tidak");

        switch (durasi_passed){
            case 1:
                binding.textViewPassedDurasi.setText("Reguler");
                break;
            case 2:
                binding.textViewPassedDurasi.setText("Swift");
                break;
            case 3:
                binding.textViewPassedDurasi.setText("Same Day");
                break;
        }

        return binding.getRoot();


    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btKonfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addData();
                NavHostFragment.findNavController(OrderLaundry.this)
                        .navigate(R.id.action_OrderLaundry_to_OrderBerhasil);
            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }

    public void addData() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, JSON_URL_ADD, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

//                Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
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