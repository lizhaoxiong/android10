package com.inke.core.framework;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.os.Bundle;

import com.inke.core.component.Component;

import java.util.Iterator;
import java.util.List;

public class IKInstrumentation extends Instrumentation {

    private Instrumentation mOriginInstrumentation;
    private int mForegroundCount = 0;
    private Application mApplication;
    private boolean firstActivityCreate = false;
    private IKFrameworkMonitor mFrameworkMonitor;

    public IKInstrumentation(Instrumentation instrumentation, Application application) {
        mOriginInstrumentation = instrumentation;
        mApplication = application;
    }

    @Override
    public void callApplicationOnCreate(Application app) {
        mFrameworkMonitor = IKFramework.getInstance().getFrameworkMonitor();
        IKFramework.getInstance().setContext(mApplication);
        List<Component> componentList = IKFramework.getInstance().getComponents();
        if (componentList != null && componentList.size() > 0) {
            for (Component component : componentList) {
                long beforeAppOnCreateStartTime = System.currentTimeMillis();
                component.beforeAppCreate(mApplication);
                if (mFrameworkMonitor != null) {
                    mFrameworkMonitor.beforeAppOnCreate(component,
                            (System.currentTimeMillis() - beforeAppOnCreateStartTime));
                }
            }
        }
        mOriginInstrumentation.callApplicationOnCreate(app);
        if (componentList != null && componentList.size() > 0) {
            for (Component component : componentList) {
                long afterAppOnCreateStartTime = System.currentTimeMillis();
                component.afterAppCreate(mApplication);
                if (mFrameworkMonitor != null) {
                    mFrameworkMonitor.afterAppOnCreate(component,
                            (System.currentTimeMillis() - afterAppOnCreateStartTime));
                }
            }
            addMonitor();
        }
        if (mFrameworkMonitor != null) {
            mFrameworkMonitor.finishLoadComponents();
        }
    }

    /**
     * 内部增加前后台监控器
     */
    private void addMonitor() {
        mApplication.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                if (!firstActivityCreate) {
                    firstActivityCreate = true;
                    List<Component> componentList = IKFramework.getInstance().getComponents();
                    if (componentList == null) return;
                    Iterator<Component> iterator = componentList.iterator();
                    while (iterator.hasNext()) {
                        Component component = iterator.next();
                        component.onFirstActivityCreate();
                    }
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (mForegroundCount <= 0) {
                    List<Component> componentList = IKFramework.getInstance().getComponents();
                    if (componentList == null) return;
                    Iterator<Component> iterator = componentList.iterator();
                    while (iterator.hasNext()) {
                        Component component = iterator.next();
                        component.onAppForeground();
                    }
                }
                mForegroundCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                IKFramework.getInstance().pushCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                IKFramework.getInstance().popCurrentActivity(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {
                mForegroundCount--;
                if (mForegroundCount <= 0) {
                    List<Component> componentList = IKFramework.getInstance().getComponents();
                    if (componentList == null) return;
                    Iterator<Component> iterator = componentList.iterator();
                    while (iterator.hasNext()) {
                        Component component = iterator.next();
                        component.onAppBackground();
                    }
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }
}
