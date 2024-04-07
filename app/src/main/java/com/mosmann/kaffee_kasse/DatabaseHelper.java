package com.mosmann.kaffee_kasse;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mosmann.kaffee_kasse.ui.uebersicht.AusgabenData;
import com.mosmann.kaffee_kasse.ui.einnahmen.EinnahmenData;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Der Name der Datenbank
    public static final String DATABASE_NAME = "kaffeekasse_datenbank.db";

    // Die Version der Datenbank
    public static final int DATABASE_VERSION = 1;

    // Die Namen der Tabellen
    public static final String TABLE_AUSGABEN = "ausgaben";
    public static final String TABLE_EINNAHMEN = "einnahmen";
    public static final String TABLE_VARIABLEN = "variablen";

    // Die Spalten der Tabelle ausgaben
    public static final String COLUMN_AUSGABEN_ID = "_id";
    public static final String COLUMN_AUSGABEN_DATUM = "datum";
    public static final String COLUMN_AUSGABEN_ART = "art";
    public static final String COLUMN_AUSGABEN_MENGE = "menge";
    public static final String COLUMN_AUSGABEN_GESAMTBETRAG = "gesamtpreis";
    public static final String COLUMN_AUSGABEN_KOMMENTAR = "kommentar";

    // Die Spalten der Tabelle einnahmen
    public static final String COLUMN_EINNAHMEN_ID = "_id";
    public static final String COLUMN_EINNAHMEN_DATUM = "datum";
    public static final String COLUMN_EINNAHMEN_ART = "art";
    public static final String COLUMN_EINNAHMEN_GESAMTBETRAG = "gesamtbetrag";
    public static final String COLUMN_EINNAHMEN_KOMMENTAR = "kommentar";

    // Die Spalten der Tabelle variablen
    public static final String COLUMN_VARIABLEN_ID = "_id";
    public static final String COLUMN_VARIABLEN_KONTOSTAND = "kontostand";
    public static final String COLUMN_VARIABLEN_BESTAND_KAFFEESORTE_1 = "bestand_kaffeesorte_1";
    public static final String COLUMN_VARIABLEN_BESTAND_KAFFEESORTE_2 = "bestand_kaffeesorte_2";
    public static final String COLUMN_VARIABLEN_BESTAND_MILCHPULVER = "bestand_milchpulver";

    // Der Konstruktor der Klasse
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Die onCreate-Methode, die die Tabellen erstellt
    @Override
    public void onCreate(SQLiteDatabase db) {
    // Der SQL-Befehl, um die Tabelle ausgaben zu erstellen
        String CREATE_TABLE_AUSGABEN = "CREATE TABLE " + TABLE_AUSGABEN + "("
                + COLUMN_AUSGABEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_AUSGABEN_DATUM + " TEXT NOT NULL DEFAULT CURRENT_DATE, "
                + COLUMN_AUSGABEN_ART + " TEXT NOT NULL, "
                + COLUMN_AUSGABEN_MENGE + " INTEGER NOT NULL CHECK (" + COLUMN_AUSGABEN_MENGE + " > 0), "
                + COLUMN_AUSGABEN_GESAMTBETRAG + " REAL NOT NULL, "
                + COLUMN_AUSGABEN_KOMMENTAR + " TEXT"
                + ")";

    // Der SQL-Befehl, um die Tabelle einnahmen zu erstellen
        String CREATE_TABLE_EINNAHMEN = "CREATE TABLE " + TABLE_EINNAHMEN + "("
                + COLUMN_EINNAHMEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EINNAHMEN_DATUM + " TEXT NOT NULL DEFAULT CURRENT_DATE, "
                + COLUMN_EINNAHMEN_ART + " TEXT NOT NULL, "
                + COLUMN_EINNAHMEN_GESAMTBETRAG + " REAL NOT NULL CHECK (" + COLUMN_EINNAHMEN_GESAMTBETRAG + " > 0), "
                + COLUMN_EINNAHMEN_KOMMENTAR + " TEXT"
                + ")";

    // Der SQL-Befehl, um die Tabelle variablen zu erstellen
        String CREATE_TABLE_VARIABLEN = "CREATE TABLE " + TABLE_VARIABLEN + "("
                + COLUMN_VARIABLEN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_VARIABLEN_KONTOSTAND + " REAL NOT NULL DEFAULT 0, "
                + COLUMN_VARIABLEN_BESTAND_KAFFEESORTE_1 + " INTEGER NOT NULL DEFAULT 0, "
                + COLUMN_VARIABLEN_BESTAND_KAFFEESORTE_2 + " INTEGER NOT NULL DEFAULT 0, "
                + COLUMN_VARIABLEN_BESTAND_MILCHPULVER + " INTEGER NOT NULL DEFAULT 0"
                + ")";

    // Füge einen Datensatz mit einem Kontostand von 0 ein, wenn die Tabelle variablen erstellt wird
        String INSERT_INITIAL_KONTOSTAND = "INSERT INTO " + TABLE_VARIABLEN + "(" + COLUMN_VARIABLEN_KONTOSTAND + ") VALUES (0)";

    // Die SQL-Befehle ausführen
        db.execSQL(CREATE_TABLE_AUSGABEN);
        db.execSQL(CREATE_TABLE_EINNAHMEN);
        db.execSQL(CREATE_TABLE_VARIABLEN);
        db.execSQL(INSERT_INITIAL_KONTOSTAND);
    }

    // Die onUpgrade-Methode, die die Tabellen aktualisiert
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // Der SQL-Befehl, um die Tabellen zu löschen, falls sie existieren
        String DROP_TABLE_AUSGABEN = "DROP TABLE IF EXISTS " + TABLE_AUSGABEN;
        String DROP_TABLE_EINNAHMEN = "DROP TABLE IF EXISTS " + TABLE_EINNAHMEN;
        String DROP_TABLE_VARIABLEN = "DROP TABLE IF EXISTS " + TABLE_VARIABLEN;

    // Die SQL-Befehle ausführen
        db.execSQL(DROP_TABLE_AUSGABEN);
        db.execSQL(DROP_TABLE_EINNAHMEN);
        db.execSQL(DROP_TABLE_VARIABLEN);

    // Die Tabellen neu erstellen
        onCreate(db);
    }

    public long insertAusgabe(AusgabenData ausgabenData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AUSGABEN_DATUM, ausgabenData.getDatum());
        values.put(COLUMN_AUSGABEN_ART, ausgabenData.getArt());
        values.put(COLUMN_AUSGABEN_MENGE, ausgabenData.getMenge());
        values.put(COLUMN_AUSGABEN_GESAMTBETRAG, ausgabenData.getGesamtbetrag().toString()); // Konvertierung zu String
        values.put(COLUMN_AUSGABEN_KOMMENTAR, ausgabenData.getKommentar());

        // Füge die Daten in die Tabelle "ausgaben" ein
        long newRowId = db.insert(TABLE_AUSGABEN, null, values);
        db.close();
        return newRowId;
    }

    public long insertEinnahme(EinnahmenData einnahmenData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_EINNAHMEN_DATUM, einnahmenData.getDatum());
        values.put(COLUMN_EINNAHMEN_ART, einnahmenData.getArt());
        values.put(COLUMN_EINNAHMEN_GESAMTBETRAG, einnahmenData.getGesamtbetrag().toString()); // Konvertierung zu String
        values.put(COLUMN_EINNAHMEN_KOMMENTAR, einnahmenData.getKommentar());

        // Füge die Daten in die Tabelle "einnahmen" ein
        long newRowId = db.insert(TABLE_EINNAHMEN, null, values);
        db.close();
        return newRowId;
    }

    public List<AusgabenData> getAllData() {
        List<AusgabenData> returnList = new ArrayList<>();

        // Get Data from the database, combining data from both tables
        String queryString = "SELECT * FROM (" +
                "SELECT "
                + COLUMN_AUSGABEN_ID + ", "
                + COLUMN_AUSGABEN_DATUM + ", "
                + COLUMN_AUSGABEN_ART + ", "
                + COLUMN_AUSGABEN_MENGE + ", "
                + COLUMN_AUSGABEN_GESAMTBETRAG + ", "
                + COLUMN_AUSGABEN_KOMMENTAR +
                " FROM " + TABLE_AUSGABEN +
                " UNION " +
                "SELECT "
                + COLUMN_EINNAHMEN_ID + ", "
                + COLUMN_EINNAHMEN_DATUM + ", "
                + COLUMN_EINNAHMEN_ART + ", "
                + "NULL AS " + COLUMN_AUSGABEN_MENGE + ", "  // Adding a placeholder for menge in einnahmen
                + COLUMN_EINNAHMEN_GESAMTBETRAG + ", "
                + COLUMN_EINNAHMEN_KOMMENTAR +
                " FROM " + TABLE_EINNAHMEN +
                ") ORDER BY " + COLUMN_AUSGABEN_DATUM + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                // Check if the column exists in the cursor
                int idIndex = cursor.getColumnIndex(COLUMN_AUSGABEN_ID);
                int datumIndex = cursor.getColumnIndex(COLUMN_AUSGABEN_DATUM);
                int artIndex = cursor.getColumnIndex(COLUMN_AUSGABEN_ART);
                int mengeIndex = cursor.getColumnIndex(COLUMN_AUSGABEN_MENGE);
                int gesamtbetragIndex = cursor.getColumnIndex(COLUMN_AUSGABEN_GESAMTBETRAG);
                int kommentarIndex = cursor.getColumnIndex(COLUMN_AUSGABEN_KOMMENTAR);

                if (idIndex != -1 && datumIndex != -1 && artIndex != -1 && mengeIndex != -1 &&
                        gesamtbetragIndex != -1 && kommentarIndex != -1) {

                    // Check if the entry is from the ausgaben table
                    if (mengeIndex != -1) {
                        long id = cursor.getLong(idIndex);
                        String datum = cursor.getString(datumIndex);
                        String art = cursor.getString(artIndex);
                        int menge = cursor.getInt(mengeIndex);
                        double gesamtbetrag = cursor.getDouble(gesamtbetragIndex);
                        String kommentar = cursor.getString(kommentarIndex);

                        BigDecimal gesamtbetragBigDecimal = BigDecimal.valueOf(gesamtbetrag); // Umwandlung von double in BigDecimal

                        AusgabenData dataEntry = new AusgabenData(id, datum, art, menge, gesamtbetragBigDecimal, kommentar);
                        returnList.add(dataEntry);
                    } else {
                        // It's from the einnahmen table
                        long id = cursor.getLong(idIndex);
                        String datum = cursor.getString(datumIndex);
                        String art = cursor.getString(artIndex);
                        double gesamtbetrag = cursor.getDouble(gesamtbetragIndex);
                        String kommentar = cursor.getString(kommentarIndex);

                        BigDecimal gesamtbetragBigDecimal = BigDecimal.valueOf(gesamtbetrag); // Umwandlung von double in BigDecimal

                        EinnahmenData einnahmenData = new EinnahmenData(id, datum, art, gesamtbetragBigDecimal, kommentar);
                        // You need to create the EinnahmenData class accordingly
                        //returnList.add(einnahmenData);
                    }
                }
            } while (cursor.moveToNext());
        }

        // Close both the cursor and the db when done.
        cursor.close();
        db.close();
        return returnList;
    }

    public void deleteAusgabenEntry(long entryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_AUSGABEN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(entryId)};

        db.delete(TABLE_AUSGABEN, selection, selectionArgs);
        db.close();
    }

    public void deleteEinnahmenEntry(long entryId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COLUMN_EINNAHMEN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(entryId)};

        db.delete(TABLE_EINNAHMEN, selection, selectionArgs);
        db.close();
    }

    public void updateKontostand(BigDecimal betrag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Aktuellen Kontostand abrufen
        BigDecimal aktuellerKontostand = getAktuellerKontostandBigDecimal();

        // Neuen Kontostand berechnen und aktualisieren
        BigDecimal neuerKontostand = aktuellerKontostand.add(betrag);
        values.put(COLUMN_VARIABLEN_KONTOSTAND, neuerKontostand.toString());

        // Update durchführen
        db.update(TABLE_VARIABLEN, values, null, null);
        db.close();
    }

    public BigDecimal getAktuellerKontostandBigDecimal() {
        SQLiteDatabase db = this.getReadableDatabase();
        BigDecimal kontostand = BigDecimal.ZERO;

        String[] columns = {COLUMN_VARIABLEN_KONTOSTAND};
        Cursor cursor = db.query(TABLE_VARIABLEN, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_VARIABLEN_KONTOSTAND);
            if (columnIndex != -1) {
                kontostand = new BigDecimal(cursor.getString(columnIndex));
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return kontostand;
    }

    public void updateKaffee1Menge(int menge) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Aktuellen Bestand abrufen
        int aktuellerBestand = getBestandKaffee1();

        // Berechnen Sie den neuen Bestand, stellen Sie sicher, dass er nicht unter 0 fällt
        int neuerBestand = Math.max(aktuellerBestand + menge, 0);

        ContentValues values = new ContentValues();
        values.put(COLUMN_VARIABLEN_BESTAND_KAFFEESORTE_1, neuerBestand);

        // Update durchführen
        db.update(TABLE_VARIABLEN, values, null, null);
        db.close();
    }


    public int getBestandKaffee1() {
        SQLiteDatabase db = this.getReadableDatabase();
        int bestandKaffee1 = 0;

        String[] columns = {COLUMN_VARIABLEN_BESTAND_KAFFEESORTE_1};
        Cursor cursor = db.query(TABLE_VARIABLEN, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_VARIABLEN_BESTAND_KAFFEESORTE_1);
            if (columnIndex != -1) {
                bestandKaffee1 = cursor.getInt(columnIndex);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return bestandKaffee1;
    }

    public void updateKaffee2Menge(int menge) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Aktuellen Bestand abrufen
        int aktuellerBestand = getBestandKaffee2();

        // Berechnen Sie den neuen Bestand, stellen Sie sicher, dass er nicht unter 0 fällt
        int neuerBestand = Math.max(aktuellerBestand + menge, 0);

        ContentValues values = new ContentValues();
        values.put(COLUMN_VARIABLEN_BESTAND_KAFFEESORTE_2, neuerBestand);

        // Update durchführen
        db.update(TABLE_VARIABLEN, values, null, null);
        db.close();
    }


    public int getBestandKaffee2() {
        SQLiteDatabase db = this.getReadableDatabase();
        int bestandKaffee2 = 0;

        String[] columns = {COLUMN_VARIABLEN_BESTAND_KAFFEESORTE_2};
        Cursor cursor = db.query(TABLE_VARIABLEN, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_VARIABLEN_BESTAND_KAFFEESORTE_2);
            if (columnIndex != -1) {
                bestandKaffee2 = cursor.getInt(columnIndex);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return bestandKaffee2;
    }

    public void updateMilchpulverMenge(int menge) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Aktuellen Bestand abrufen
        int aktuellerBestand = getBestandMilchpulver();

        // Berechnen Sie den neuen Bestand, stellen Sie sicher, dass er nicht unter 0 fällt
        int neuerBestand = Math.max(aktuellerBestand + menge, 0);

        ContentValues values = new ContentValues();
        values.put(COLUMN_VARIABLEN_BESTAND_MILCHPULVER, neuerBestand);

        // Update durchführen
        db.update(TABLE_VARIABLEN, values, null, null);
        db.close();
    }


    public int getBestandMilchpulver() {
        SQLiteDatabase db = this.getReadableDatabase();
        int bestandMilchpulver = 0;

        String[] columns = {COLUMN_VARIABLEN_BESTAND_MILCHPULVER};
        Cursor cursor = db.query(TABLE_VARIABLEN, columns, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex(COLUMN_VARIABLEN_BESTAND_MILCHPULVER);
            if (columnIndex != -1) {
                bestandMilchpulver = cursor.getInt(columnIndex);
            }
        }

        if (cursor != null) {
            cursor.close();
        }

        return bestandMilchpulver;
    }

}
