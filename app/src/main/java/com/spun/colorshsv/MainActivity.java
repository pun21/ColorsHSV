package com.spun.colorshsv;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private static int MAX_HUE = 360;
    private static int MIN_HUE = 0;
    private static int MAX_SATURATION = 1;
    private static int MIN_SATURATION = 0;
    private static int MAX_VALUE = 1;
    private static int MIN_VALUE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listView = (ListView) findViewById(R.id.list);
        int rainbow[] = new int[] {Color.RED, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA};
        int hsv[][] = new int[][] {{345, MAX_SATURATION, MAX_VALUE}, {330, MAX_SATURATION, MAX_VALUE}, {315, MAX_SATURATION, MAX_VALUE}, {100, MAX_SATURATION, MAX_VALUE}};
        ArrayList<Integer> list = new ArrayList<Integer>();
        ArrayList<HSV> hsvList = new ArrayList<HSV>();
        for (int i = 0; i< hsv.length; i++) {
            HSV color = new HSV(hsv[i][0], hsv[i][1], hsv[i][2]);
            hsvList.add(color);
        }

        for (int i = 0; i < rainbow.length; i++) {
            list.add(rainbow[i]);
        }

        CustomAdapter adapter = new CustomAdapter(this, R.layout.list_item, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getApplicationContext(),
                        "Item has been clicked.", Toast.LENGTH_SHORT).show();
            }
        });

//        FragmentManager fm = getFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        /*The first fragment contains gradients of pure spectral hues with 100% saturation
        /*100% value.
        */

        //CustomFragment huesFragment = new CustomFragment();

        //add fragment
        //fragmentTransaction.commit();
        /*The second fragment displays the same hues at varying saturation levels (100% to 0%)
        /*at a constant 100% value.
         */
    }
    public class CustomAdapter extends ArrayAdapter<Integer> {

        public CustomAdapter(Context context, int viewResourceID, ArrayList<Integer> list) {
            super(context, 0, list);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int color = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            }

            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setBackgroundColor(color);

            return convertView;
        }
    }

//    private void replaceFragment() {
//
//    }

//    public static class CustomFragment extends ListFragment {
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            super.onCreateView(inflater, container, savedInstanceState);
//            View view = inflater.inflate(R.layout.fragment_item_list, container, false);
//
//            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(inflater.getContext(), R.layout.list_item, colors);
//            //GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, new int[] {colorLeft, colorRight});
//            //view.setBackground(drawable);
//
//            float[] hsv = colors.get(position);
//            int color = Color.HSVToColor(hsv);
//
//
//            return view;
//        }
//        @Override
//        public void onListItemClick(ListView listView, View v, int position, long id) {
//            super.onListItemClick(listView, v, position, id);
//
//            //replace with another fragment
//            ((MainActivity)getActivity()).replaceFragment();
//        }
//
//    }

}
