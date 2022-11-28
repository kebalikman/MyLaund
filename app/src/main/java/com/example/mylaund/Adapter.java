package com.example.mylaund;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private static String JSON_URL_DELETE = "http://10.200.224.23/basic-slim/orderlist/delete";
    static int order_id_adp, seterika_adp, durasi_adp;
    static String alamat_adp, jenisCucian_adp;

    LayoutInflater inflater;
    List<Order> orderList;

    public Adapter(Context ctx, List<Order> orderList){
        this.inflater = LayoutInflater.from(ctx);
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom_list_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    //bind the data
        holder.order_number.setText(String.valueOf(position+1));
        holder.order_id.setText(String.valueOf(orderList.get(position).getOrder_id()));
        holder.alamat.setText(orderList.get(position).getAlamat());
        holder.jenis_cucian.setText(orderList.get(position).getJenis_cucian());

        if (orderList.get(position).getSeterika()==2){
            holder.seterika.setText("Seterika");
        } else {
            holder.seterika.setText("Tidak Seterika");
        }

        if (orderList.get(position).getDurasi()==1){
            holder.durasi.setText("Reguler");
        } else {
            if (orderList.get(position).getDurasi()==2){
                holder.durasi.setText("Swift");
            } else { holder.durasi.setText("Same Day"); }
        }


    }

    @Override
    public int getItemCount() { return orderList.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView order_id, alamat, jenis_cucian, seterika, durasi, order_number;
        ConstraintLayout constraintLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            order_number = itemView.findViewById(R.id.order_number);
            order_id = itemView.findViewById(R.id.order_id);
            alamat = itemView.findViewById(R.id.alamat);
            jenis_cucian = itemView.findViewById(R.id.jenis_cucian);
            seterika = itemView.findViewById(R.id.seterika);
            durasi = itemView.findViewById(R.id.durasi);
            constraintLayout = itemView.findViewById(R.id.layout_recycler);

            constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    AlertDialog.Builder PUHelpBuilder = new AlertDialog.Builder(itemView.getContext());

                    LayoutInflater inflater = LayoutInflater.from(itemView.getContext());
                    View DialogLayout = inflater.inflate(R.layout.popup_window, null);
                    PUHelpBuilder.setView(DialogLayout);


                    AlertDialog helpDialog = PUHelpBuilder.create();
                    helpDialog.show();

                    TextView batal = DialogLayout.findViewById(R.id.tv_batal);
                    TextView edit = DialogLayout.findViewById(R.id.tv_edit);

                    batal.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            deleteData();
                            orderList.remove(getAdapterPosition());
                            notifyItemRemoved(getAdapterPosition());
                            helpDialog.dismiss();
                        }

                        private void deleteData() {
                            RequestQueue queue = Volley.newRequestQueue(itemView.getContext());
                            StringRequest postRequest = new StringRequest(Request.Method.POST, JSON_URL_DELETE,
                                    new Response.Listener<String>()
                                    {
                                        @Override
                                        public void onResponse(String response) {
                                            // if success
//                                            Toast.makeText(itemView.getContext(), "Response :"+ response, Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    new Response.ErrorListener()
                                    {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // error
                                            Toast.makeText(itemView.getContext(), "Error :" + error, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            ) {
                                @Override
                                protected Map<String, String> getParams()
                                {
                                    Map<String, String>  params = new HashMap<String, String>();
                                    params.put("order_id", order_id.getText().toString());

                                    return params;
                                }
                            };
                            queue.add(postRequest);
                        }

                    });

                    edit.setOnClickListener(new View.OnClickListener() {

                        public void onClick(View v) {
                            order_id_adp = orderList.get(getAdapterPosition()).getOrder_id();
                            alamat_adp = orderList.get(getAdapterPosition()).getAlamat();
                            jenisCucian_adp = orderList.get(getAdapterPosition()).getJenis_cucian();
                            seterika_adp = orderList.get(getAdapterPosition()).getSeterika();
                            durasi_adp = orderList.get(getAdapterPosition()).getDurasi();

                            Navigation.findNavController(view).navigate(R.id.action_FirstFragment_to_EditFragment);
                            helpDialog.dismiss();
                        }
                    });


                    return false;
                }
                
            });

        }

    }




}
