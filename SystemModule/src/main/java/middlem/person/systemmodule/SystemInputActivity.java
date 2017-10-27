package middlem.person.systemmodule;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/***********************************************
 *
 * <P> desc:   监听系统键盘（该activity为非全屏模式），设置模式为
 *              android:windowSoftInputMode="adjustResize|stateAlwaysHidden"
 * <P> Author: gongtong
 * <P> Date: 2017-10-25 21:29
 ***********************************************/

public class SystemInputActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_input_all);
        initView();
    }

    private void initView() {
      ConstraintLayout root_view=findViewById(R.id.root_view);
        EditText editText=findViewById(R.id.editText);
        editText.setText("非全屏测试");
        Button button=findViewById(R.id.button);
        button.setVisibility(View.GONE);
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
}
