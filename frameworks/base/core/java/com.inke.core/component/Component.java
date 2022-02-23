package com.inke.core.component;

import android.app.Application;
import android.content.Context;


public interface Component {

    /**
     * 应用进程初始化
     *
     * @param base
     */
    default void attachBaseContext(Context base) {
    }

    /**
     * 应用进程初始化(afterAppCreate)之前，attachBaseContext之后调用
     *
     * @param app
     */
    default void beforeAppCreate(Application app) {
    }

    /**
     * 应用进程初始化
     *
     * @param app
     */
    default void afterAppCreate(Application app) {
    }

    /**
     * 应用进入后台
     */
    default void onAppBackground() {
    }

    /**
     * 应用回到前台
     */
    default void onAppForeground() {
    }

    /**
     * 扩展用-组件间事件广播,例如权限变化，登录状态变化等等可以通过这里来通知每个组件
     */
    default void onEvent(String event, Object... params) {
    }

    /**
     * 应用第一个页面创建，该方法整个生命周期只会调用一次，主要用来做一些特殊业务初始化，例如依赖页面的一些功能-分享等
     */
    default void onFirstActivityCreate() {
    }

    /**
     * 设置组件加载优先级，数值越低优先级越低.
     * <p>
     * 该方法只有使用{@link com.inke.core.framework.IKFramework#install(Context, String, boolean)}, 并且sortWithComponentPriority=true，才生效
     */
    default short getPriority() {
        return 0;
    }
}
