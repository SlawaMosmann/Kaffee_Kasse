package com.mosmann.kaffee_kasse.ui.uebersicht;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.mosmann.kaffee_kasse.DatabaseHelper;
import com.mosmann.kaffee_kasse.MainActivity;
import com.mosmann.kaffee_kasse.R;

import java.util.List;

public class UebersichtFragment extends Fragment {

    ArrayAdapter customorArrayAdapter;
    private CustomAdapter adapter;
    private List<AusgabenData> ausgabenListe;
    private ListView listView;
    private ListView lv_uebersicht;
    private DatabaseHelper databaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_uebersicht, container, false);

        lv_uebersicht = root.findViewById(R.id.lv_uebersicht);
        databaseHelper = new DatabaseHelper(requireContext());
        ausgabenListe = databaseHelper.getAllData();
        adapter = new CustomAdapter(getActivity(), ausgabenListe);
        lv_uebersicht.setAdapter(adapter);

        return root;
    }

}