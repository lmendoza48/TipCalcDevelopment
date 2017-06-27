package edu.galileo.android.tipcalc.fragments;

import edu.galileo.android.tipcalc.models.TipRecordsModel;

/**
 * Created by Alejandro on 23/3/2017.
 */
public interface TipHistoryListFragmentListener {
    void action(String str);
    void addToList(TipRecordsModel record);
    void cleardList();
}
