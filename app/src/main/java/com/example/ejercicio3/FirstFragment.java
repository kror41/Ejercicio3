package com.example.ejercicio3;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.ejercicio3.db.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Objects;

import static android.provider.MediaStore.Images.Media.getBitmap;

public class FirstFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvName = view.findViewById(R.id.tvMemberName);
        TextView tvMatricula = view.findViewById(R.id.tvMatricula);
        TextView tvDirection = view.findViewById(R.id.tvDirection);
        TextView tvExpresion = view.findViewById(R.id.tvExpresion);
        ImageView imageView =  view.findViewById(R.id.imageViewMemberDetail);

        final Bundle bundle = this.getArguments();
        assert bundle != null;
        tvName.setText(bundle.getString("name"));
        tvMatricula.setText(bundle.getString("matricula"));
        tvDirection.setText(bundle.getString("direction"));
        tvExpresion.setText(bundle.getString("expresion"));

        if(bundle.getString("image") != null){
            Uri uri = Uri.parse(bundle.getString("image"));
            try {
                Bitmap bitmap = getBitmap(Objects.requireNonNull(getActivity()).getContentResolver(),uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            imageView.setImageResource(R.mipmap.ic_launcher);
        }


        view.findViewById(R.id.btnDeleteMember).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper databaseHelper = new DatabaseHelper(view.getContext());
                if(databaseHelper.deleteOne(bundle.getString("name"))){
                    Snackbar.make(view, "Borrado exitoso", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    NavHostFragment.findNavController(FirstFragment.this)
                            .navigate(R.id.action_FirstFragment_to_FrameList);
                }else {

                    Snackbar.make(view, "Se ha presentado un error con la base de datos, el miembro no fue borrado", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }


            }
        });
    }
}
