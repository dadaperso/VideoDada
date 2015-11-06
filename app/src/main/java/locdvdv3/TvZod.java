package locdvdv3;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dada on 04/11/2015.
 */
public class TvZod implements Serializable
{
    private String tagLine;
    private int  id, saison, episode, year;
    private Date releaseDate, createDate, modifyDate;
    private Mapper mapper;
    private Tvshow tvshow;

    public String getTagLine() {
        return tagLine;
    }

    public void setTagLine(String tagLine) {
        this.tagLine = tagLine;
    }

    public int getSaison() {
        return saison;
    }

    public void setSaison(int saison) {
        this.saison = saison;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public Mapper getMapper() {
        return mapper;
    }

    public void setMapper(Mapper mapper) {
        this.mapper = mapper;
    }

    public Tvshow getTvshow() {
        return tvshow;
    }

    public void setTvshow(Tvshow tvshow) {
        this.tvshow = tvshow;
    }
}
