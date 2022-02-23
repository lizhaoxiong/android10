package com.inke.core.framework;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.inke.core.component.Component;
import com.inke.core.service.ServiceFactory;
import com.inke.core.utils.ReflectUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

public final class IKFramework {
    private static final IKFramework sInstance = new IKFramework();

    public static IKFramework getInstance() {
        return sInstance;
    }

    private IKFramework() {
    }

    private List<Component> mComponents = new ArrayList<>();
    private Context mContext;
    private volatile Map<Class<?>, ServiceFactory<?>> mServices = new ConcurrentHashMap<>();
    private volatile Map<String, String> mRoutePaths = new ConcurrentHashMap<>();
    private Boolean mIsMainProcess = null;
    private Boolean mIsDebuggable = null;

    private Stack<WeakReference<Activity>> mCurrentActivity = new Stack<>();

    private com.inke.core.framework.IKFrameworkMonitor mFrameworkMonitor;

    /**
     * 组件化框架初始化，在Application attachBaseContext时调用，自动安装框架内所有组件，通过插桩安装组件
     *
     * @param context Context
     */
    public void install(Context context) {
        throw new IKFrameworkException("You can not call this method manually");
    }

    /**
     * 组件化框架初始化，在Application attachBaseContext时调用，自动安装框架内所有组件，需要显式指定配置路径
     *
     * @param context    Context
     * @param assetsPath Config Path
     */
    public void install(Context context, String assetsPath) {
        install(context, assetsPath, false);
    }

