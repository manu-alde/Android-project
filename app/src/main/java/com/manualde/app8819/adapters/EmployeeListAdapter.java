package com.manualde.app8819.adapters;

import android.content.Context;
import android.net.Uri;
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
import com.manualde.app8819.data.SQLEmployeeController;
import com.manualde.app8819.entities.Employee;
import com.manualde.app8819.utils.SharedSettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class EmployeeListAdapter extends RecyclerView.Adapter<EmployeeListAdapter.ViewHolder> {


    public static final int NAME_ASC = 0;
    public static final int NAME_DSC = 1;
    public static final int ANTIQUITY_ASC = 2;
    public static final int ANTIQUITY_DSC = 3;
    private SQLEmployeeController sqlEmployeeController;
    private boolean edit = false;
    private RecyclerViewOnItemClickListener recyclerViewOnItemClickListener;
    private boolean userPermitted = false;
    private List<Employee> employees;
    private List<Employee> enabledPositions = new ArrayList<>();

    public EmployeeListAdapter(Context context, RecyclerViewOnItemClickListener recyclerViewOnItemClickListener) {
        sqlEmployeeController = new SQLEmployeeController(context);
        updateData();
        this.recyclerViewOnItemClickListener = recyclerViewOnItemClickListener;
        SharedSettings sharedSettings = new SharedSettings(context);
        if(!sharedSettings.isSampleCreated()){
            ArrayList<Employee> emp = new ArrayList<>();
            emp.add(new Employee("https://vignette.wikia.nocookie.net/zoolander/images/f/f1/Derek-Zoolander-in-a-turban.jpg/revision/latest/scale-to-width-down/250?cb=20160227145330", "Carlos", "Cabrera", 40, new Date(new GregorianCalendar(2000, 0, 1).getTimeInMillis()), "Economy", "Sales manager", "Promotion of new project"));
            emp.add(new Employee("https://vignette.wikia.nocookie.net/zoolander/images/8/84/Mugatu.jpg/revision/latest/scale-to-width-down/250?cb=20160304173642", "Esteban", "Polero", 30, new Date(new GregorianCalendar(2010, 0, 1).getTimeInMillis()), "Research", "Database manager", "NoSQL database maintenance"));
            emp.add(new Employee("https://4.bp.blogspot.com/-678o44zDrNw/V_TElaAr01I/AAAAAAAAg-4/qatE9pnO39I44bUaEfRIiTZWiIXNWt9MgCLcB/s1600/Christine%2BTaylor1.jpg", "Lucía", "Gimenez", 25, new Date(new GregorianCalendar(2019, 0, 20).getTimeInMillis()), "Development", "Development trainee", "Learning C#"));
            emp.add(new Employee("https://pbs.twimg.com/profile_images/791163918127394816/OXOiuvYu_400x400.jpg", "Ivan", "Carbone", 32, new Date(new GregorianCalendar(2018, 5, 25).getTimeInMillis()), "Design", "Design leader", "Creating logo for new project, coaching new trainees"));
            emp.add(new Employee("https://2.bp.blogspot.com/-mhyE427gwfk/TwdiQdysEYI/AAAAAAAAADA/4gUxnmqRcKc/s1600/woman_employee.jpg", "Sofía", "Gutierrez", 21, new Date(new GregorianCalendar(2019, 6, 2).getTimeInMillis()), "Design", "Design trainee", "Learning Inkscape"));
            for (Employee e:emp) {
                sqlEmployeeController.insertEmployee(e);
            }
            updateData();
        }
    }

    public void updateData() {
        employees = sqlEmployeeController.getEmployees();
        edit = false;
        enabledPositions.clear();
        notifyDataSetChanged();
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

    public void setEdit() {
        edit = !edit;
        if (!edit)
            enabledPositions.clear();
        notifyDataSetChanged();
    }

    public boolean isEdit() {
        return edit;
    }

    public List<Employee> getSelected() {
        return enabledPositions;
    }

    public void addSelected(Employee position) {
        if (!enabledPositions.contains(position))
            enabledPositions.add(position);
        notifyDataSetChanged();
    }

    public void removeSelected(Employee position) {
        if (enabledPositions.contains(position)) {
            enabledPositions.remove(position);
            if (enabledPositions.isEmpty()) {
                edit = false;
            }
            notifyDataSetChanged();
        }
    }

    public void deleteSelected() {
        if (!enabledPositions.isEmpty()) {
            for (Employee e : enabledPositions) {
                sqlEmployeeController.deleteEmployee(e);
            }
            updateData();
        }
        enabledPositions.clear();
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
        else {
            if (!edit) {
                holder.ivSelected.setVisibility(View.GONE);
                holder.btnDetails.setVisibility(View.VISIBLE);
            } else {
                holder.btnDetails.setVisibility(View.GONE);
            }
        }
        Glide.with(holder.ivProfile.getContext())
                .load(Uri.parse(e.getProfileImage()))
                .placeholder(R.drawable.ic_baseline_account_circle_24px)
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

    public void orderBy(int option) {
        switch (option) {
            case NAME_ASC:
                Collections.sort(employees, new Employee.SortByNameAsc());
                break;
            case NAME_DSC:
                Collections.sort(employees, new Employee.SortByNameDsc());
                break;
            case ANTIQUITY_ASC:
                Collections.sort(employees, new Employee.SortByAntiquityAsc());
                break;
            case ANTIQUITY_DSC:
                Collections.sort(employees, new Employee.SortByAntiquityDsc());
                break;
        }
        notifyDataSetChanged();
    }

    public interface RecyclerViewOnItemClickListener {
        void onLongClick(View v, int position);

        void onClick(View v, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView tvName;
        TextView tvSurname;
        ImageView ivProfile;
        ImageView ivSelected;
        Button btnDetails;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvSurname = itemView.findViewById(R.id.tvSurname);
            ivProfile = itemView.findViewById(R.id.ivProfile);
            ivSelected = itemView.findViewById(R.id.ivEdit);
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
