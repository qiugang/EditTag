package me.originqiu.library;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;


/**
 * http://stackoverflow.com/questions/4886858/android-edittext-deletebackspace-key-event
 * fix soft keyboard can not listen delete action event
 * Created by OriginQiu on 16/7/29.
 */
public class MEditText extends AppCompatEditText {
    public MEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MEditText(Context context) {
        super(context);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new ZanyInputConnection(super.onCreateInputConnection(outAttrs),
                                       true);
    }

    private class ZanyInputConnection extends InputConnectionWrapper {

        public ZanyInputConnection(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                // Un-comment if you wish to cancel the backspace:
                // return false;
            }
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean deleteSurroundingText(int beforeLength, int afterLength) {
            // magic: in latest Android, deleteSurroundingText(1, 0) will be
            // called for backspace
            if (beforeLength == 1 && afterLength == 0) {
                // backspace
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN,
                                                 KeyEvent.KEYCODE_DEL)) && sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP,
                                                                                                     KeyEvent.KEYCODE_DEL));
            }

            return super.deleteSurroundingText(beforeLength, afterLength);
        }

        /** Custom code to add tags with physical and soft keyboards, by AlejandroHCruz
         *
         * NOTE: If you want to support auto-correct, override commitCorrection(CorrectionInfo correctionInfo)
         *       setting String newTag = (String) correctionInfo.getNewText();
         *       Otherwise just add android:inputType="textNoSuggestions" to your MEditText
         *
         * **/
        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {

            String newTag = (String) text;
            if (newTag.endsWith(",") || newTag.endsWith("\n") || newTag.endsWith("\r") || newTag.endsWith(" ")) {
                return sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_COMMA));
            }

            return super.commitText(text, newCursorPosition);
        }

    }
}
