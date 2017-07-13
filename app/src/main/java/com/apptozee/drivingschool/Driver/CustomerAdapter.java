package com.apptozee.drivingschool.Driver;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apptozee.drivingschool.R;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder> {

    private List<Customer> customerList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, time, slot;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.cname);
            time = (TextView) view.findViewById(R.id.hours);
            slot = (TextView) view.findViewById(R.id.amount);
        }
    }


    public CustomerAdapter(List<Customer> customerList) {
        this.customerList = customerList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.customer_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Customer customer = customerList.get(position);
        holder.name.setText(customer.getnumber());
        holder.time.setText(customer.gettime());
        holder.slot.setText(customer.getslot());
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }
}
