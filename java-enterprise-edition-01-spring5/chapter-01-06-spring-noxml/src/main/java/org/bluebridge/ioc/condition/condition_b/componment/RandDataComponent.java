package org.bluebridge.ioc.condition.condition_b.componment;
import	java.util.function.Supplier;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/8 14:22
 */
public class RandDataComponent<T> {
    private Supplier<T> rand;

    public RandDataComponent(Supplier<T> rand){
        this.rand = rand;
    }

    public T rand(){
        return rand.get();
    }
}
