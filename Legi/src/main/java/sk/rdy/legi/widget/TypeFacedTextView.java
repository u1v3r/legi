package sk.rdy.legi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import sk.rdy.legi.R;

/**
 * Created by rdy on 10.7.2013.
 */
public class TypeFacedTextView extends TextView {

    private Context mContext;

    public TypeFacedTextView(Context context){
        super(context);
        mContext = context;
    }

    public TypeFacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        // Typeface.createFromAsset doesn't work in the layout editor. Skipping ...
        if (isInEditMode()){
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypeFacedTextView);
        String fontName =
                styledAttrs != null ? styledAttrs.getString(R.styleable.TypeFacedTextView_typeface) : null;

        if (styledAttrs != null) {
            styledAttrs.recycle();
        }

        if (fontName != null){
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
            setTypeface(typeface);
        }
    }

    public void setFontName(String fontName){
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + fontName);
        setTypeface(typeface);
    }
}
