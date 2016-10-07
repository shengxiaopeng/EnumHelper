package com.sxp.enumTest;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/10/6.
 */
public enum Credit {

    CREDIT_GOOD("good",11),
    CREDIT_BAD("bad",12);

    private Credit(String name,Integer type){
        this.name=name;
        this.type=type;
    }

    private String name;
    private Integer type;

    public String getName() {
        return name;
    }


    public Integer getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
