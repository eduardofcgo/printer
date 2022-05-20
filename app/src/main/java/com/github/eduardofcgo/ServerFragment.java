package com.github.eduardofcgo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.github.eduardofcgo.databinding.FragmentServerBinding;

import java.io.IOException;

public class ServerFragment extends Fragment {

    private FragmentServerBinding binding;

    private MainActivity activity;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        this.activity = (MainActivity) getActivity();
        binding = FragmentServerBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(v -> {
            if (activity.server.isRunning()) stopServer();
            else startServer();
        });
    }

    private void startServer() {
        try {
            binding.buttonFirst.setText(R.string.starting);

            activity.server.start();

            binding.buttonFirst.setText(R.string.stop);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopServer() {
        binding.buttonFirst.setText(R.string.stopping);

        activity.server.stop();

        binding.buttonFirst.setText(R.string.start);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        stopServer();

        binding = null;
    }

}