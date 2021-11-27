package models;

import java.util.Date;

public class Clients {
    private Integer id;
    private String name;
    private Date clientDateCreation;

    public Clients(){}

    public Clients(String name) {
        this.name = name;
    }

    public Clients(Integer id , String name, Date clientDateCreation){
        this.id = id;
        this.name = name;
        this.clientDateCreation = clientDateCreation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getClientDateCreation() {
        return clientDateCreation;
    }

    public void setClientDateCreation(Date clientDateCreation) {
        this.clientDateCreation = clientDateCreation;
    }

    @Override
    public String toString() {
        return "Clients{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", clientDateCreation=" + clientDateCreation +
                '}';
    }
}
