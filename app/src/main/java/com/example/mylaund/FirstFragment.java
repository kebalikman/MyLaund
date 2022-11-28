package com.example.mylaund;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mylaund.databinding.FragmentFirstBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    TextView edit;

    ConstraintLayout clTambahPesanan;
    List<Order> orderList;
    RecyclerView recyclerView;
    private Adapter adapter;
    private static String JSON_URL = "http://10.200.224.23/basic-slim/orderlist";

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.fragment_first, container, false);
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        clTambahPesanan = view.findViewById(R.id.cl_tambah_pesanan);
        recyclerView = view.findViewById(R.id.recyclerview_orderlist);
        orderList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getContext(), orderList);
        extractOrderList();
        recyclerView.setAdapter(adapter);

        SecondFragment.pressed_pakaian = false;
        SecondFragment.pressed_selimut = false;
        SecondFragment.pressed_sepatu = false;
        SecondFragment.pressed_helm = false;
        SecondFragment.pressed_tas = false;


        return view;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clTambahPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void extractOrderList(){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, JSON_URL, null, response -> {
            for (int i = 0; i < response.length(); i++) {
                try {
                    JSONObject orderListObject = response.getJSONObject(i);

                    Order objOrderList = new Order();
                    objOrderList.setOrder_id(orderListObject.getInt("order_id"));
                    objOrderList.setAlamat(orderListObject.getString("alamat").toString());
                    objOrderList.setJenis_cucian(orderListObject.getString("jenis_cucian").toString());
                    objOrderList.setSeterika(orderListObject.getInt("seterika"));
                    objOrderList.setDurasi(orderListObject.getInt("durasi"));
                    orderList.add(objOrderList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                recyclerView.setAdapter(adapter);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "onErrorResponse: "+error.getMessage());
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonArrayRequest);
    }



}