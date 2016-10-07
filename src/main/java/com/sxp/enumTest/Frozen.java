package com.sxp.enumTest;

/**
 * ${DESCRIPTION}
 *
 * @author sxp
 * @create 2016/10/6.
 */
public enum  Frozen {


    FROZEN_GOOD("yes",21),
    FROZEN_BAD("no",22);

    private Frozen(String name,Integer type){
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
        return "Frozen{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
