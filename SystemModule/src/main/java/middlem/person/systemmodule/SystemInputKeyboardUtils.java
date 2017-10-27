package middlem.person.systemmodule;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

import middlem.person.utilsmodule.SystemUtils;

/***********************************************
 * <P> desc: 修复activity设置adjustResize无效的问题，并检测系统键盘的状态
 *            在activity的setContentView之后调用
 * <P> Author: gongtong
 * <P> Date: 2017/10/23 19:10
 * <P> Copyright  2008 二维火科技
 ***********************************************/

public class SystemInputKeyboardUtils {

    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.
    public static void assistActivity (Activity activity) {
        new SystemInputKeyboardUtils(activity);
    }

    private View mChildOfContent;
    private int usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private SystemInputKeyboardUtils(Activity activity) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent();
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent() {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard/4)) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard;
            }
            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
    }
    /**
     * 返回软件盘弹起后所占高度阀值
     * @param context
     * @return
     */
    public static  int getkeyHeight(Context context){
        return SystemUtils.getScreenHeight(context) / 3;
    }
//
//    /**
//     * 设置软键盘的监听回调
//     * @param context
//     * @param rootView
//     */
//    public static void setOnLayoutChangeListener(final Context context, View rootView){
//        rootView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
//            @Override
//            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
//                //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
//                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > getkeyHeight(context))) {
//                    EventBus.getDefault().post(new SystemKeyBoardChangeEvent(SystemKeyBoardChangeEvent.SYSTEM_INPUT_KEYBOARD_CHANGE,true));
//                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > getkeyHeight(context))) {
//                    EventBus.getDefault().post(new SystemKeyBoardChangeEvent(SystemKeyBoardChangeEvent.SYSTEM_INPUT_KEYBOARD_CHANGE,false));
//                }
//            }
//        });
//    }
}
