package org.bluebridge.designpattern.adapter_a;
import	java.util.ArrayList;
import java.util.List;

/**
 * @author lingwh
 * @desc
 * @date   2019/4/15 8:52
 */
public class DispatcherServlet {
    private List<HandlerAdapter> handlerAdapters = new ArrayList < HandlerAdapter>();

    public DispatcherServlet() {
        handlerAdapters.add(new SimpleHandlerAdapter());
        handlerAdapters.add(new HttpHandlerAdapter());
        handlerAdapters.add(new AnnotationHandlerAdapter());
    }

    public void doDispatch(){
        //此处模拟SpringMVC从request取handler的对象，仅仅new出，可以出，
        //不论实现何种Controller，适配器总能经过适配以后得到想要的结果
//      HttpController controller = new HttpController();
//      AnnotationController controller = new AnnotationController();
        SimpleController controller = new SimpleController();
        //得到对应适配器
        HandlerAdapter adapter = this.getHandler(controller);
        //通过适配器执行对应的controller对应方法
        adapter.handle(controller);

    }

    public HandlerAdapter getHandler(Controller controller){
        for(HandlerAdapter adapter: this.handlerAdapters){
            if(adapter.supports(controller)){
                return adapter;
            }
        }
        return null;
    }
}
