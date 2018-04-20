package com.buscaloaltoq.buscaloaltoq.others;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.buscaloaltoq.buscaloaltoq.R;

/**
 * Created by rz on 15/04/2018.
 */

public class DialogPersonalized {
    private Dialog dialog;
    public void mostrarDialog(Context context, String nombre){
        // custom dialog
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.item_description);
        ImageView imgItemDescripcion;

        imgItemDescripcion = (ImageView) dialog.findViewById(R.id.imgItemDescripcion);
        //<------Aqui se carga las imagenes
        Glide.with(context)
                .load(GlobalVariables.getUrlimagen() + nombre)
                .into(imgItemDescripcion);
        //------>Fin :v
        // set the custom dialog components - text, image and button
        ImageButton close = (ImageButton) dialog.findViewById(R.id.btnClose);

        // Close Button
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //TODO Close button action
            }
        });

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }
}
