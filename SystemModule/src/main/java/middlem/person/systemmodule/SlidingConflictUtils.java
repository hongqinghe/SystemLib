package middlem.person.systemmodule;
import android.view.MotionEvent;
import android.view.View;

/***********************************************
 * <P> desc:   此类用于自定义View的时候出现事件冲突的解决方案(根据移动的时间来判断)
 * <P> Author: hehongqing
 * <P> Date: 2017/10/30 23:37
 * <P> Copyright  2008 二维火科技
 ***********************************************/

public class SlidingConflictUtils {
    private static boolean isClick = false;
    private  static Long startTime, endTime;

    /**
     * 滑动事件和点击事件的处理冲突
     * @param view
     */
    public static  void toSolveConflict(View view){
        if (view!=null){
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            isClick = false;
                            startTime = System.currentTimeMillis();
                            break;
                        case MotionEvent.ACTION_MOVE:
                            isClick = true;
                            break;
                        case MotionEvent.ACTION_UP:
                            endTime = System.currentTimeMillis();
                            //解决事件 冲突
                            if ((endTime - startTime) > 0.1 * 1000L) {
                                isClick = true;
                            } else
                                isClick = false;
                            break;
                    }
                    return isClick  ;

                }
            });
        }
    }
}
