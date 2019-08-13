package com.manualde.app8819.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.manualde.app8819.R;
import com.manualde.app8819.entities.Employee;

import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {

    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    private boolean userPermitted = false;
    private List<Employee> employees;

    public EmployeeListAdapter(List<Employee> employees, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        this.employees = employees;
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
    }

    @NonNull
    @Override
    public EmployeeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(v);
    }

    public void setUserPermitted() {
        userPermitted = !userPermitted;
    }

    public Employee getItem(int position) {
        return employees.get(position);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeListAdapter.ViewHolder holder, int position) {
        Employee e = employees.get(getItemViewType(position));
        holder.onBind(e);
        int years = e.getAntiquity();
        if (years > 10)
            holder.itemView.findViewById(R.id.constraintRightSide).setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.ten_years));
        else if (years >= 5)
            holder.itemView.findViewById(R.id.constraintRightSide).setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.five_years));
        else
            holder.itemView.findViewById(R.id.constraintRightSide).setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.other_years));

        if (!userPermitted)
            holder.btnDetails.setVisibility(View.GONE);
        Glide.with(holder.ivProfile.getContext())
                .load(e.getProfileImage())
                .into(holder.ivProfile);
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public interface RecyclerViewOnItemClickListener {
        void onLongClick(View v, int position);

        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvName;
        TextView tvSurname;
        ImageView ivProfile;
        Button btnDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSurname = itemView.findViewById(R.id.tvSurname);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            btnDetails = itemView.findViewById(R.id.btnDetails);
            ivProfile.setClipToOutline(true);
            btnDetails.setOnClickListener(this);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        void onBind(Employee employee) {
            tvName.setText(employee.getName());
            tvSurname.setText(employee.getSurname());
        }

        @Override
        public void onClick(View view) {
            recyclerViewOnItemClickListener.onClick(view, getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            recyclerViewOnItemClickListener.onLongClick(view, getAdapterPosition());
            return true;
        }
    }

}
