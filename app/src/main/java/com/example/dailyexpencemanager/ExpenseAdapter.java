package com.example.dailyexpencemanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyViewHolder> {

    private Context context;
    private List<ExpenseTable> expenseTablesList;

    private ClickEvent clickEvent;
    public ExpenseAdapter(Context context, ClickEvent clickEvent) {

        this.context = context;
        expenseTablesList=new ArrayList<>();
        this.clickEvent=clickEvent;
    }

    public void add(ExpenseTable expenseTable){
        expenseTablesList.add(expenseTable);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ExpenseTable expenseTable=expenseTablesList.get(position);
        holder.amount.setText(String.valueOf(expenseTable.getAmount()));
        holder.title.setText(expenseTable.getPaymentType());
        holder.description.setText(expenseTable.getDescription());

        if(expenseTable.isIncome()){
            holder.status.setText("Income");
        }else {
            holder.status.setText("Expense");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickEvent.OnClick(position);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                clickEvent.OnLongPress(position);
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return expenseTablesList.size();
    }

    public int getId(int pos){
        return expenseTablesList.get(pos).getId();
    }

    public boolean isIncome(int pos){
        return expenseTablesList.get(pos).isIncome();
    }

    public String paymentType(int pos){
        return expenseTablesList.get(pos).getPaymentType();
    }

    public long amount(int pos){
        return expenseTablesList.get(pos).getAmount();
    }

    public String  desc(int pos){
        return expenseTablesList.get(pos).getDescription();
    }

    public void delete(int pos){
        expenseTablesList.remove(pos);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView amount, status,title, description;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            amount=itemView.findViewById(R.id.amount);
            status=itemView.findViewById(R.id.isIncome);
            title=itemView.findViewById(R.id.title);
            description=itemView.findViewById(R.id.description);
        }
    }

}
