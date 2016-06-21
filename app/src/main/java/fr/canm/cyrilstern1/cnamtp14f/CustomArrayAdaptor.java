package fr.canm.cyrilstern1.cnamtp14f;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by cyrilstern1 on 19/06/2016.
 */

public class CustomArrayAdaptor extends ArrayAdapter<RowItem>{
    private final static String BUTTON_DELETE = "delteButton";
    private static final int DELETE_B = 70;
    ArrayList<RowItem> resource1;
    public CustomArrayAdaptor(Context context, ArrayList<RowItem> resource) {
        super(context, 0, resource);
        resource1 = new ArrayList<>(resource);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        RowItem ri = getItem(position);
        if (view == null) {
           // view = LayoutInflater.from(getContext())
             //       .inflate(R.layout.layoutrow, null, false);
        }
        TextView textView = new TextView(getContext());
        textView.setText(ri.getCourseRow());
        LinearLayout linearLayout = new LinearLayout(getContext());
        Button but = new Button(getContext());
        but.setText("X");
        but.setWidth(60);
        but.setTag(position);
        but.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer index = (Integer) v.getTag();
                        resource1.remove(index.intValue());
                        notifyDataSetChanged();
                    }
                }
        );
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundColor(Color.GRAY);
        linearLayout.addView(textView);
        linearLayout.addView(but);
        return linearLayout;
    }


}
