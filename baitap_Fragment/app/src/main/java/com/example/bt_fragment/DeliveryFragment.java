package com.example.bt_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bt_fragment.databinding.FragmentDeliveryBinding;

public class DeliveryFragment extends Fragment {
    FragmentDeliveryBinding binding;
    public DeliveryFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        binding = FragmentDeliveryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
