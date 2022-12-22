package com.example.book_android.adapters;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_android.R;
import com.example.book_android.models.Receipt;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.HistoryHolder> {
    private final List<Receipt> receipts;
    public PaymentHistoryAdapter(List<Receipt> receipts) {
        this.receipts = receipts;
    }
    @NonNull
    @Override
    public PaymentHistoryAdapter.HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_payment_history, parent, false);
        return new PaymentHistoryAdapter.HistoryHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull PaymentHistoryAdapter.HistoryHolder holder, int position) {
        Receipt receipt = receipts.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
//        String date = String.valueOf(Instant.parse(receipt.getCreatedAt()));
        holder.txtReceiptID.setText("Mã hóa đơn: " + receipt.getId());
        holder.txtBookName.setText("Sách: " + receipt.getBook().getBookName());
        holder.txtDateBuy.setText("Ngày mua: " + convertDate(receipt.getCreatedAt()));
        holder.txtPrice.setText("Tổng thanh toán: " + formatter.format(receipt.getPrice()) + " VND");
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    private String convertDate (Date date){
        date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String strDate = dateFormat.format(date);
        return strDate;
    }

    public static class HistoryHolder extends RecyclerView.ViewHolder{

        private TextView txtReceiptID, txtBookName, txtDateBuy, txtPrice;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);
            txtReceiptID = itemView.findViewById(R.id.txtReceiptID);
            txtBookName = itemView.findViewById(R.id.txtReceiptBookName);
            txtDateBuy = itemView.findViewById(R.id.txtReceiptDate);
            txtPrice = itemView.findViewById(R.id.txtTotalPayment);

        }
    }
}
