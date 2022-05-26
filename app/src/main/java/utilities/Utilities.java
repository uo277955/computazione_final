package utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import logic.Ticket;

public abstract class Utilities{

    public static void changeView(Context context, Class cla){
        Intent intent = new Intent(context, cla);
        context.startActivity(intent);
    }

    public static View.OnClickListener back(Activity a){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.onBackPressed();
            }
        };
    }

}
