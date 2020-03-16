package kalkull.ruslanann.kalkulator24.mathCount;

import android.widget.EditText;

import java.math.BigDecimal;

public class MathCount {
    static float num1 = 0;
    static float num2 = 0;
    static float num3 = 0;
    static float num4 = 0;
    static float result2 = 0;
    static float result3 = 0;
    static String oper = "";
    static float result = 0;


    public static float makeCount10Amper(EditText num11, EditText num22, float x) {
        num1 = Float.parseFloat(num11.getText().toString());
        num2 = Float.parseFloat(num22.getText().toString());
        result = (float) (num2 * 7.5 * x / 150 / num1);
        result = new BigDecimal(result).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
        return result;

    }

    public static float ROUND_HALF_UP(EditText num33) {
        num3 = Float.parseFloat(num33.getText().toString());
        result2 = result * 255 / (235 + num3);
        result2 = new BigDecimal(result2).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
        return result2;
    }

    public static float makeCount20degrees(EditText etNum, float results) {
        num4 = Float.parseFloat(etNum.getText().toString());
        result3 = (results / num4) * 100 - 100;
        result3 = new BigDecimal(result3).setScale(4, BigDecimal.ROUND_HALF_UP).floatValue();
        return result3;
    }
}
