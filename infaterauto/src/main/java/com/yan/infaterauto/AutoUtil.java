package com.yan.infaterauto;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.util.TypedValue;

/**
 * Created by yan on 25/11/2017
 */
public class AutoUtil {
    public static View auto(View view) {
        final InflaterAuto inflaterAuto = InflaterAuto.getInstance();
        if (inflaterAuto == null) {
            return view;
        }

        final float hRatio = inflaterAuto.getHRatio();
        final float vRatio = inflaterAuto.getVRatio();

        innerAuto(view, inflaterAuto, hRatio, vRatio);

        return view;
    }

    private static void innerAuto(View view, InflaterAuto inflaterAuto, float hRatio, float vRatio) {
        if (view != null && inflaterAuto.except(view.getClass())) {
            final ViewGroup.LayoutParams lp = view.getLayoutParams();
            if (lp != null) {
                if (lp.width != -1 && lp.width != -2) {
                    lp.width = (int) (lp.width * hRatio + 0.5);
                }
                if (lp.height != -1 && lp.height != -2) {
                    lp.height = (int) (lp.height * vRatio + 0.5);
                }

                view.setPadding((int) (view.getPaddingLeft() * hRatio + 0.5)
                        , (int) (view.getPaddingTop() * vRatio + 0.5)
                        , (int) (view.getPaddingRight() * hRatio + 0.5)
                        , (int) (view.getPaddingBottom() * vRatio + 0.5)
                );

                if (lp instanceof ViewGroup.MarginLayoutParams) {
                    final ViewGroup.MarginLayoutParams mplp = (ViewGroup.MarginLayoutParams) lp;
                    mplp.leftMargin = (int) (mplp.leftMargin * hRatio + 0.5);
                    mplp.rightMargin = (int) (mplp.rightMargin * hRatio + 0.5);
                    mplp.topMargin = (int) (mplp.topMargin * vRatio + 0.5);
                    mplp.bottomMargin = (int) (mplp.bottomMargin * vRatio + 0.5);
                }

                if (view instanceof TextView) {
                    final TextView tv = (TextView) view;
                    final float textSize = tv.getTextSize() * Math.min(hRatio, vRatio);
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
                }

                if ((view instanceof ViewGroup) && !(view instanceof AbsListView) && !(view instanceof RecyclerView)) {
                    for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                        innerAuto(((ViewGroup) view).getChildAt(i), inflaterAuto, hRatio, vRatio);
                    }
                }
            }
        }
    }

    public static float getValueHorizontal(float value) {
        return InflaterAuto.getInstance().getHRatio() * value;
    }

    public static float getValueVertical(float value) {
        return InflaterAuto.getInstance().getVRatio() * value;
    }
}

