package middlem.person.systemmodule;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/***********************************************
 *
 * <P> desc:   基类activty
 * <P> Author: gongtong
 * <P> Date: 2017-10-25 21:25
 ***********************************************/

public class BaseActivity extends FragmentActivity {
protected Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mContext=this;
    }

    /**
     * 此方法用于拦截在系统键盘弹出的时候，点击其他区域（除过输入框区域）隐藏系统键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftKeyboard();
            }

            return super.dispatchTouchEvent(ev);
        }
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }

        return onTouchEvent(ev);
    }
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            //获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getY() < right && event.getY() > top && event.getY() < bottom) {
                //点击的是输入框区域。保留点击事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    /**
     * 隐藏软键盘
     */
    protected void hideSoftKeyboard() {
        InputMethodManager im = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (im.isActive() || this.getCurrentFocus() != null) {
            im.hideSoftInputFromWindow(this.findViewById(android.R.id.content).getWindowToken(), 0);
        }
    }

    /**
     * 显示软键盘
     */
    protected void showSoftKeyboard() {
        InputMethodManager im = (InputMethodManager) this.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (!im.isActive() || this.getCurrentFocus() != null) {
            im.showSoftInput(this.getCurrentFocus(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){

        }
        return super.onKeyDown(keyCode, event);
    }
}
