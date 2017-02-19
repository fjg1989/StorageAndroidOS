package com.fg.storage.ui.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fg.storage.R;
import com.fg.storage.ui.batch.BatchListActivity;
import com.fg.storage.ui.product.ProductListActivity;
import com.fg.storage.ui.storecell.StoreListActivity;
import com.fg.storage.ui.suppliers.SupplyListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends Fragment {

    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getActivity();
        View view = inflater.inflate(R.layout.fragment_setting, null, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        ((Button) view.findViewById(R.id.cellid)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, StoreListActivity.class));
            }
        });
        ((Button) view.findViewById(R.id.product)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, ProductListActivity.class));
            }
        });
        ((Button) view.findViewById(R.id.batch)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, BatchListActivity.class));
            }
        });
        ((Button) view.findViewById(R.id.supply)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, SupplyListActivity.class));
            }
        });
    }


}
