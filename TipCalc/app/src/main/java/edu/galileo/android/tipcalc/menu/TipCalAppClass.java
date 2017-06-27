package edu.galileo.android.tipcalc.menu;

import android.app.Application;

/**
 * Created by Alejandro on 19/3/2017.
 */
public class TipCalAppClass extends Application {
    /* esta clase me sirve para obtner la funcion del menu que se creo en la clase MainActivity
    * y es una clase que hereda funcionalidad de la clase application
    * para que funcione en el archivo xml android manifest coloco un android name con el nombre de la clase creada*/

    private final static String ABOUT_URL= "https://accounts.google.com/gmail.com";

    public  String getAboutUrl() {
        return ABOUT_URL;
    }

}
