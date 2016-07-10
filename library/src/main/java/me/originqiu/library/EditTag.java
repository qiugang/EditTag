/**
 * The MIT License (MIT)

 Copyright (c) 2015 OriginQiu

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all
 copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 SOFTWARE.

 */
package me.originqiu.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by OriginQiu on 4/7/16.
 */
public class EditTag extends FrameLayout {
    
    private FlowLayout mFlowLayout;
    
    private EditText mEditText;

    private int tagBackgroundColor;

    private int tagTextColor;
    
    private int tagViewLayoutRes;
    
    private int inputTagLayoutRes;

    private View.OnClickListener tagClickListener;
    
    private List<String> mTagList = new ArrayList<>();
    
    public EditTag(Context context) {
        this(context, null);
    }
    
    public EditTag(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    
    public EditTag(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs,
                                                                R.styleable.EditTag);
        tagViewLayoutRes = mTypedArray.getResourceId(R.styleable.EditTag_tag_layout,
                                                     R.layout.view_default_tag);
        inputTagLayoutRes = mTypedArray.getResourceId(R.styleable.EditTag_input_layout,
                                                      R.layout.view_default_input_tag);
        mTypedArray.recycle();

        setupView();

        TextView tagTv = (TextView) LayoutInflater.from(getContext())
                .inflate(tagViewLayoutRes,
                        mFlowLayout,
                        false);

        ColorDrawable cd = (ColorDrawable) tagTv.getBackground();
        this.tagBackgroundColor = cd.getColor();
        this.tagTextColor = tagTv.getCurrentTextColor();

    }
    
    private void setupView() {
        mFlowLayout = new FlowLayout(getContext());
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                                                     LayoutParams.WRAP_CONTENT);
        mFlowLayout.setLayoutParams(layoutParams);

        addView(mFlowLayout);
        addTagView();
        setupListener();

    }


    public void setEditable(boolean editable){
        if(!editable && mFlowLayout.getChildCount()>0){
            mFlowLayout.removeViewAt(mFlowLayout.getChildCount()-1);
        }
    }

    // Delete any tag by long pressing it
    // Can be changed to having an 'x' button next to each tag
    // If author is interested in that, can implement it

    private void setDeleteTagListener(TextView view) {

        view.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                TextView tv = (TextView) view;
                mFlowLayout.removeView(view);
                mTagList.remove(tv.getText());
                return true;
            }
        });
    }

    // Set tag bg color
    public void setTagBackgroundColor(int color){
        tagBackgroundColor = color;
    }

    // Set tag text color
    public void setTagTextColor(int color){
        tagTextColor = color;
    }

    // Set tag click listener
    public void setTagClickListener(View.OnClickListener listener){
        tagClickListener = listener;
    }

    private void setupListener() {
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            
            @Override
            public boolean onEditorAction(TextView v,
                                          int actionId,
                                          KeyEvent event) {
                boolean isHandle = false;
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    String tagContent = mEditText.getText().toString();
                    if (TextUtils.isEmpty(tagContent)) {
                        // do nothing, or you can tip "can'nt add empty tag"
                    }
                    else {
                        mFlowLayout.addView(createTag(mFlowLayout, tagContent),
                                            mFlowLayout.getChildCount() - 1);
                        isHandle = true;
                        mTagList.add(tagContent);
                        mEditText.getText().clear();
                    }
                }
                return isHandle;
            }
        });
        mEditText.setOnKeyListener(new View.OnKeyListener() {
            
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                boolean isHandle = false;
                if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                    String tagContent = mEditText.getText().toString();
                    if (TextUtils.isEmpty(tagContent)) {
                        // if tags != null,delete last tag
                        int tagCount = mFlowLayout.getChildCount();
                        if (tagCount > 1) {
                            mFlowLayout.removeViewAt(tagCount - 2);
                            mTagList.remove(tagCount - 2);
                            isHandle = true;
                        }
                    }
                    else {
                        mEditText.getText().delete(tagContent.length() - 1,
                                                   tagContent.length());
                    }
                }
                return isHandle;
            }
        });
    }
    
    private void addTagView() {
        mFlowLayout.removeAllViews();
        int size = mTagList.size();
        for (int i = 0; i < size + 1; i++) {
            if (i == size) {
                mFlowLayout.addView(createEditTag(mFlowLayout));
            }
            else {
                mFlowLayout.addView(createTag(mFlowLayout, mTagList.get(i)));
            }
        }
    }
    
    private TextView createTag(ViewGroup parent, String s) {
        TextView tagTv = (TextView) LayoutInflater.from(getContext()).inflate(tagViewLayoutRes,
                        parent,
                        false);
        tagTv.setText(s);
        tagTv.setTextColor(tagTextColor);
        tagTv.setBackgroundColor(tagBackgroundColor);
        if(tagClickListener!=null)
            tagTv.setOnClickListener(tagClickListener);
        setDeleteTagListener(tagTv);
        return tagTv;
    }
    
    private EditText createEditTag(ViewGroup parent) {
        mEditText = (EditText) LayoutInflater.from(getContext())
                                             .inflate(inputTagLayoutRes,
                                                      parent,
                                                      false);
        return mEditText;
    }

    public List<String> getTagList() {
        return mTagList;
    }

    public void setTagList(List<String> mTagList) {
        this.mTagList = mTagList;
        addTagView();
        setupListener();
    }
}
