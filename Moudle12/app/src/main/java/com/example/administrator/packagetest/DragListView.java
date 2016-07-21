package com.example.administrator.packagetest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by Administrator on 2016/7/21.
 */
public class DragListView extends ListView {
    private  Context context;
    private ImageView dragImageView;//被拖拽项的影像，其实就是一个ImageView
    private int dragStartPosition;//手指拖动项原始在列表中的位置
    private int dragCurrentPosition;//手指拖动的时候，当前拖动项在列表中的位置

    //当前位置距离边界的位置
    private int dragOffsetX;
    private int dragOffSetY;
    //移动的位置
    private int dragPointX;
    private int dragPointY;
    //拖动的时候，开始向上 向下滚动的边界
    private int upperBound;
    private int lowerBound;


    private WindowManager windowManager;//windows窗口控制类
    private WindowManager.LayoutParams layoutParams;//用于控制拖拽项的显示的参数

    private int scaledTouchSlop;//触发移动事件的最短距离
    public DragListView(Context context) {
        super(context);
        this.context=context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }
    public DragListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        scaledTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction()==MotionEvent.ACTION_DOWN) {
                 stopDrag();
                final int x = (int) ev.getX();
                final int y = (int) ev.getY();
                final int itemNum = pointToPosition(x, y);
                if(itemNum == AdapterView.INVALID_POSITION){
                    return super.onInterceptTouchEvent(ev);
                }
                final View item = (View) getChildAt(itemNum - getFirstVisiblePosition());
                dragPointX = x - item.getLeft();
                dragPointY = y - item.getTop();
                dragOffsetX = ((int) ev.getRawX()) - x;
                dragOffSetY = ((int) ev.getRawY()) - y;
                //长按
                item.setOnLongClickListener(new OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Log.d("pipi",String.valueOf(itemNum - getFirstVisiblePosition()));
                        //upScrollBounce当在屏幕的上部(上面1/3区域)或者更上的区域，执行拖动的边界，downScrollBounce同理定义
                        final int height = getHeight();
                        upperBound = Math.min(y - scaledTouchSlop, height / 3);
                        lowerBound = Math.max(y + scaledTouchSlop, height * 2 / 3);
                        dragCurrentPosition = dragStartPosition = itemNum;
                        item.setDrawingCacheEnabled(true);
                        Bitmap bitmap = Bitmap.createBitmap(item.getDrawingCache());
                        //避免重复缓存
                        item.destroyDrawingCache();
                        startDrag(bitmap,x,y);
                        return true;
                    }
                });
        }
        return super.onInterceptTouchEvent(ev);
    }

    public void startDrag(Bitmap bitmap ,int x,int y){

        layoutParams = new WindowManager.LayoutParams();
        layoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        layoutParams.x = x - dragPointX + dragOffsetX;
        layoutParams.y = y - dragPointY + dragOffSetY;
        layoutParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //下面这些参数能够帮助准确定位到选中项点击位置，
        layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
                | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        layoutParams.format = PixelFormat.TRANSLUCENT;
        layoutParams.windowAnimations = 0;

        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(bitmap);
        imageView.setPadding(0, 0, 0, 0);
        windowManager.addView(imageView, layoutParams);
        dragImageView = imageView;
    }

    public void stopDrag(){
        if(dragImageView!=null){
            windowManager.removeView(dragImageView);
            dragImageView.setImageDrawable(null);
            dragImageView = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果dragmageView为空，说明拦截事件中已经判定仅仅是点击，不是拖动，返回
        //如果点击的是无效位置，返回，需要重新判断
        if(dragImageView!=null&&dragCurrentPosition!=INVALID_POSITION){
            int action = ev.getAction();
            switch(action){
                case MotionEvent.ACTION_UP:
                    int upY = (int)ev.getY();
                    int upx = (int)ev.getX();
                    //释放拖动影像
                    stopDrag();
                    //放下后，判断位置，实现相应的位置删除和插入
                   onDrop(upx,upY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveY = (int)ev.getY();
                    //拖动影像
                    onDrag(moveY);
                    break;
                default:break;
            }
            return true;
        }
        //这个返回值能够实现selected的选中效果，如果返回true则无选中效果
        return super.onTouchEvent(ev);
    }
    public void onDrag(int y){
        if(dragImageView!=null){
            //透明度
            layoutParams.alpha = 0.5f;
            layoutParams.y = y - dragPointY + dragOffSetY;
            windowManager.updateViewLayout(dragImageView,layoutParams);
        }
        //为了避免滑动到分割线的时候，返回-1的问题
        int tempPosition = pointToPosition(0, y);
        if(tempPosition!=INVALID_POSITION){
            dragCurrentPosition = tempPosition;
        }

        //滚动
        int scrollHeight = 0;
        if(y<upperBound){
            scrollHeight = 8;//定义向上滚动8个像素，如果可以向上滚动的话
        }else if(y>lowerBound){
            scrollHeight = -8;//定义向下滚动8个像素，，如果可以向上滚动的话
        }

        if(scrollHeight!=0){
            //真正滚动的方法setSelectionFromTop()
            setSelectionFromTop(dragCurrentPosition, getChildAt(dragCurrentPosition-getFirstVisiblePosition()).getTop()+scrollHeight);
        }
    }
    public void onDrop(int x,int y){
        //获取放下位置在数据集合中position
        //定义临时位置变量为了避免滑动到分割线的时候，返回-1的问题，如果为-1，则不修改dragPosition的值，急需执行，达到跳过无效位置的效果
        int tempPosition = pointToPosition(x,y);
        if(tempPosition!=INVALID_POSITION){
            dragCurrentPosition = tempPosition;
        }
        //超出边界处理
        if(y<getChildAt(0).getTop()){
            //超出上边界，设为最小值位置0
            dragCurrentPosition = 0;
        }else if(y>getChildAt(getChildCount()-1).getBottom()){
            //超出下边界，设为最大值位置，注意哦，如果大于可视界面中最大的View的底部则是越下界，所以判断中用getChildCount()方法
            //但是最后一项在数据集合中的position是getAdapter().getCount()-1，这点要区分清除
            dragCurrentPosition = getAdapter().getCount()-1;
        }
        //数据更新
        if(dragCurrentPosition>0&&dragCurrentPosition<getAdapter().getCount()){
            @SuppressWarnings("unchecked")
            ArrayAdapter<String> adapter = (ArrayAdapter<String>)getAdapter();
            String dragItem = adapter.getItem(dragStartPosition);
            Log.d("dragItem",String.valueOf(dragCurrentPosition));
            Log.d("dragItem",String.valueOf(dragStartPosition));
            //删除原位置数据项
            adapter.remove(dragItem);
            //在新位置插入拖动项
            adapter.insert(dragItem, dragCurrentPosition);
            adapter.notifyDataSetChanged();
        }
    }
}