/**
 * Created by abhijeet anand on 8/8/2016.
 */
package com.example.jsr268client;
public class items {
    private int id;
    private String name;
    private Double cpi;

    public items(Integer id,String name,double cpi)
    {
        this.id=id;
        this.name=name;
        this.cpi=cpi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCpi() {
        return cpi;
    }

    public void setCpi(double cpi) {
        this.cpi = cpi;
    }

    public int getid()
    {
        return this.id;
    }

}
