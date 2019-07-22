package com.boyma.viewpagercards.viewpagercardsfeature.view.info1fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boyma.viewpagercards.viewpagercardsfeature.MainActivityViewModel;
import com.boyma.viewpagercards.R;

public class Info1Fragment extends Fragment {

    private Info1ViewModel mViewModel;
    private MainActivityViewModel model;

    public static Info1Fragment newInstance() {
        return new Info1Fragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.info1_fragment, container, false);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(Info1ViewModel.class);
        model = ViewModelProviders.of(getActivity()).get(MainActivityViewModel.class);

        initSubs();
    }

    private void initSubs() {
        model.isRefreshing().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer n) {
                if (n != null) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("onFinish");
                            model.setRefreshing(null);
                        }
                    },3000);

                }

            }
        });
    }


}
