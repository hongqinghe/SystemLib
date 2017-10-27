package middlem.person.systemmodule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

/***********************************************
 *
 * <P> desc:   监听系统键盘（该activity为全屏模式时），设置模式为
 *              android:windowSoftInputMode="adjustResize|stateAlwaysHidden"，虽然这样设置了系统键盘模式为
 *              adjustResize【该模式下弹出系统键盘layout会进行重绘，可以给布局中的root布局设置监听方法，从而判断
 *              系统键盘的状态】，但是全屏模式下该监听是无效的，所以使用{@link  SystemInputKeyboardUtils},该工具
 *              类解决了设置adjustResize无效的判断，但是使用工具类后，会造成root布局的增大，差不多30dp
 * <P> Author: gongtong
 * <P> Date: 2017-10-25 21:29
 ***********************************************/

public class SystemInputFullActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        设置activity设置全屏模式
        requestWindowFeature(Window.FEATURE_NO_TITLE);
       /*set it to be full screen*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_input_all);
        SystemInputKeyboardUtils.assistActivity(this);
        initView();
    }

    private void initView() {
      ConstraintLayout root_view=findViewById(R.id.root_view);
        Button button= findViewById(R.id.button);
        button.setVisibility(View.VISIBLE);
      root_view.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
          @Override
          public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
              //现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > SystemInputKeyboardUtils.getkeyHeight(mContext))) {
                    Toast.makeText(mContext,  "弹起", Toast.LENGTH_SHORT).show();
//                    EventBus.getDefault().post(new SystemKeyBoardChangeEvent(SystemKeyBoardChangeEvent.SYSTEM_INPUT_KEYBOARD_CHANGE,true));
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > SystemInputKeyboardUtils.getkeyHeight(mContext))) {
//                    EventBus.getDefault().post(new SystemKeyBoardChangeEvent(SystemKeyBoardChangeEvent.SYSTEM_INPUT_KEYBOARD_CHANGE,false));
                    Toast.makeText(mContext,  "收起", Toast.LENGTH_SHORT).show();
                }
            }

      });
    }

    public void toNotFull(View view) {
        Intent intent=new Intent(this,SystemInputActivity.class);
        startActivity(intent);
    }
}
