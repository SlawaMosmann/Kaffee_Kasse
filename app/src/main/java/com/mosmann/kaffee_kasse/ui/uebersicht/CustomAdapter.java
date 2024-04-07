package com.mosmann.kaffee_kasse.ui.uebersicht;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mosmann.kaffee_kasse.DatabaseHelper;
import com.mosmann.kaffee_kasse.R;

import java.math.BigDecimal;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<AusgabenData> {
    private final Context context;
    private final List<AusgabenData> ausgabenListe;
    private final LayoutInflater inflater;
    private FloatingActionButton fabDelete;
    private DatabaseHelper databaseHelper;

    public CustomAdapter(Context context, List<AusgabenData> ausgabenListe) {
        super(context, R.layout.list_item, ausgabenListe);
        this.context = context;
        this.ausgabenListe = ausgabenListe;
        this.inflater = LayoutInflater.from(context);
        this.databaseHelper = new DatabaseHelper(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.list_item, parent, false);

        ImageView imageView = rowView.findViewById(R.id.image);
        TextView datumView = rowView.findViewById(R.id.datum);
        TextView artView = rowView.findViewById(R.id.art);
        TextView mengeView = rowView.findViewById(R.id.menge);
        TextView gesamtpreisView = rowView.findViewById(R.id.gesamtpreis);
        TextView kommentarView = rowView.findViewById(R.id.kommentar);
        TextView x = rowView.findViewById(R.id.x);

        AusgabenData ausgabenData = ausgabenListe.get(position);

        // Setzen Sie hier Ihr Bild basierend auf dem Wert von gesamtpreis
        if (ausgabenData.getGesamtbetrag().compareTo(BigDecimal.ZERO) > 0) {
            imageView.setImageResource(R.drawable.plus2);
        } else {
            imageView.setImageResource(R.drawable.minus2);
        }

        datumView.setText(ausgabenData.getDatum());
        artView.setText(ausgabenData.getArt());

        // Überprüfen Sie, ob gesamtpreis positiv ist, um menge anzuzeigen oder auszublenden
        if (ausgabenData.getGesamtbetrag().compareTo(BigDecimal.ZERO) > 0) {
            mengeView.setVisibility(View.GONE);
            x.setVisibility(View.GONE);
        } else {
            mengeView.setVisibility(View.VISIBLE);
            x.setVisibility(View.VISIBLE);
            mengeView.setText(String.valueOf(ausgabenData.getMenge()));
        }

        gesamtpreisView.setText(String.valueOf(ausgabenData.getGesamtbetrag()));
        kommentarView.setText(ausgabenData.getKommentar());

        FloatingActionButton fabDelete = rowView.findViewById(R.id.floatingActionButton3);
        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Hier rufst du die Methode zum Löschen des Eintrags auf
                showDeleteConfirmationDialog(ausgabenData);
            }
        });

        return rowView;
    }

    private void showDeleteConfirmationDialog(AusgabenData ausgabenData) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Möchten Sie diesen Eintrag wirklich löschen?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Hier rufst du die Methode zum Löschen des Eintrags auf
                        if (ausgabenData.getGesamtbetrag().compareTo(BigDecimal.ZERO) > 0) {
                            Log.d("DeleteEntry", "Deleting Einnahmen Entry with ID: " + ausgabenData.getId());
                            deleteEinnahmenEntry(ausgabenData.getId());
                            databaseHelper.updateKontostand(ausgabenData.getGesamtbetrag().multiply(BigDecimal.valueOf(-1)));

                        } else {
                            Log.d("DeleteEntry", "Deleting Ausgaben Entry with ID: " + ausgabenData.getId());
                            deleteAusgabenEntry(ausgabenData.getId());
                            databaseHelper.updateKontostand(ausgabenData.getGesamtbetrag().multiply(BigDecimal.valueOf(-1)));
                        }
                        switch (ausgabenData.getArt()) {
                            case "Kaffeesorte 1": {
                                int menge = ausgabenData.getMenge();
                                databaseHelper.updateKaffee1Menge(menge * -1);
                                break;
                            }
                            case "Kaffeesorte 2": {
                                int menge = ausgabenData.getMenge();
                                databaseHelper.updateKaffee2Menge(menge * -1);
                                break;
                            }
                            case "Milchpulver": {
                                int menge = ausgabenData.getMenge();
                                databaseHelper.updateMilchpulverMenge(menge * -1);
                                break;
                            }
                            default: {
                                Log.d("Debug", "Unbekannte Art: " + ausgabenData.getArt());
                                break;
                            }
                        }
                    }
                })
                .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Benutzer hat Nein geklickt, schließe den Dialog
                        dialog.dismiss();
                    }
                });

        // Erstelle und zeige den Dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteEinnahmenEntry(long entryId) {
        // Hier rufst du die Methode in deinem DatabaseHelper auf, um den Einnahmen-Eintrag zu löschen
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);
        DatabaseHelper.deleteEinnahmenEntry(entryId);
        int position = findPositionById(entryId);
        if (position != -1) {
            ausgabenListe.remove(position);
            notifyDataSetChanged();
        }
    }

    private void deleteAusgabenEntry(long entryId) {
        // Hier rufst du die Methode in deinem DatabaseHelper auf, um den Ausgaben-Eintrag zu löschen
        DatabaseHelper DatabaseHelper = new DatabaseHelper(context);
        DatabaseHelper.deleteAusgabenEntry(entryId);
        int position = findPositionById(entryId);
        if (position != -1) {
            ausgabenListe.remove(position);
            notifyDataSetChanged();
        }
    }

    // Hilfsmethode, um die Position eines Eintrags in der Liste zu finden
    private int findPositionById(long entryId) {
        for (int i = 0; i < ausgabenListe.size(); i++) {
            if (ausgabenListe.get(i).getId() == entryId) {
                return i;
            }
        }
        return -1; // Wenn der Eintrag nicht gefunden wurde
    }

}