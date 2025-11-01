package org.bluebridge.listener;


import org.flowable.engine.delegate.TaskListener;
import org.flowable.task.service.delegate.DelegateTask;

public class MyTaskListener implements TaskListener {
    /**
     * 监听器触发的方法
     * @param delegateTask
     */
    @Override
    public void notify(DelegateTask delegateTask) {
        if("创建请假流程".equals(delegateTask.getName())
            &&"create".equals(delegateTask.getEventName())) {
            //满足触发的条件的事件，那么我们就来设置assignee
            delegateTask.setAssignee("zhangsan");
            System.out.println("MyTaskListener触发了......" + delegateTask.getName());
        }else {
            delegateTask.setAssignee("lisi");
        }

    }
}
