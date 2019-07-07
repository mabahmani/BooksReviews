package ir.mab.booksreviews.history;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.mab.booksreviews.R;
import ir.mab.booksreviews.utils.SharedPref;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    private RecyclerView recyclerView;
    private BarcodeListAdapter barcodeListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BarcodeList barcodeList = SharedPref.getInstance(getContext()).getBarcodeList();

        recyclerView = view.findViewById(R.id.barcode_list);
        barcodeListAdapter = new BarcodeListAdapter(barcodeList.getBarcodeList(),getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(barcodeListAdapter);

    }
}
