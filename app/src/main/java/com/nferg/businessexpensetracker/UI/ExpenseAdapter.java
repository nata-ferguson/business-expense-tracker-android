package com.nferg.businessexpensetracker.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nferg.businessexpensetracker.R;
import com.nferg.businessexpensetracker.entities.Expense;

import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.ExpenseViewHolder>{
    class ExpenseViewHolder extends RecyclerView.ViewHolder {
        private final TextView expenseItemView;
        private final TextView expenseItemView2;
        private ExpenseViewHolder (View itemview) {
            super(itemview);
            expenseItemView = itemview.findViewById(R.id.textViewexpensename);
            expenseItemView2 = itemview.findViewById(R.id.textViewexpenseprice);

            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Expense current=mExpenses.get(position);

                    // Log the tripID as it's being sent
                    //Log.d("ExpenseAdapter", "Sending tripID: " + current.getTripID());

                    Intent intent = new Intent(context, ExpenseDetails.class);
                    intent.putExtra("id", current.getExpenseID());
                    intent.putExtra("name", current.getExpenseName());
                    intent.putExtra("price", current.getPrice());
                    intent.putExtra("tripID", current.getTripID());
                    intent.putExtra("date", current.getExpenseDate());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Expense> mExpenses;
    private final Context context;
    private final LayoutInflater mInflater;

    public ExpenseAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ExpenseAdapter.ExpenseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.expense_list_item, parent, false);
        return new ExpenseViewHolder((itemView));
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseAdapter.ExpenseViewHolder holder, int position) {
        if(mExpenses != null) {
            Expense current = mExpenses.get(position);
            String name = current.getExpenseName();
            double price = current.getPrice();
            holder.expenseItemView.setText(name);
            holder.expenseItemView2.setText(Double.toString(price));
        }
        else {
            holder.expenseItemView.setText("No Expense Name");
            holder.expenseItemView2.setText("No Expense Price");
        }
    }

    @Override
    public int getItemCount() {
        return mExpenses.size();
    }

    public void setExpenses(List<Expense> expenses){
        mExpenses = expenses;
        notifyDataSetChanged();
    }

}

