package edu.galileo.android.tipcalc.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import edu.galileo.android.tipcalc.R;
import edu.galileo.android.tipcalc.menu.TipCalAppClass;
import edu.galileo.android.tipcalc.fragments.TipHistoryListFragment;
import edu.galileo.android.tipcalc.fragments.TipHistoryListFragmentListener;
import edu.galileo.android.tipcalc.models.TipRecordsModel;

public class MainActivity extends AppCompatActivity {
// variable del objeto que devuelve la lista para mostrar por pantalla
    private TipHistoryListFragmentListener fragmentListener;
//------- variables para definir la accion del boton de incrementar y decrementar --------
    private static final int VALUE_FOR_INCREMENT_OR_DECREMENT = 1;
    private static final int VALUE_FOR_DEFAULT = 10;
//----------------------------------------------------------------------------------------
    @Bind(R.id.inputBill)
    EditText inputBill;
    @Bind(R.id.btnSubmit)
    Button btnSubmit;
    @Bind(R.id.inputPropina)
    EditText inputPropina;
    @Bind(R.id.btnIncrement)
    Button btnIncrement;
    @Bind(R.id.btnDecrease)
    Button btnDecrease;
    @Bind(R.id.btnClear)
    Button btnClear;
    @Bind(R.id.tctTip)
    TextView tctTip;

    /*onCreate es el unico metodo que se puede sobre escribir para darle funcionalidad a la app
    la clase R es la que me permite hacer el vinculo entre la clase java y el archivo xml donde tengo
    layout de mi app
      */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this); //con esta libreria obtengo con mayor facilidad los datos de las acciones de los input y botones con inyeccion de dependencias

        /*estoy instanciando el fragmento en mi actividad para poder comunicar y lo busco por el id del mismo todo lo de la actividad se crea aqui*/
        TipHistoryListFragment fragment = (TipHistoryListFragment) getSupportFragmentManager().findFragmentById(R.id.FragmentList);
        fragment.setRetainInstance(true); /* esto lo q me permite es q los valores no sea reiniciado cuando se haga cualquier actividad*/

        fragmentListener = fragment;
    }

    /* aqui voy crear el menu de app es decir los tre punto de un menu desplegable para dar una funcionalidad*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu); // dentro de menu.menu_main definier los elemento del menu
        return super.onCreateOptionsMenu(menu);
    }

    /*aqui la funcionalidad dentro del menu es decir si se aprieta cualquierar que este dentro del menu viene aca
    * ejm con el if obtengo el valor del item que cree en el archivo xml y con el tomo una accion ya sea enviarlo a una pagina web o otra cosa*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_about) {
            about();  /*aqui en este metodo obtengo en este caso el url si se selecciono el item con nombre action_about*/
        }
        return super.onOptionsItemSelected(item);
    }
     /*-------- handle of all button ------------------------------------*/
    /*butterknife me permite con una etiqueta obtner el valor de la accion de un btn
    * aqui hago una action cuando le doy al btn de enviar*/
    @OnClick(R.id.btnSubmit)
    public void handleClickSubmit() {
        hideKeyBoard(); // con este metodo oculto el teclado cuando envio la accion de algu boton
        // here get value of the input for the total value
        String valueInputTotal = inputBill.getText().toString().trim(); // el metodo trim me permite quitar el espacio
        /* con el valor obtenido calculo el porcentaje de propina y lo muestro en un mensaje del view layout
        * con la variable tctTip*/
        if (!valueInputTotal.isEmpty()) {
            double totalValue = Double.parseDouble(valueInputTotal);
            int tipPecerntage = getTipPercentage();

            /* carga los datos del modelo que voy a guardar */
            TipRecordsModel recordsModel = new TipRecordsModel();
            recordsModel.setBill(totalValue);
            recordsModel.setTipPocentage(tipPecerntage);
            recordsModel.setTimeStamp(new Date());
            /*-------------------------------------------------*/
           // double totalPropina = totalValue * (tipPecerntage / 100d); el calculo se hace en el objeto TipRecordModel

            String strTip = String.format(getString(R.string.global_message_tip), recordsModel.getTip());
            /*lo que ahora hago es mostrar en la aplicacion una ventana con un mensaje de cuando sea cargado el total de la propina*/
            fragmentListener.action(strTip);
            fragmentListener.addToList(recordsModel);
            // ---------------------------------------------------------------
            // ----------- aqui hago visible el total del calculo por pantalla ---------------
            tctTip.setVisibility(View.VISIBLE);
            tctTip.setText(strTip); // con el setText de butterknife muestro en un input un valor de string
        }
        //Log.e(getLocalClassName(),"Get action btn submit");
    }

    @OnClick(R.id.btnIncrement)
    public void handleClickIncrease() {
        hideKeyBoard();
        handleTipChange(VALUE_FOR_INCREMENT_OR_DECREMENT);

    }

    @OnClick(R.id.btnDecrease)
    public void handleClickDecrease() {
        hideKeyBoard();
        handleTipChange(-VALUE_FOR_INCREMENT_OR_DECREMENT);

    }
    @OnClick(R.id.btnClear)
    public void btnClear(){
        fragmentListener.cleardList();
    }
 /*---------------------------------------------------------------------*/
    /* metodo para el manjeo del porcentaje de propina */
    private void handleTipChange(int valueChange) {
        int tipPecerntage = getTipPercentage();
        tipPecerntage += valueChange;
        if (tipPecerntage > 0 && tipPecerntage < 100) {
            inputPropina.setText(String.valueOf(tipPecerntage));
        } else {
            fragmentListener.action("Se muestra Porcentaje por default no Se puede Incrementar mas");
            inputPropina.setText(String.valueOf(VALUE_FOR_DEFAULT));
        }
    }
    //---------------------------------------------------------------------------------

    public int getTipPercentage() {
        // aqui obengo el valor del porcentaje que se va a multiplicar
        int valuePorcentega = VALUE_FOR_DEFAULT;
        String valueInputPorcentage = inputPropina.getText().toString().trim(); // el metodo trim() me devuelve el valor sin espacio
        if (!valueInputPorcentage.isEmpty()) {
            valuePorcentega = Integer.parseInt(valueInputPorcentage);
        } else {
            inputPropina.setText(String.valueOf(valuePorcentega));
        }

        return valuePorcentega;
    }

    public void hideKeyBoard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        try {
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    inputMethodManager.HIDE_IMPLICIT_ONLY);
        } catch (NullPointerException errorGet) {
            Log.e("Error get Value btn", Log.getStackTraceString(errorGet)); // capturo la trace si hay un nullpointer
        }

    }

    private void about() {
        TipCalAppClass urlOfApp = (TipCalAppClass) getApplication();
        String urlgetApp = urlOfApp.getAboutUrl();
        /*aqui estoy diciendole a andoid que cuando se traiga el string y como es un url lo abra en el navegador */
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlgetApp));
        startActivity(intent);
    }
}
