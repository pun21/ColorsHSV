package com.spun.colorshsv;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SummaryFragment extends Fragment{
    private String mText;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        bundle = this.getArguments();

        setText(bundle);

    }
    public void setText(Bundle bundle) {
        mText = bundle.getString("summary");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.summary_item, container, false);
        TextView summary = (TextView) view.findViewById(R.id.summary);
        summary.setText(mText);

        // Inflate the layout for this fragment
        return view;
    }
}
