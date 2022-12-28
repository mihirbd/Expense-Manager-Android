package com.example.dailyexpencemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dailyexpencemanager.databinding.ActivityAddBinding;

public class AddActivity extends AppCompatActivity {

    ActivityAddBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        boolean update=getIntent().getBooleanExtra("update", false);

        //Toast.makeText(this,""+update, Toast.LENGTH_SHORT).show();

        String desc=getIntent().getStringExtra("desc");
        int id=getIntent().getIntExtra("id",1);
        long amount=getIntent().getLongExtra("amount",-1);
        String ptype=getIntent().getStringExtra("paymentType");
        boolean isIncome=getIntent().getBooleanExtra("isIncome", false);

        if(update){
            binding.addButton.setText("Update");
            binding.amount.setText(amount+"");
            binding.paymentType.setText(ptype);
            binding.description.setText(desc);

            if (isIncome){
                binding.incomeRadio.setChecked(true);
            }else{
                binding.expenseRadio.setChecked(true);
            }
        }

        binding.backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String amount=binding.amount.getText().toString();
                String type=binding.paymentType.getText().toString();
                String desc=binding.description.getText().toString();
                boolean isIncome=binding.incomeRadio.isChecked();
                if (isIncome){
                    showToast("Add Income Successfully");
                }else {
                    showToast("Add Expense Successfully");
                }

                Intent intent =new Intent(getApplicationContext(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                ExpenseTable expenseTable=new ExpenseTable();
                expenseTable.setAmount(Long.parseLong((amount)));
                expenseTable.setDescription(desc);
                expenseTable.setPaymentType(type);
                expenseTable.setIncome(isIncome);

                ExpenseDatabase expenseDatabase=ExpenseDatabase.getInstance(view.getContext());
                ExpenseDao expenseDao=expenseDatabase.getDao();

                //expenseDao.insertExpense(expenseTable);

                if(!update){
                    expenseDao.insertExpense(expenseTable);
                }else {
                    expenseTable.setId(id);
                    expenseDao.updateExpense(expenseTable);
                }
                finish();
            }
        });

    }
    private void showToast(String msg){
        Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show();
    }


}