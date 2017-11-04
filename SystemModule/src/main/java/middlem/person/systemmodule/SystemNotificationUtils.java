package middlem.person.systemmodule;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/***********************************************
 * <P> desc:
 * <P> Author: gongtong
 * <P> Date: 2017/11/3 15:59
 * <P> Copyright  2008 二维火科技
 ***********************************************/

public class SystemNotificationUtils {
    private static final int REQUEST_NOTIFICATION_CODE = 100;

    /**
     *  Notification.FLAG_SHOW_LIGHTS	是否使用呼吸灯提醒
        Notification.FLAG_INSISTENT	持续提醒(声音/振动)直到用户响应(点击/取消)
        Notification.FLAG_ONLY_ALERT_ONCE	提醒(铃声/震动/滚动通知摘要)只执行一次
        Notification.FLAG_ONGOING_EVENT	正在进行中通知
        Notification.FLAG_AUTO_CANCEL	用户点击通知后自动取消
        Notification.FLAG_NO_CLEAR	用户无法取消
        Notification.FLAG_FOREGROUND_SERVICE	表示正在运行的服务

     作者：reezy
     链接：http://www.jianshu.com/p/d2051a785309
     來源：简书
     著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     * @param context
     * @param clz
     */
    public static void showNotificatio(Context context, @Nullable Class<?> clz) {
        Intent intent = null;
        //获取系统的notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        if (clz != null) {
            intent = new Intent(context, clz);
        }
        /**
         * //如果新请求的 PendingIntent 发现已经存在时，取消已存在的，用新的 PendingIntent 替换
         int FLAG_CANCEL_CURRENT

         //如果新请求的 PendingIntent 发现已经存在时，忽略新请求的，继续使用已存在的。日常开发中很少使用
         int FLAG_NO_CREATE

         //表示 PendingIntent 只能使用一次，如果已使用过，那么 getXXX(...) 将会返回 NULL
         //也就是说同类的通知只能使用一次，后续的通知单击后无法打开。
         int FLAG_ONE_SHOT

         //如果新请求的 PendingIntent 发现已经存在时, 如果 Intent 有字段改变了，这更新已存在的 PendingIntent
         int FLAG_UPDATE_CURRENT

         */
        PendingIntent pendingIntent = PendingIntent.getService(context, REQUEST_NOTIFICATION_CODE, intent,PendingIntent.FLAG_ONE_SHOT);
        notificationBuilder.setContentIntent(pendingIntent)
                .setSmallIcon(android.R.drawable.ic_lock_silent_mode_off)
                .setContentTitle("头部")
                .setContentText("内容")
                .setAutoCancel(true)
                ;
        notificationManager.notify(REQUEST_NOTIFICATION_CODE, notificationBuilder.build());
    }
}
