package org.bluebridge.circulardependence.injectbyset.prototype;


public class Wife {

    private String name;
    private Husband husband;

    /**
     * 这个方法没有实际意义，只是为了在toString()方法中使用时不会引发递归问题
     * @return
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHusband(Husband husband) {
        this.husband = husband;
    }

    @Override
    public String toString() {
        return "Wife{" +
                "name='" + name + '\'' +
                ", husband=" + husband.getName() +
                '}';
    }
}
