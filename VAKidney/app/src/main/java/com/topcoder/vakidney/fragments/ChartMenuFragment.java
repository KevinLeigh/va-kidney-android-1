package com.topcoder.vakidney.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.topcoder.vakidney.adapter.ChartMenuAdapter;
import com.topcoder.vakidney.databinding.FragmentChartMenuBinding;
import com.topcoder.vakidney.model.UserData;
import com.topcoder.vakidney.R;
import com.topcoder.vakidney.constant.ChartType;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * This class is used to show all the chart type related to the disease category
 */
public class ChartMenuFragment extends Fragment {

    private ChartMenuAdapter mAdapter;
    private FragmentChartMenuBinding binder;

    public ChartMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binder = DataBindingUtil.inflate(inflater, R.layout.fragment_chart_menu, container, false);
        final View view = binder.getRoot();


        UserData userData = UserData.get();

        binder.rvChartMenu.setLayoutManager(
                new LinearLayoutManager(
                        getActivity(),
                        LinearLayoutManager.VERTICAL,
                        false));

        List<Long> chartTypes = ChartType.getChartsByCategory(
                userData.getDiseaseCategory(),
                userData.isDialysis()
        );

        mAdapter = new ChartMenuAdapter(binder.rvChartMenu, chartTypes);
        binder.rvChartMenu.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
