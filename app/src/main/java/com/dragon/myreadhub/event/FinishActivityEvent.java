package com.dragon.myreadhub.event;

public class FinishActivityEvent extends MessageEvent
{
    private Class activityClass;

    public Class getActivityClass()
    {
        return activityClass;
    }

    public void setActivityClass(Class activityClass)
    {
        this.activityClass = activityClass;
    }
}