    /**
     * 组件化框架初始化，在Application attachBaseContext时调用，自动安装框架内所有组件，需要显式指定配置路径
     *
     * @param context                   Context
     * @param assetsPath                Config Path
     * @param sortWithComponentPriority false: 组件加载优先级由assetsPath中的jsonArray顺序决定
     *                                  true: 组件加载优先级由Component实现类中的{@link Component#getPriority()}决定
     */
    public void install(Context context, String assetsPath, boolean sortWithComponentPriority) throws IKFrameworkException {
        try (InputStream is = context.getAssets().open(assetsPath)) {
            BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            List<Component> components = new ArrayList<>();
            String line, result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            JSONArray jsonArray = new JSONArray(result);
            JSONObject jsonObject;
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String className = jsonObject.getString("clazz");
                Class<?> cls = Class.forName(className);
                Component obj = (Component) cls.newInstance();
                components.add(obj);
            }
            if (sortWithComponentPriority) {
                Collections.sort(components, (o1, o2) -> o2.getPriority() - o1.getPriority());
            }
            install(context, components);
        } catch (IOException e) {
            throw new IKFrameworkException("IOException", e);
        } catch (ClassNotFoundException e1) {
            throw new IKFrameworkException("ClassNotFoundException", e1);
        } catch (IllegalAccessException e2) {
            throw new IKFrameworkException("IllegalAccessException", e2);
        } catch (InstantiationException e3) {
            throw new IKFrameworkException("InstantiationException", e3);
        } catch (JSONException e4) {
            throw new IKFrameworkException("JSONException", e4);
        }

    }

    /**
     * 组件化框架初始化，在Application attachBaseContext时调用，自动安装框架内所有组件，需要显式指定组件列表
     *
     * @param context    Context
     * @param components Config List
     */
    public void install(Context context, List<Component> components) {
        setContext(context);
        if (components == null || components.size() < 1) {
            return;
        }
        try {
            Instrumentation baseInstrumentation = ReflectUtil.getInstrumentation(mContext);
            final IKInstrumentation instrumentation = new IKInstrumentation(baseInstrumentation,
                    (Application) mContext);
            Object activityThread = ReflectUtil.getActivityThread();
            ReflectUtil.setInstrumentation(activityThread, instrumentation);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mComponents.addAll(components);
        if (mFrameworkMonitor != null) {
            mFrameworkMonitor.installSuccess();
        }
        for (Component component : components) {
            long attachBaseContextStartTime = System.currentTimeMillis();
            component.attachBaseContext(mContext);
            if (mFrameworkMonitor != null) {
                mFrameworkMonitor.attachBaseContext(component,
                        (System.currentTimeMillis() - attachBaseContextStartTime));
            }
        }
    }

    public IKFrameworkMonitor getFrameworkMonitor() {
        return mFrameworkMonitor;
    }

    public void setFrameworkMonitor(IKFrameworkMonitor mFrameworkMonitor) {
        this.mFrameworkMonitor = mFrameworkMonitor;
    }

    /**
     * 获取组件列表
     *
     * @return
     */
    public List<Component> getComponents() {
        return mComponents;
    }

    /**
     * 组件一对多事件广播
     *
     * @param event  事件名，通用事件名定义在框架事件常量池，业务事件由业务维护
     * @param params 自定义参数名，业务广播事件变长参数
     */
    public void sendEvent(String event, Object... params) {
        Iterator<Component> iterator = mComponents.iterator();
        while (iterator.hasNext()) {
            Component component = iterator.next();
            component.onEvent(event, params);
        }
    }

    /**
     * 全局设置AppContext
     *
     * @param mContext
     */
    protected void setContext(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 全局获取应用Context，如果不初始化，Context可能为空
     *
     * @return Application Context
     */
    public Context getAppContext() {
        return mContext;
    }

    /**
     * 判断应用是否Debug包
     *
     * @return
     */
    public boolean isDebuggable() {
        if (mIsDebuggable == null) {
            if (mContext != null) {
                ApplicationInfo applicationInfo = mContext.getApplicationInfo();
                if (applicationInfo != null) {
                    mIsDebuggable = (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
                }
            }
        }
        return mIsDebuggable;
    }

    /**
     * 判断当前进程是否主进程
     *
     * @param context ApplicationContext
     * @return true:Main Process  false:Not Main Process
     */
    public boolean isMainProcess(Context context) {
        if (mIsMainProcess == null) {
            mIsMainProcess = context.getPackageName().equals(readProcessName(context));
        }
        return mIsMainProcess;
    }

    /**
     * Copy from IngkeeApplication
     *
     * @param context
     * @return
     */
    private String readProcessName(Context context) {
        byte[] cmdlineBuffer = new byte[64];
        FileInputStream stream = null;

        String var5 = null;
        try {
            stream = new FileInputStream("/proc/self/cmdline");
            int n = stream.read(cmdlineBuffer);
            int endIndex = indexOf(cmdlineBuffer, (byte) 0);
            var5 = new String(cmdlineBuffer, 0, endIndex > 0 ? endIndex : n);
        } catch (IOException e) {
            // ignore
        } finally {
            try {
                if (stream != null) {
                    stream.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }

        if (TextUtils.isEmpty(var5)) {
            try {
                int pid = android.os.Process.myPid();
                ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                for (ActivityManager.RunningAppProcessInfo process : manager.getRunningAppProcesses()) {
                    if (process.pid == pid) {
                        var5 = process.processName;
                        break;
                    }
                }
            } catch (Throwable e) {
                // ignore
            }
        }

        return var5;
    }

    /**
     * @param haystack
     * @param needle
     * @return
     */
    private int indexOf(byte[] haystack, byte needle) {
        for (int i = 0; i < haystack.length; ++i) {
            if (haystack[i] == needle) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取应用VersionName
     *
     * @param context ApplicationContext
     * @return 应用VersionName
     */
    public String getVersionName(Context context) {
        String versionName = "";
        try {
            versionName = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_CONFIGURATIONS).versionName;
        } catch (Throwable e) {
            //只要异常就返回空串
        }
        return versionName;
    }

    /**
     * 获取应用VersionCode
     *
     * @param context Application Context
     * @return 应用VersionCode
     */
    public int getVersionCode(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Throwable e) {
            //只要异常就返回0
        }
        return 0;
    }

    /**
     * 获取框架提供的服务
     *
     * @param serviceClass 服务接口类
     * @param <S>          服务接口类
     * @return 真实服务
     */
    //todo,lzx,学完泛型好好看看这里是怎么回事
    public <S> S getService(Class<? extends S> serviceClass) {
        final ServiceFactory<?> factory = mServices.get(serviceClass);
        if (factory == null) return null;
        return ((S) factory.getImpl());
    }

    /**
     * 注册框架服务
     *
     * @param clazz   服务接口类
     * @param factory 服务工厂类
     * @param <S>     服务实现类
     */
    public <S> void registerService(Class<S> clazz, ServiceFactory<? extends S> factory) {
        if (mServices.containsKey(clazz)) {
            throw new IKFrameworkException("clazz has been register with registerService");
        }
        mServices.put(clazz, factory);
    }

    /**
     * 注册路由，默认是Activity路由
     *
     * @param path className 路由路径 及 页面类名
     */
    public void registerRoute(String path, String className) {
        if (mRoutePaths.containsKey(path)) {
            throw new IKFrameworkException("path has been register with registerRoute");
        }
        mRoutePaths.put(path, className);
    }

    /**
     * 通过路由表根据path获取类名，注意要判空！
     *
     * @param path path
     * @return
     */
    public String getClassNameByPath(String path) {
        return mRoutePaths.get(path);
    }

    /**
     * 通过统跳url启动Activity
     *
     * @param context 上下文
     * @param url     统跳url
     */
    public void startActivity(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            Log.e("IKFramework", "url is null!");
            return;
        }
        Uri uri = Uri.parse(url);
        String path = getPathFromUrl(url);
        String className = mRoutePaths.get(path);
        if (TextUtils.isEmpty(className)) {
            Log.e("IKFramework", url + " not register!");
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(context, className);
        intent.setData(uri);
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    /**
     * 通过统跳url启动Activity
     *
     * @param activity    上下文
     * @param url         统跳路径
     * @param requestCode 请求码
     */
    public void startActivityForResult(Activity activity, String url, int requestCode) {
        if (TextUtils.isEmpty(url)) {
            Log.e("IKFramework", "url is null!");
            return;
        }
        Uri uri = Uri.parse(url);
        String path = getPathFromUrl(url);
        String className = mRoutePaths.get(path);
        if (TextUtils.isEmpty(className)) {
            Log.e("IKFramework", url + " not register!");
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(activity, className);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 通过统跳url启动Activity
     *
     * @param fragment    上下文
     * @param url         统跳路径
     * @param requestCode 请求码
     */
    public void startActivityForResult(@NonNull Fragment fragment, String url, int requestCode) {
        if (TextUtils.isEmpty(url)) {
            Log.e("IKFramework", "url is null!");
            return;
        }
        Uri uri = Uri.parse(url);
        String path = getPathFromUrl(url);
        String className = mRoutePaths.get(path);
        if (TextUtils.isEmpty(className)) {
            Log.e("IKFramework", url + " not register!");
            return;
        }
        Intent intent = new Intent();
        intent.setClassName(fragment.getContext(), className);
        intent.setData(uri);
        fragment.startActivityForResult(intent, requestCode);
    }

    private String getPathFromUrl(String url) {
        String key;
        int tmp = url.indexOf('?');
        if (tmp > 0) {
            key = url.substring(0, tmp);
        } else {
            key = url;
        }
        return key;
    }

    /**
     * 创建Activity Intent启动参数
     *
     * @param context 上下文
     * @param path    统跳路径
     * @return 参数Builder
     */
    public Builder build(Context context, String path) {
        return new Builder(context, path);
    }

    public class Builder {
        private Intent intent = new Intent();
        private Context mContext;
        private String mPath;

        public Builder(Context context, String path) {
            this.mContext = context;
            this.mPath = path;
        }

        public Builder putExtra(String key, boolean value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, byte value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, char value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, short value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, int value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, long value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, float value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, double value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, String value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, CharSequence value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, Parcelable[] value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, Parcelable value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putParcelableArrayListExtra(String key, ArrayList<? extends Parcelable> value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putIntegerArrayListExtra(String key, ArrayList<Integer> value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putStringArrayListExtra(String key, ArrayList<String> value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putCharSequenceArrayListExtra(String key, ArrayList<CharSequence> value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, Serializable value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, boolean[] value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, byte[] value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, short[] value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, char[] value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, int[] value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, long[] value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, float[] value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, double[] value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, String[] value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, CharSequence[] value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtra(String key, Bundle value) {
            intent.putExtra(key, value);
            return this;
        }

        public Builder putExtras(Intent extras) {
            intent.putExtras(extras);
            return this;
        }

        public Builder putExtras(Bundle extras) {
            intent.putExtras(extras);
            return this;
        }

        public Builder setData(Uri data) {
            this.intent.setData(data);
            return this;
        }

        public Builder setAction(String action) {
            this.intent.setAction(action);
            return this;
        }

        public Builder addCategory(String category) {
            this.intent.addCategory(category);
            return this;
        }

        public Builder setFlags(int flags) {
            this.intent.setFlags(flags);
            return this;
        }

        public Builder addFlags(int flags) {
            this.intent.addFlags(flags);
            return this;
        }

        /**
         * 根据拼装后的Intent启动Activity
         */
        public void startActivity() {
            String className = mRoutePaths.get(mPath);
            if (TextUtils.isEmpty(className)) {
                Log.e("IKFramework", mPath + " not register!");
                return;
            }
            intent.setClassName(mContext, className);
            if (!(mContext instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            mContext.startActivity(intent);
        }

        /**
         * 根据拼装后的Intent启动Activity
         *
         * @param requestCode 请求码
         */
        public void startActivityForResult(int requestCode) {
            String className = mRoutePaths.get(mPath);
            if (TextUtils.isEmpty(className)) {
                Log.e("IKFramework", mPath + " not register!");
                return;
            }
            intent.setClassName(mContext, className);
            if (mContext instanceof Activity) {
                ((Activity) mContext).startActivityForResult(intent, requestCode);
            }
        }

        /**
         * 根据拼装后的Intent启动Activity
         *
         * @param requestCode 请求码
         */
        public void startActivityForResult(@NonNull Fragment fragment, int requestCode) {
            String className = mRoutePaths.get(mPath);
            if (TextUtils.isEmpty(className)) {
                Log.e("IKFramework", mPath + " not register!");
                return;
            }
            intent.setClassName(mContext, className);
            fragment.startActivityForResult(intent, requestCode);
        }
    }

    protected void pushCurrentActivity(Activity activity) {
        WeakReference<Activity> activityWeakReference = new WeakReference<>(activity);
        mCurrentActivity.push(activityWeakReference);
        if (isDebuggable()) {
            Log.d("IKFramework", "setCurrentActivity " + (activity == null ? "null" : activity.getClass().getName()));
        }
    }

    protected void popCurrentActivity(Activity activity) {
        if (mCurrentActivity.empty() || activity == null) {
            return;
        }
        // 当退出一个ActivityA，快速打开ActivityB时，有时会发生ActivityA->onDestory() 慢于 ActivityB->onCreate()
        // 这样过去用 Classname 判断时，如果ActivityA 与 ActivityB 是同一个类，那么会错误的置空
        // if (TextUtils.equals(currentActivity.getClass().getName(), activity.getClass().getName())) {
        WeakReference<Activity> activityWeakReference = mCurrentActivity.peek();
        if (activityWeakReference != null && activityWeakReference.get() == activity) {
            if (IKFramework.getInstance().isDebuggable()) {
                Log.d("IKFramework", "releaseCurrentActivity " + activity.getClass().getName());
            }
            mCurrentActivity.pop();
        }
    }

    /**
     * 获取当前Activity
     *
     * @return
     */
    public Activity getCurrentActivity() {
        if (mCurrentActivity != null && mCurrentActivity.empty()) {
            return null;
        }
        WeakReference<Activity> activityWeakReference = mCurrentActivity.peek();
        if (activityWeakReference == null) {
            return null;
        }
        return activityWeakReference.get();
    }

}
