package com.fg.storage.ui.home;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.fg.storage.R;
import com.fg.storage.ui.product.StorageActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private Context context;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null, false);
        initView(view);
        context = getActivity();
        return view;
    }

    private void initView(View view) {
        ((Button) view.findViewById(R.id.storage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, StorageActivity.class));
            }
        });
        ((Button) view.findViewById(R.id.record)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "敬请期待", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
