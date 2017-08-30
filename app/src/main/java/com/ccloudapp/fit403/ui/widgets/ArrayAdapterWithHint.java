package com.ccloudapp.fit403.ui.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ccloudapp.fit403.R;

import java.util.List;

/**
 * Created by Amit on 27/8/17.
 */

public class ArrayAdapterWithHint<T> extends ArrayAdapter<T> {
    private Context mContext;

    public ArrayAdapterWithHint(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public ArrayAdapterWithHint(@NonNull Context context, int resource, int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public ArrayAdapterWithHint(@NonNull Context context, int resource, @NonNull T[] objects) {
        super(context, resource, objects);
    }

    public ArrayAdapterWithHint(@NonNull Context context, int resource, int textViewResourceId,
            @NonNull T[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public ArrayAdapterWithHint(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
    }

    public ArrayAdapterWithHint(@NonNull Context context, int resource, int textViewResourceId,
            @NonNull List<T> objects) {
        super(context, resource, textViewResourceId, objects);
    }


    @Override
    public boolean isEnabled(int position) {
        return position != 0;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView,
            @NonNull ViewGroup parent) {
        View view = super.getDropDownView(position, convertView, parent);
        TextView textView = (TextView) view;
        textView.setTextSize((position != 0) ? 16 : 14);
        textView.setTextColor(
                (position != 0) ? ContextCompat.getColor(getContext(), R.color.text_primary_dark)
                        : ContextCompat.getColor(getContext(), R.color.text_secondary_dark));
        return view;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        TextView textView = (TextView) view;
        textView.setTextColor(
                (position != 0) ? ContextCompat.getColor(getContext(), R.color.text_primary_dark)
                        : ContextCompat.getColor(getContext(), R.color.text_secondary_dark));
        return view;
    }
}
