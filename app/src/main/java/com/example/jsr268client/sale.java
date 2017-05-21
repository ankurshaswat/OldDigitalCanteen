package com.example.jsr268client;

/**
 * Created by abhijeet anand on 8/11/2016.
 */
public class sale {
    private String name;
    private Double cpi;
    private Integer quan;

    public sale(String name,Double cpi,Integer quan)
    {
        this.name=name;
        this.cpi=cpi;
        this.quan=quan;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCpi() {
        return cpi;
    }

    public void setCpi(Double cpi) {
        this.cpi = cpi;
    }

    public Integer getQuan() {
        return quan;
    }

    public void setQuan(Integer quan) {
        this.quan = quan;
    }
    public void incr(Integer q)
    {
        this.quan+=q;
    }
}
