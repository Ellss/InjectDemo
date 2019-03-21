package com.wudh.study.injectmanager;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.wudh.study.injectmanager.annotation.BindView;
import com.wudh.study.injectmanager.annotation.OnClick;
import com.wudh.study.injectmanager.annotation.SetContentView;
import com.wudh.study.injectmanager.proxy.ClickInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by wudh on 2019/3/21.
 **/
public class InjectManager {

    public static void inject(Activity activity){

        //注入布局
        injectLayout(activity);
        //注入控件
        injectView(activity);
        //注入事件
        injectEvent(activity);
    }

    private static void injectLayout(Activity activity) {
        try {
            Class<? extends Activity> clazz = activity.getClass();
            SetContentView setContentView=clazz.getAnnotation(SetContentView.class);
            if (setContentView!=null) {
                int layoutId = setContentView.value();
                Method method = clazz.getMethod("setContentView", int.class);
                method.invoke(activity, layoutId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void injectView(Activity activity) {

        try {
            Class<? extends Activity> clazz = activity.getClass();
            //getDeclaredMethod*()获取的是类自身声明的所有方法，包含public、protected和private方法。
            //getMethod*()获取的是类的所有共有方法，这就包括自身的所有public方法，和从基类继承的、从接口实现的所有public方法。
            Field[] fields = clazz.getDeclaredFields();
            for (Field field:fields){
                BindView bindView = field.getAnnotation(BindView.class);
                if (bindView!=null) {
                    int layout = bindView.value();
                    Method findViewById = clazz.getMethod("findViewById", int.class);
                    Object view= findViewById.invoke(activity, layout);
                    //private私有属性 必须setAccessible后才能set
                    field.setAccessible(true);
                    field.set(activity,view);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static void injectEvent(Activity activity) {
        try {
            Class<? extends Activity> clazz = activity.getClass();
            Method[] methods = clazz.getMethods();
            for (Method method:methods){
                OnClick annotation = method.getAnnotation(OnClick.class);
                if (annotation!=null){
                    int[] ids = annotation.value();
                    Object proxy = Proxy.newProxyInstance(View.OnClickListener.class.getClassLoader(),
                            new Class[]{View.OnClickListener.class},new ClickInvocationHandler(activity,method));
                    Method findViewById = clazz.getMethod("findViewById", int.class);
                    for (int id:ids){
                        Object view = findViewById.invoke(activity, id);
                        Method setOnClickListener = view.getClass().getMethod("setOnClickListener", View.OnClickListener.class);
                        //到proxy时进行方法拦截 进入proxy的invoke中
                        setOnClickListener.invoke(view,proxy);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
