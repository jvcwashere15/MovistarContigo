package pe.com.qallarix.movistarcontigo.util;

import android.text.method.PasswordTransformationMethod;
import android.view.View;


public class NumericKeyBoardTransformationMethod extends PasswordTransformationMethod {
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return source;
    }
}
