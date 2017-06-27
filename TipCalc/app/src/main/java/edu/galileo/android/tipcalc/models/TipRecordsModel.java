package edu.galileo.android.tipcalc.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alejandro on 2/4/2017.
 */
public class TipRecordsModel {
    /* esta clase es un modal la cual me va a permitir
     * recuperar los datos para mostrar en el listado
     * */
    private double bill;
    private int tipPocentage;
    private Date timeStamp;

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getTipPocentage() {
        return tipPocentage;
    }

    public void setTipPocentage(int tipPocentage) {
        this.tipPocentage = tipPocentage;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }
/* devuelve el porcentaje de propina*/
    public double getTip(){
        return bill*(tipPocentage/100d);
    }
/*-------------------------------------------*/
    public String getDatteFormated(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM dd,yyyy HH:mm");
        return simpleDateFormat.format(timeStamp);
    }


}
