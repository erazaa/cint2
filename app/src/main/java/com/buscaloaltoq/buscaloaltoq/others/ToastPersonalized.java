package com.buscaloaltoq.buscaloaltoq.others;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;

import com.muddzdev.styleabletoastlibrary.StyleableToast;

/**
 * Created by rz on 15/04/2018.
 */

public class ToastPersonalized {
    public void mostrarMensaje (Context context, String mensaje, @DrawableRes int icono){
        StyleableToast styleableToast= new StyleableToast.Builder(context)
                .text(mensaje)
                .textColor(Color.WHITE)
                .backgroundColor(Color.BLUE)
                .cornerRadius(8)
                .spinIcon()
                .icon(icono)
                .build();
        styleableToast.show();
    }
}
