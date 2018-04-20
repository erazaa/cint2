package com.buscaloaltoq.buscaloaltoq.others;

import com.buscaloaltoq.buscaloaltoq.entities.Shops;

import java.util.ArrayList;

/**
 * Created by rz on 15/04/2018.
 */

public class GlobalVariables {
    private static ArrayList<Shops> carritoComprasArrayList;
    private static boolean cookieUsuario;
    private static Integer cantidadProductos;
    private static String correoUsuario;
    private static String nombresUsuario;
    private static Integer codigoUsuario;
    private static String url="http://192.168.1.38/PlazaVeaBDRemota/";
    private static String urlimagen=url+"Imagenes/";
    private static boolean cookieMenuEditar;
    private static Double total=0.0;

    public static String getUrlimagen() {
        return urlimagen;
    }

    public static void setUrlimagen(String urlimagen) {
        GlobalVariables.urlimagen = urlimagen;
    }

    public static Double getTotal() {
        return total;
    }

    public static void setTotal(Double total) {
        GlobalVariables.total = total;
    }

    public static boolean isCookieMenuEditar() {
        return cookieMenuEditar;
    }

    public static void setCookieMenuEditar(boolean cookieMenuEditar) {
        GlobalVariables.cookieMenuEditar = cookieMenuEditar;
    }

    public static ArrayList<Shops> getCarritoComprasArrayList() {
        return carritoComprasArrayList;
    }

    public static void setCarritoComprasArrayList(ArrayList<Shops> carritoComprasArrayList) {
        GlobalVariables.carritoComprasArrayList = carritoComprasArrayList;
    }

    public static boolean isCookieUsuario() {
        return cookieUsuario;
    }

    public static void setCookieUsuario(boolean cookieUsuario) {
        GlobalVariables.cookieUsuario = cookieUsuario;
    }

    public static Integer getCantidadProductos() {
        return cantidadProductos;
    }

    public static void setCantidadProductos(Integer cantidadProductos) {
        GlobalVariables.cantidadProductos = cantidadProductos;
    }

    public static String getCorreoUsuario() {
        return correoUsuario;
    }

    public static void setCorreoUsuario(String correoUsuario) {
        GlobalVariables.correoUsuario = correoUsuario;
    }

    public static String getNombresUsuario() {
        return nombresUsuario;
    }

    public static void setNombresUsuario(String nombresUsuario) {
        GlobalVariables.nombresUsuario = nombresUsuario;
    }

    public static Integer getCodigoUsuario() {
        return codigoUsuario;
    }

    public static void setCodigoUsuario(Integer codigoUsuario) {
        GlobalVariables.codigoUsuario = codigoUsuario;
    }

    public static String getUrl() {
        return url;
    }

    public static void setUrl(String url) {
        GlobalVariables.url = url;
    }
}
