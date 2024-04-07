package com.mosmann.kaffee_kasse.ui.home;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.mosmann.kaffee_kasse.DatabaseHelper;
import com.mosmann.kaffee_kasse.R;

import java.math.BigDecimal;

public class HomeFragment extends Fragment {

    TextView tv_kontostand;
    TextView tv_kaffee1;
    TextView tv_kaffee2;
    TextView tv_milk;
    ImageView kaffee1, kaffee2, milk;
    private DatabaseHelper databaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        tv_kontostand = root.findViewById(R.id.tv_kontostand);
        tv_kaffee1 = root.findViewById(R.id.tv_kaffee1);
        tv_kaffee2 = root.findViewById(R.id.tv_kaffee2);
        tv_milk = root.findViewById(R.id.tv_milk);

        databaseHelper = new DatabaseHelper(requireContext());
        BigDecimal aktuellerKontostand = databaseHelper.getAktuellerKontostandBigDecimal();
        tv_kontostand.setText(aktuellerKontostand + "€");
        // Falls der Kontostand positiv ist, grüne Farbe, ansonsten rote Farbe
        int textColor = aktuellerKontostand.compareTo(BigDecimal.ZERO) >= 0 ? ContextCompat.getColor(requireContext(), android.R.color.holo_green_dark) : ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark);
        tv_kontostand.setTextColor(textColor);

        int kaffee1Anzahl = databaseHelper.getBestandKaffee1();
        int kaffee2Anzahl = databaseHelper.getBestandKaffee2();
        int milkAnzahl = databaseHelper.getBestandMilchpulver();

        tv_kaffee1.setText(String.valueOf(kaffee1Anzahl));
        tv_kaffee2.setText(String.valueOf(kaffee2Anzahl));
        tv_milk.setText(String.valueOf(milkAnzahl));

        kaffee1 = root.findViewById(R.id.iv_kaffee1);
        kaffee1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                int kaffee1Anzahl = Integer.parseInt(tv_kaffee1.getText().toString());
                if (kaffee1Anzahl > 0) {

                    // Hier wird eine Berechnung durchgeführt, beispielsweise eine Abnahme um 1
                    kaffee1Anzahl = -1;

                    // Hier wird der Bestand aktualisiert
                    databaseHelper.updateKaffee1Menge(kaffee1Anzahl);

                    // Daten aus der Datenbank erneut abrufen
                    int updatedKaffee1Anzahl = databaseHelper.getBestandKaffee1();

                    // TextView mit den aktualisierten Werten aktualisieren
                    tv_kaffee1.setText(String.valueOf(updatedKaffee1Anzahl));

                    v.animate()
                            .scaleX(0f)
                            .scaleY(0f)
                            .alpha(0f)
                            .setDuration(1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    v.setVisibility(View.GONE);
                                    v.setScaleX(1f);
                                    v.setScaleY(1f);
                                    v.setAlpha(1f);
                                    v.setVisibility(View.VISIBLE);
                                }

                            });

                    MediaPlayer mediaPlayer = MediaPlayer.create(v.getContext(), R.raw.blup);
                    mediaPlayer.start();

                } else {
                    Toast.makeText(requireContext(), "Bestand 0, bitte einkaufen!", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        kaffee2 = root.findViewById(R.id.iv_kaffee2);
        kaffee2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                int kaffee1Anzahl = Integer.parseInt(tv_kaffee2.getText().toString());
                if (kaffee1Anzahl > 0) {

                    // Hier wird eine Berechnung durchgeführt, beispielsweise eine Abnahme um 1
                    kaffee1Anzahl = -1;

                    // Hier wird der Bestand aktualisiert
                    databaseHelper.updateKaffee2Menge(kaffee1Anzahl);

                    // Daten aus der Datenbank erneut abrufen
                    int updatedKaffee2Anzahl = databaseHelper.getBestandKaffee2();

                    // TextView mit den aktualisierten Werten aktualisieren
                    tv_kaffee2.setText(String.valueOf(updatedKaffee2Anzahl));

                    v.animate()
                            .scaleX(0f)
                            .scaleY(0f)
                            .alpha(0f)
                            .setDuration(1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    v.setVisibility(View.GONE);
                                    v.setScaleX(1f);
                                    v.setScaleY(1f);
                                    v.setAlpha(1f);
                                    v.setVisibility(View.VISIBLE);
                                }

                            });

                    MediaPlayer mediaPlayer = MediaPlayer.create(v.getContext(), R.raw.blup);
                    mediaPlayer.start();

                } else {
                    Toast.makeText(requireContext(), "Bestand 0, bitte einkaufen!", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        milk = root.findViewById(R.id.iv_milk);
        milk.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                int kaffee1Anzahl = Integer.parseInt(tv_milk.getText().toString());
                if (kaffee1Anzahl > 0) {

                    // Hier wird eine Berechnung durchgeführt, beispielsweise eine Abnahme um 1
                    kaffee1Anzahl = -1;

                    // Hier wird der Bestand aktualisiert
                    databaseHelper.updateMilchpulverMenge(kaffee1Anzahl);

                    // Daten aus der Datenbank erneut abrufen
                    int updatedMilchpulverAnzahl = databaseHelper.getBestandMilchpulver();

                    // TextView mit den aktualisierten Werten aktualisieren
                    tv_milk.setText(String.valueOf(updatedMilchpulverAnzahl));

                    v.animate()
                            .scaleX(0f)
                            .scaleY(0f)
                            .alpha(0f)
                            .setDuration(1000)
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);
                                    v.setVisibility(View.GONE);
                                    v.setScaleX(1f);
                                    v.setScaleY(1f);
                                    v.setAlpha(1f);
                                    v.setVisibility(View.VISIBLE);
                                }

                            });

                    MediaPlayer mediaPlayer = MediaPlayer.create(v.getContext(), R.raw.blup);
                    mediaPlayer.start();

                } else {
                    Toast.makeText(requireContext(), "Bestand 0, bitte einkaufen!", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
        });

        return root;
    }
}
