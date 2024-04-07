package com.mosmann.kaffee_kasse.ui.uebersicht;

import java.math.BigDecimal;

public class AusgabenData {
    private long id;
    private String datum;
    private String art;
    private int menge;
    private BigDecimal gesamtbetrag;
    private String kommentar;

    //constructors
    public AusgabenData(long id, String datum, String art, int menge, BigDecimal gesamtbetrag, String kommentar) {
        this.id = id;
        this.datum = datum;
        this.art = art;
        this.menge = menge;
        this.gesamtbetrag = gesamtbetrag;
        this.kommentar = kommentar;
    }

    public AusgabenData() {
    }

    // toString is necessary for printing the contents of a class object
    public String toString() {
        return "AusgabenData{" +
                "id=" + id +
                ", datum=" + datum +
                ", art=" + art +
                ", menge=" + menge +
                ", gesamtbetrag=" + gesamtbetrag +
                ", kommentar=" + kommentar +
                '}';

    }

    // Getter und Setter für jede Datenfeld
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public int getMenge() {
        return menge;
    }

    public void setMenge(int menge) {
        this.menge = menge;
    }

    public BigDecimal getGesamtbetrag() {
        return gesamtbetrag;
    }

    public void setGesamtbetrag(BigDecimal gesamtbetrag) {
        this.gesamtbetrag = gesamtbetrag;
    }

    public String getKommentar() {
        return kommentar;
    }

    public void setKommentar(String kommentar) {
        this.kommentar = kommentar;
    }
}