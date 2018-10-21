package com.boyma.viewpagercards;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Pair;

public class MainActivityViewModel extends ViewModel {

    private final MutableLiveData<Integer> refreshing = new MutableLiveData<>();

    public void setRefreshing(Integer integer){
        refreshing.postValue(integer);
    }

    public MutableLiveData<Integer> isRefreshing(){
        return refreshing;
    }


}
