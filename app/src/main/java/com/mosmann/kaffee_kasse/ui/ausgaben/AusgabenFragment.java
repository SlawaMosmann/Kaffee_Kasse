package com.mosmann.kaffee_kasse.ui.ausgaben;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.mosmann.kaffee_kasse.R;
import com.mosmann.kaffee_kasse.DatabaseHelper;
import com.mosmann.kaffee_kasse.ui.DecimalDigitsInputFilter;
import com.mosmann.kaffee_kasse.ui.uebersicht.AusgabenData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AusgabenFragment extends Fragment {

    private EditText editTextNumber;
    private EditText editTextNumberDecimal;
    private EditText editTextText;
    private DatabaseHelper databaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ausgaben, container, false);

        editTextNumber = root.findViewById(R.id.et_menge_eingeben);
        editTextNumberDecimal = root.findViewById(R.id.et_gesamtpreis_eingeben);
        editTextText = root.findViewById(R.id.et_kommentar1);
        Button btnBestaetigen = root.findViewById(R.id.btn_bestaetigen);
        Button btnFelderLeeren = root.findViewById(R.id.btn_felder_leeren);
        databaseHelper = new DatabaseHelper(requireContext());

        editTextNumberDecimal.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});

        // OnClickListener für "Bestätigen"-Button
        btnBestaetigen.setOnClickListener(v -> {
            String mengeStr = editTextNumber.getText().toString().trim();
            String gesamtPreisStr = editTextNumberDecimal.getText().toString().trim();
            String kommentar = editTextText.getText().toString().trim();

            if (!mengeStr.isEmpty() && !gesamtPreisStr.isEmpty()) {
                int menge = Integer.parseInt(mengeStr);
                double gesamtpreis = Double.parseDouble(gesamtPreisStr);

                // Get the current date in dd.MM.yy format
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
                String currentDate = sdf.format(new Date());

                AusgabenData ausgabenData = new AusgabenData();
                ausgabenData.setDatum(currentDate);
                Spinner spinnerArt = root.findViewById(R.id.spinner1);
                String selectedArt = spinnerArt.getSelectedItem().toString();
                ausgabenData.setArt(selectedArt);
                ausgabenData.setMenge(menge);

                // check "gesamtpreis" > 0, Betrag für die Rechnung und Datenbank negativ machen
                if (gesamtpreis > 0) {
                    gesamtpreis = -Math.abs(gesamtpreis);
                }

                ausgabenData.setGesamtbetrag(gesamtpreis);

                ausgabenData.setKommentar(kommentar);

                // Call the insertAusgabe method with the AusgabenData object
                long newRowId = databaseHelper.insertAusgabe(ausgabenData);

                if (newRowId != -1) {
                    Toast.makeText(requireContext(), "Daten erfolgreich hinzugefügt. ID: " + newRowId, Toast.LENGTH_SHORT).show();
                    databaseHelper.updateKontostand(gesamtpreis);
                    // Update-Funktionen für die Menge der drei verschiedenen Möglichkeiten
                    switch (selectedArt) {
                        case "Kaffeesorte 1":
                            databaseHelper.updateKaffee1Menge(menge);
                            break;
                        case "Kaffeesorte 2":
                            databaseHelper.updateKaffee2Menge(menge);
                            break;
                        case "Milchpulver":
                            databaseHelper.updateMilchpulverMenge(menge);
                            break;
                    }
                } else {
                    Toast.makeText(requireContext(), "Fehler beim Hinzufügen der Daten", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Bitte füllen Sie Menge und Gesamtpreis aus", Toast.LENGTH_SHORT).show();
            }
        });

        // OnClickListener für "Felder leeren"-Button
        btnFelderLeeren.setOnClickListener(v -> {
            // alle Felder leeren
            editTextNumber.setText("");
            editTextNumberDecimal.setText("");
            editTextText.setText("");
        });

        return root;
    }
}
