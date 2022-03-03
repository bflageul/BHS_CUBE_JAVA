package com.cesi.bhs.api.data;

import java.time.LocalDate;

public class Test {
    private int id;
    private String commentaire;
    private LocalDate date;

    /**
     * Constructeur
     * @param id
     * @param commentaire
     * @param date
     */
    public Test(int id, String commentaire, LocalDate date) {
        this.id = id;
        this.commentaire = commentaire;
        this.date = date;
    }

    /**
     * Recupere id
     *
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Met a jour id
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Recupere commentaire
     *
     * @return commentaire
     */
    public String getCommentaire() {
        return commentaire;
    }

    /**
     * Met a jour commentaire
     *
     * @param commentaire
     */
    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    /**
     * Recupere date
     *
     * @return date
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Met a jour date
     *
     * @param date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }
}
