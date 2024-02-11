package com.mosmann.kaffee_kasse.ui.home;

public class VariablenData {

    private long id;
    private double kontostand;
    private int bestandKaffeesorte1;
    private int bestandKaffeesorte2;
    private int bestandMilchpulver;

    // Konstruktor
    public VariablenData() {
        // Standardkonstruktor
    }

    // Getter und Setter für ID
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getter und Setter für Kontostand
    public double getKontostand() {
        return kontostand;
    }

    public void setKontostand(double kontostand) {
        this.kontostand = kontostand;
    }

    // Getter und Setter für Bestand der ersten Kaffeesorte
    public int getBestandKaffeesorte1() {
        return bestandKaffeesorte1;
    }

    public void setBestandKaffeesorte1(int bestandKaffeesorte1) {
        this.bestandKaffeesorte1 = bestandKaffeesorte1;
    }

    // Getter und Setter für Bestand der zweiten Kaffeesorte
    public int getBestandKaffeesorte2() {
        return bestandKaffeesorte2;
    }

    public void setBestandKaffeesorte2(int bestandKaffeesorte2) {
        this.bestandKaffeesorte2 = bestandKaffeesorte2;
    }

    // Getter und Setter für Bestand des Milchpulvers
    public int getBestandMilchpulver() {
        return bestandMilchpulver;
    }

    public void setBestandMilchpulver(int bestandMilchpulver) {
        this.bestandMilchpulver = bestandMilchpulver;
    }
}

