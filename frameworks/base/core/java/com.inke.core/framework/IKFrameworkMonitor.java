package com.inke.core.framework;

import com.inke.core.component.Component;

public interface IKFrameworkMonitor {

    /**
     * 组件安装完成
     */
    void installSuccess();

    /**
     * 组件attachBaseContext耗时
     *
     * @param component
     * @param cost
     */
    void attachBaseContext(Component component, long cost);

    /**
     * 组件beforeAppOnCreate耗时
     *
     * @param component
     * @param cost
     */
    void beforeAppOnCreate(Component component, long cost);

    /**
     * 组件afterAppOnCreate耗时
     *
     * @param component
     * @param cost
     */
    void afterAppOnCreate(Component component, long cost);

    /**
     * 所有组件加载完成回调
     */
    void finishLoadComponents();
}
