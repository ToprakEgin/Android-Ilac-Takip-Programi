package com.example.toprakegin.ilactakipdeneme1;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;

public class CustomAutoCompleteView extends AutoCompleteTextView {
    public CustomAutoCompleteView(Context context) {
        super(context);
    }

    public CustomAutoCompleteView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAutoCompleteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // this is how to disable AutoCompleteTextView filter
    @Override
    protected void performFiltering(final CharSequence text, final int keyCode) {
        String filterText = "";
        super.performFiltering(filterText, keyCode);
    }


    @Override
    protected void replaceText(final CharSequence text) {
        super.replaceText(text);
    }
}
