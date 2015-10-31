package locdvdv3;

import java.io.Serializable;

/**
 * Created by damien.lejart on 12/05/2015.
 */
public class Film implements Serializable {
    private String srtCat = "";
    private String srtTitre = "";
    private String strReal = "";
    private int iImg = 0;

    public String getSrtCat() {
        return srtCat;
    }

    public void setSrtCat(String srtCat) {
        this.srtCat = srtCat;
    }

    public String getSrtTitre() {
        return srtTitre;
    }

    public void setSrtTitre(String srtTitre) {
        this.srtTitre = srtTitre;
    }

    public String getStrReal() {
        return strReal;
    }

    public void setStrReal(String strReal) {
        this.strReal = strReal;
    }

    public int getiImg() {
        return iImg;
    }

    public void setiImg(int iImg) {
        this.iImg = iImg;
    }

    @Override
    public String toString() {
        return this.getSrtTitre();
    }
}
