package entities;

import javax.persistence.*;

@Entity
@Table(name = "ville", schema = "medical", catalog = "")
public class VilleEntity {
    private int id;
    private String pays;
    private String nom;
    private String codePostal;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pays", nullable = false, length = 150)
    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    @Basic
    @Column(name = "nom", nullable = false, length = 200)
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Basic
    @Column(name = "code_postal", nullable = false, length = 150)
    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VilleEntity that = (VilleEntity) o;

        if (id != that.id) return false;
        if (pays != null ? !pays.equals(that.pays) : that.pays != null) return false;
        if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;
        if (codePostal != null ? !codePostal.equals(that.codePostal) : that.codePostal != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (pays != null ? pays.hashCode() : 0);
        result = 31 * result + (nom != null ? nom.hashCode() : 0);
        result = 31 * result + (codePostal != null ? codePostal.hashCode() : 0);
        return result;
    }
}
