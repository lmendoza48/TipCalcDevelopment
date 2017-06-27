package edu.galileo.android.tipcalc.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.tipcalc.R;

/*clase para el mandejo de otra venta
* con informacion de los datos guardados
* */
public class TipDetailActivity extends AppCompatActivity {

    @Bind(R.id.txtBillTotal)
    TextView txtBillTotal;
    @Bind(R.id.txtTip)
    TextView txtTip;
    @Bind(R.id.txtDate)
    TextView txtDate;

    public final static String TIP_KEY="tip";
    public final static String DATA_KEY="timestamp";
    public final static String BILL_TOTAL_KEY="total";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_detail);
        ButterKnife.bind(this);

        /* con esta instacia lo q hago es atrapar los valores q le estoy enviando para mostrarlo por pantalla*/
        Intent intent = getIntent();
        String strBillTip = String.format(getString(R.string.tipdetail_message_tip), intent.getDoubleExtra(BILL_TOTAL_KEY,0d));
        String strTotal = String.format(getString(R.string.global_message_tip), intent.getDoubleExtra(TIP_KEY,0d));

        txtBillTotal.setText(strBillTip);
        txtTip.setText(strTotal);
        txtDate.setText(intent.getStringExtra(DATA_KEY));
        /*--------------------------------------------------------------------------------------------------*/

    }

}
