package com.mosmann.kaffee_kasse.ui.einnahmen;

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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EinnahmenFragment extends Fragment {

    private EditText editTextNumberDecimal;
    private EditText editTextText;
    private DatabaseHelper databaseHelper;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_einnahmen, container, false);

        editTextNumberDecimal = root.findViewById(R.id.et_betrag_eingeben);
        editTextText = root.findViewById(R.id.et_kommentar2);
        Button btnBestaetigen = root.findViewById(R.id.btn_bestaetigen2);
        Button btnFelderLeeren = root.findViewById(R.id.btn_felder_leeren2);
        databaseHelper = new DatabaseHelper(requireContext());

        editTextNumberDecimal.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});

        // Setze OnClickListener für den "Bestätigen"-Button
        btnBestaetigen.setOnClickListener(v -> {
            String betragStr = editTextNumberDecimal.getText().toString().trim();
            String kommentar = editTextText.getText().toString().trim();

            if (!betragStr.isEmpty()) {
                BigDecimal betrag = new BigDecimal(betragStr);

                // Runde den Betrag auf 2 Kommastellen
                betrag = betrag.setScale(2, BigDecimal.ROUND_HALF_UP);

                if (betrag.compareTo(BigDecimal.ZERO) > 0) {
                    // Get the current date in dd.MM.yy format
                    SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy", Locale.getDefault());
                    String currentDate = sdf.format(new Date());

                    EinnahmenData einnahmenData = new EinnahmenData();
                    einnahmenData.setDatum(currentDate);
                    Spinner spinnerArt = root.findViewById(R.id.spinner2);
                    String selectedArt = spinnerArt.getSelectedItem().toString();
                    einnahmenData.setArt(selectedArt);
                    einnahmenData.setGesamtbetrag(betrag);
                    einnahmenData.setKommentar(kommentar);

                    // Call the insertEinnahme method with the EinnahmenData object
                    long newRowId = databaseHelper.insertEinnahme(einnahmenData);

                    if (newRowId != -1) {
                        Toast.makeText(requireContext(), "Daten erfolgreich hinzugefügt. ID: " + newRowId, Toast.LENGTH_SHORT).show();
                        databaseHelper.updateKontostand(betrag);
                    } else {
                        Toast.makeText(requireContext(), "Fehler beim Hinzufügen der Daten", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Der Betrag muss größer als 0 sein", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Bitte füllen Sie den Betrag aus", Toast.LENGTH_SHORT).show();
            }
        });



        // Setze OnClickListener für den "Felder leeren"-Button
        btnFelderLeeren.setOnClickListener(v -> {
            // Hier kannst du alle Felder leeren
            editTextNumberDecimal.setText("");
            editTextText.setText("");
        });

        return root;
    }
}
