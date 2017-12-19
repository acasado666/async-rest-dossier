package com.dat.webservice.dossier;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;
import java.util.HashMap;

@JsonPropertyOrder({"nameDossier", "creationYearDossier"})
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Dossier {

    private String nameDossier;
    private String idDossier;
    private String countryDossier;
    private String description;
    private Date creationYearDossier;
    private HashMap<String,Object> extras = new HashMap<String,Object>();

    public String getNameDossier() {
        return nameDossier;
    }

    public void setNameDossier(String nameDossier) {
        this.nameDossier = nameDossier;
    }

    public String getIdDossier() {
        return idDossier;
    }

    public void setIdDossier(String idDossier) {
        this.idDossier = idDossier;
    }

    public String getCountryDossier() {
        return countryDossier;
    }

    public void setCountryDossier(String countryDossier) {
        this.countryDossier = countryDossier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationYearDossier() {
        return creationYearDossier;
    }

    public void setCreationYearDossier(Date creationYearDossier) {
        this.creationYearDossier = creationYearDossier;
    }

    @JsonAnyGetter
    public HashMap<String, Object> getExtras() {
        return extras;
    }

    @JsonAnySetter
    public void set(String key, Object value) {
        this.extras.put(key, value);
    }
}
