package com.ursaccharine.nscnotifier;

public class Model
{
    String name,url;
    int nod,nol,nov;

    public Model() {

    }

    public Model(String name, String url, int nod, int nol, int nov) {
        this.name = name;
        this.url = url;
        this.nod = nod;
        this.nol = nol;
        this.nov = nov;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNod() {
        return nod;
    }

    public void setNod(int nod) {
        this.nod = nod;
    }

    public int getNol() {
        return nol;
    }

    public void setNol(int nol) {
        this.nol = nol;
    }

    public int getNov() {
        return nov;
    }

    public void setNov(int nov) {
        this.nov = nov;
    }
}
