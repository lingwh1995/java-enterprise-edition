package org.bulebridge.ioc.condition.condition_b.componment;
import	java.util.function.Supplier;

/**
 * @author ronin
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
