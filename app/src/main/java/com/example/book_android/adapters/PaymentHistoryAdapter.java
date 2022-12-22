package com.example.book_android.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.book_android.R;
import com.example.book_android.models.Receipt;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @Override
    public void onBindViewHolder(@NonNull PaymentHistoryAdapter.HistoryHolder holder, int position) {
        Receipt receipt = receipts.get(position);
        DecimalFormat formatter = new DecimalFormat("###,###,###.##");
        holder.txtReceiptID.setText("Mã hóa đơn: " + receipt.getId());
        holder.txtBookName.setText("Sách: " + receipt.getBook().getBookName());
        holder.txtDateBuy.setText("Ngày mua: " + convertDate(receipt.getCreatedAt()));
        holder.txtPrice.setText("Tổng thanh toán: " + formatter.format(receipt.getPrice()) + " VND");
    }

    @Override
    public int getItemCount() {
        return receipts.size();
    }

    private String convertDate (String value) {
        String dtStart = value;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        try {
            Date date = format.parse(dtStart);
            String dateTime = format.format(date);
            return dateTime;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
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
