package it.axant.divtoview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import it.axant.divtoview.R;

public class ViewBuilder {

    public static View createExampleView(final Context context){
        LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = li.inflate(R.layout.example, null);
        Button button = (Button)view.findViewById(R.id.html_button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"Example action!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

}
