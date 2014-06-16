package sk.rdy.legi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;
import sk.rdy.legi.R;

/**
 * Created by rdy on 14.7.2013.
 */
public class TypeFacedButton extends Button {

    private Context mContext;

    public TypeFacedButton(Context context){
        super(context);
        mContext = context;
    }

    public TypeFacedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        // Typeface.createFromAsset doesn't work in the layout editor. Skipping ...
        if (isInEditMode()){
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypeFacedButton);
        String fontName =
                styledAttrs != null ? styledAttrs.getString(R.styleable.TypeFacedButton_button_typeface) : null;

        if (styledAttrs != null) {
            styledAttrs.recycle();
        }

        if (fontName != null){
            setFontName(fontName);
        }
    }

    public void setFontName(String fontName){
        Typeface typeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/" + fontName);
        setTypeface(typeface);
    }
}
