package com.chauhai.android.batsg.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.chauhai.android.batsg.R;

/**
 * Create a LinearLayout which width is a proportional of height, or reverse.
 * One of width or height must be computable, and the other should be 0.
 * <p>
 * The proportion of width/height is set by the attribute <code>proportion</code>
 * in the layout XML.
 *
 * @author umbalaconmeogia
 *
 */
public class ProportionalLayout extends LinearLayout {

  float proportion = 1f;

  public ProportionalLayout(Context context) {
    super(context);
  }

  public ProportionalLayout(Context context, AttributeSet attrs) {
    super(context, attrs);

    // Get proportion.
    TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ProportionalLayout);
    proportion = a.getFloat(R.styleable.ProportionalLayout_proportion, 1f);
    a.recycle();
  }

  public void setProportion(float proportion) {
    this.proportion = proportion;
  }

  /**
   * Set width (height) based on height (width) if it is zero
   * base on the proportion.
   */
  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

    int width = MeasureSpec.getSize(widthMeasureSpec);
    int height = MeasureSpec.getSize(heightMeasureSpec);

    if (width == 0) { // Calculate width based on height.
      widthMeasureSpec = MeasureSpec.makeMeasureSpec(
          (int) (proportion * height), MeasureSpec.EXACTLY);
    } else if (height == 0 && proportion != 0f) { // Calculate height based on width
      heightMeasureSpec = MeasureSpec.makeMeasureSpec(
          (int) (width / proportion), MeasureSpec.EXACTLY);
    }
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
  }
}
