package org.bluebridge.profile.dibysetsenior.inject;


/**
 * set方式注入专题之注入   特殊符号 < > ' " &
 */
public class MathInjectSpecialsymbolsBySetter {

    //小于表达式
    private String expressionLt;
    //大于表达式
    private String expressionGt;

    public void setExpressionLt(String expressionLt) {
        this.expressionLt = expressionLt;
    }

    public void setExpressionGt(String expressionGt) {
        this.expressionGt = expressionGt;
    }

    @Override
    public String toString() {
        return "MathInjectSpecialsymbolsBySetter{" +
                "expressionLt='" + expressionLt + '\'' +
                ", expressionGt='" + expressionGt + '\'' +
                '}';
    }
}
