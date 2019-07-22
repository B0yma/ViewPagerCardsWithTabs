package com.boyma.viewpagercards.viewpagercardsfeature.view.listfragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.boyma.viewpagercards.viewpagercardsfeature.MainActivityViewModel;
import com.boyma.viewpagercards.R;
import com.boyma.viewpagercards.viewpagercardsfeature.domain.models.Person;
import com.github.nitrico.lastadapter.LastAdapter;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private static List<Person> data;
    private ListViewModel mViewModel;
    private RecyclerView reclist;
    private MainActivityViewModel model;

    public static ListFragment newInstance(List<Person> al) {
        data = new ArrayList<>();
        return new ListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.list_fragment, container, false);

        initUI(v);

        return v;

    }

    private void initUI(View v) {
        //v.findViewById(R.id.emtText);
        reclist = v.findViewById(R.id.reclist);
        reclist.setLayoutManager(new LinearLayoutManager(getContext()));
        new LastAdapter(data)
                .map(Person.class, R.layout.personitem)
                .into(reclist);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ListViewModel.class);
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
