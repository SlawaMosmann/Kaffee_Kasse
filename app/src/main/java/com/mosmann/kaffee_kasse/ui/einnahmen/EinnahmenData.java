package com.mosmann.kaffee_kasse.ui.einnahmen;

import java.math.BigDecimal;

public class EinnahmenData {

    private long id;
    private String datum;
    private String art;
    private BigDecimal gesamtbetrag;
    private String kommentar;

    public EinnahmenData() {
        // Default constructor
    }

    public EinnahmenData(long id, String datum, String art, BigDecimal gesamtbetrag, String kommentar) {
        this.id = id;
        this.datum = datum;
        this.art = art;
        this.gesamtbetrag = gesamtbetrag;
        this.kommentar = kommentar;
    }

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
