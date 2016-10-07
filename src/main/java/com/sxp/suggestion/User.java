package com.sxp.suggestion;

import java.math.BigDecimal;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/10/3.
 */
public class  User{
    private  Integer id;
    private String name;
    private BigDecimal money;

    public User() {

    }

    public User(Integer id, String name, BigDecimal money) {
        this.id = id;
        this.name = name;
        this.money = money;
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

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", money=" + money +
                '}';
    }
}
