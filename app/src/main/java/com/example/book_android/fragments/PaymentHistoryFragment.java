package com.example.book_android.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.book_android.APIService;
import com.example.book_android.R;
import com.example.book_android.adapters.HomeAdapter;
import com.example.book_android.adapters.PaymentHistoryAdapter;
import com.example.book_android.models.Book;
import com.example.book_android.models.Receipt;
import com.example.book_android.models.Token;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PaymentHistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PaymentHistoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView rcvHistory;
    private List<Receipt> receipts;
    PaymentHistoryAdapter historyAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    public PaymentHistoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PaymentHistoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PaymentHistoryFragment newInstance(String param1, String param2) {
        PaymentHistoryFragment fragment = new PaymentHistoryFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment_history, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rcvHistory = (RecyclerView) getView().findViewById(R.id.rcvHistory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        rcvHistory.setLayoutManager(linearLayoutManager);

        receipts = new ArrayList<>();
        getAllReceipt();
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshHistory);
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void getAllReceipt() {
        APIService.apiService.getAllReceipt(Token.accessToken).enqueue(new Callback<List<Receipt>>() {
            @Override
            public void onResponse(Call<List<Receipt>> call, Response<List<Receipt>> response) {
                receipts = response.body();
                historyAdapter = new PaymentHistoryAdapter(response.body());
                rcvHistory.setAdapter(historyAdapter);
            }

            @Override
            public void onFailure(Call<List<Receipt>> call, Throwable t) {
                Toast.makeText(PaymentHistoryFragment.this.getContext(), "Có lỗi nhá", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onRefresh() {
        getAllReceipt();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }
}