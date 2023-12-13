package org.bluebridge.circulardependence.injectbyset.singletonandprototype;

public class Husband {

    private String name;
    private Wife wife;

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

    public void setWife(Wife wife) {
        this.wife = wife;
    }

    @Override
    public String toString() {
        return "Husband{" +
                "name='" + name + '\'' +
                ", wife=" + wife.getName() +
                '}';
    }
}
