package sk.rdy.legi.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import sk.rdy.legi.R;

/**
 * Created by rdy on 10.7.2013.
 */
public class TypeFacedEditText extends EditText {

    public TypeFacedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Typeface.createFromAsset doesn't work in the layout editor. Skipping ...
        if (isInEditMode()){
            return;
        }

        TypedArray styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TypeFacedEditText);
        String fontName =
                styledAttrs != null ? styledAttrs.getString(R.styleable.TypeFacedEditText_edittext_typeface) : null;

        if (styledAttrs != null) {
            styledAttrs.recycle();
        }

        if (fontName != null){
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), "fonts/" + fontName);
            setTypeface(typeface);
        }
    }
}
