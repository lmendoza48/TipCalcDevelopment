package edu.galileo.android.tipcalc.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.tipcalc.R;
import edu.galileo.android.tipcalc.activities.TipDetailActivity;
import edu.galileo.android.tipcalc.adapters.OnItemClickListener;
import edu.galileo.android.tipcalc.adapters.TipAdapter;
import edu.galileo.android.tipcalc.models.TipRecordsModel;

/**
 * A simple {@link Fragment} subclass.
 * clase que me permite mostrar una lista
 */
public class TipHistoryListFragment extends Fragment implements TipHistoryListFragmentListener, OnItemClickListener{
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;

    private TipAdapter adapter;


    public TipHistoryListFragment() {
        // Required empty public constructor
    }
    //aqui voy a colocar la lista que voy a mostrar en mi app con este fragment hago la interfaz que me permite mostrar la lista
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tip_history_list, container, false);
        ButterKnife.bind(this,view);
        initAdapter(); // con este metodo inicializo el objeto para que reciba los primero parametros
        initRecyclerView(); // con este metodo le set los valores del contenido del objeto
        return view;
    }

    private void initAdapter() {
        if (adapter == null){
            adapter = new TipAdapter(getActivity().getApplicationContext(), this);
        }
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    /*este metodo es implementado a traves de la interfaz para tomar una accion de listar y hacer call backs entre app*
    * es decir este metodo es la forma comunicar la actividad con el fragmento a travez de la interfaz y es el metodo que me muestra
    * los mensajes
    */
    /*
    * devuelve el valor de un mensaje para mostrar en la vista */
    @Override
    public void action(String str) {
        Toast.makeText(getActivity(), str , Toast.LENGTH_SHORT).show();
    }
    /**************************************************************************************************************///
    @Override
    public void addToList(TipRecordsModel record) {
        adapter.add(record);
    }

    @Override
    public void cleardList() {
        adapter.clear();
    }

    /*clase que me devuelve en otra actividad es decir en otra venta los valores que le paso por intent
    q es la clase q me permite hacer el llamado a otra venta */
    @Override
    public void onItemClick(TipRecordsModel tipRecordsModel) {
        /* entonces aqui instancio la clase donde va estar mi actividad para poder mostrarla en otra venta */
        Intent intent = new Intent(getActivity(), TipDetailActivity.class);
        intent.putExtra(TipDetailActivity.TIP_KEY,tipRecordsModel.getTip() );
        intent.putExtra(TipDetailActivity.BILL_TOTAL_KEY, tipRecordsModel.getBill());
        intent.putExtra(TipDetailActivity.DATA_KEY, tipRecordsModel.getDatteFormated());

        startActivity(intent);

        //Toast.makeText(getActivity(), tipRecordsModel.getDatteFormated(), Toast.LENGTH_SHORT).show();
    }
}
