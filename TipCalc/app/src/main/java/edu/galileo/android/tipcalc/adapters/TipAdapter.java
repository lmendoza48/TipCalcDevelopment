package edu.galileo.android.tipcalc.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.textservice.TextInfo;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import edu.galileo.android.tipcalc.R;
import edu.galileo.android.tipcalc.models.TipRecordsModel;

/**
 * Created by Alejandro on 20/4/2017.
 */
public class TipAdapter extends RecyclerView.Adapter<TipAdapter.ViewHolder>{

    private Context context;
    private List<TipRecordsModel> dataRecordSet;
    private OnItemClickListener onItemClickListener;

    public TipAdapter(Context context,List<TipRecordsModel> dataRecordSet,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataRecordSet = dataRecordSet;
        this.onItemClickListener = onItemClickListener;
    }

    public TipAdapter(Context context,OnItemClickListener onItemClickListener) {
        this.context = context;
        this.dataRecordSet = new ArrayList<TipRecordsModel>();
        this.onItemClickListener = onItemClickListener;
    }

    /*este metodo me va a permitir crear el objecto que recibe un view y esta asociado a la clase fragments*/
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new ViewHolder(view);
    }
    /*---------------------------------------------------------------------------------------------------*/

    /* este metodo me va a permitir poner el contenido*/
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TipRecordsModel elementData = dataRecordSet.get(position);
        String positionString = String.format(context.getString(R.string.global_message_tip),elementData.getTip()); // aqui le estoy dando el formato al string q voy a mostrar

        holder.txtContext.setText(positionString);
        holder.txtDataTime.setText(elementData.getDatteFormated());
        holder.setOnItemClicklistener(elementData, onItemClickListener); // manejo del click
    }

    /*metodo para el tamaño de mi lista ------------------------------------*/
    @Override
    public int getItemCount() {
        return dataRecordSet.size();
    }

    /**----------------------------------------------------------------*/

    /*estos dos metodos me van a permitir borrar y agregar un nuevo elemento a mi lista */
    public void add(TipRecordsModel model){
        dataRecordSet.add(0,model);
        notifyDataSetChanged();
    }
    public  void clear(){
        dataRecordSet.clear();
        notifyDataSetChanged();
    }
    /*---------------------------------------------------------------------------------*/

    public static class ViewHolder extends RecyclerView.ViewHolder {
        /* todos los elementos que quiero mostrar en la vista estan xml item_row*/
       @Bind(R.id.txtContext)
        TextView txtContext;
        @Bind(R.id.txtDataTime)
        TextView txtDataTime;
        /*-----------------------------------------------------------------------*/
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        /*metodo para el manejo del click los elemento de este metodo tienen q sr final para usarlo dentro del otro metodo*/
        public void setOnItemClicklistener(final TipRecordsModel elementData, final OnItemClickListener onItemClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(elementData);
                }
            });
        }
    }
}
