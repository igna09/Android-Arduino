package com.example.backgroundbrightness.homepage.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.backgroundbrightness.R;
import com.example.backgroundbrightness.homepage.fragments.Homepage;
import com.example.backgroundbrightness.homepage.models.HomepageGridButtonModel;

import java.util.ArrayList;

public class HomepageGridButtonAdapter extends ArrayAdapter<HomepageGridButtonModel> {
    Context context;

    public HomepageGridButtonAdapter(@NonNull Context context, ArrayList<HomepageGridButtonModel> courseModelArrayList) {
        super(context, 0, courseModelArrayList);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
        }

        HomepageGridButtonModel courseModel = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.idTVCourse);
        ImageView courseIV = listitemView.findViewById(R.id.idIVcourse);

        courseTV.setText(courseModel.getLabel());
        courseIV.setImageResource(courseModel.getImgid());

        if(courseModel.getClazz() != null) {
            listitemView.setOnClickListener(view -> {
                try {
                    ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction().replace(R.id.main_view, (Fragment) courseModel.getClazz().newInstance()).addToBackStack(null).commit();
                } catch (Exception e) {
                    Log.e("HomepageGridButtonAdapter", e.getMessage());
                }
            });
        }

        return listitemView;
    }
}
