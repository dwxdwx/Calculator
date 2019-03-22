package dwx;

/**
 * Created by dwx on 2019/3/20.
 */

import java.util.Stack;

public class Deal {
    public String mText = new String();
    public String[] mTextDetail = new String[100];//用数组方便放入栈中
    public int mDetailPos;
    public String mAns = new String();
    private int mLeftBracketNum;

    private String[] mSuffix = new String[105];
    private int mSuffixPos;
    private Stack mTemp = new Stack();

    public Deal() {
        mLeftBracketNum = 0;
        mDetailPos = 0;
        mAns = "0";
        mText = "";
    }

    public void Change(char c) {


        mAns = "0";
        if (c <= '9' && c >= '0') {
            if (mDetailPos > 0) {
                if (mTextDetail[mDetailPos - 1].equals(")") == false)
                    mText += c;
            } else
                mText += c;
        } else if (c == '^') {
            int i = 0;
            i = Integer.valueOf(mText).intValue();
            mText = "";
            mText += i * i;
        } else if (c == '+' || c == '-' || c == '×' || c == '÷') {

            if (mText != "") {
                if (mText.substring(mText.length() - 1, mText.length()).equals("."))//确保末尾不能有小数点
                {
                    mText = mText.substring(0, mText.length() - 1);
                }
                mTextDetail[mDetailPos++] = mText;
                String temp = new String();
                temp = "";
                temp += c;
                mTextDetail[mDetailPos++] = temp;
                mText = "";
            } else if (mTextDetail[mDetailPos - 1].equals("(") == true) {

            } else if (mTextDetail[mDetailPos - 1].equals(")") == true) {
                String temp = new String();
                temp = "";
                temp += c;
                mTextDetail[mDetailPos++] = temp;
            } else if (mTextDetail[mDetailPos - 1].equals(")") == false) {
                String temp = new String();
                temp = "";
                temp += c;
                mTextDetail[mDetailPos - 1] = temp;
            }
            if (c == '+' || c == '-')
                if (mLeftBracketNum == 0) {
                    Calculate();
                }
        } else if (c == 'C') {
            mLeftBracketNum = 0;
            mText = "";
            for (int i = 0; i < mDetailPos; i++) {
                mTextDetail[i] = "";
            }
            mDetailPos = 0;
            mAns = "0";
        } else if (c == '=') {
            if (mText != "")
                mTextDetail[mDetailPos++] = mText;
            else {
                mText = "FORMAT ERROR";
            }
            for (int i = mLeftBracketNum; i > 0; i--) {
                //补全括号
                mTextDetail[mDetailPos++] = ")";
            }
            mTextDetail[mDetailPos++] = "=";
            Calculate();
            for (int i = 0; i < mDetailPos; i++) {
                mTextDetail[i] = "";
            }
            mDetailPos = 0;
            mText = "";
        } else if (c == '.') {
            if (mText != "") {
                if (mText.contains(".") == false) {
                    mText += c;
                }
            }
        } else if (c == '(') {
            if (mDetailPos == 0) {
                mTextDetail[mDetailPos++] = "(";
                mLeftBracketNum++;
            } else if (mTextDetail[mDetailPos - 1].equals("+") == true ||
                    mTextDetail[mDetailPos - 1].equals("-") == true ||
                    mTextDetail[mDetailPos - 1].equals("×") == true ||
                    mTextDetail[mDetailPos - 1].equals("÷") == true) {
                mTextDetail[mDetailPos++] = "(";
                mLeftBracketNum++;
            }
        } else if (c == ')') {
            if (mLeftBracketNum > 0) {
                if (mText != "") {
                    mTextDetail[mDetailPos++] = mText;
                    mTextDetail[mDetailPos++] = ")";
                    mLeftBracketNum--;
                    mText = "";
                }
            }
        }
    }

    /**
     * 中缀转后缀(逆波兰表达式)
     */
    public void toSuffix() {
        for (int i = 0; i < mDetailPos - 1; i++)//-1是因为式子包含了最后的 = + -
        {
            if (mTextDetail[i].equals("+") == true ||
                    mTextDetail[i].equals("-") == true) {
                while (mTemp.empty() == false &&
                        (mTemp.peek().equals("+") == true ||
                                mTemp.peek().equals("-") == true ||
                                mTemp.peek().equals("×") == true ||
                                mTemp.peek().equals("÷") == true)) {
                    mSuffix[mSuffixPos++] = (String) mTemp.peek();
                    mTemp.pop();
                }
                mTemp.push(mTextDetail[i]);
            } else if (mTextDetail[i].equals("×") == true ||
                    mTextDetail[i].equals("÷") == true) {
                while (mTemp.empty() == false &&
                        (mTemp.peek().equals("×") == true ||
                                mTemp.peek().equals("÷") == true)) {
                    mSuffix[mSuffixPos++] = (String) mTemp.peek();
                    mTemp.pop();
                }
                mTemp.push(mTextDetail[i]);
            } else if (mTextDetail[i].equals("(") == true) {
                mTemp.push(mTextDetail[i]);
            } else if (mTextDetail[i].equals(")") == true) {
                while (mTemp.peek().equals("(") == false) {
                    mSuffix[mSuffixPos++] = (String) mTemp.peek();
                    mTemp.pop();
                }
                mTemp.pop();
            } else {
                mSuffix[mSuffixPos++] = mTextDetail[i];
            }
        }
        while (mTemp.empty() == false) {
            mSuffix[mSuffixPos++] = (String) mTemp.peek();
            mTemp.pop();
        }
    }

    public void Calculate() {
        mSuffixPos = 0;
        for (int i = 0; i < 100; i++) {
            mSuffix[i] = "";
        }
        while (mTemp.empty() == false) {
            mTemp.pop();
        }
        toSuffix();
        //输出测试

        for (int i = 0; i < mDetailPos; i++)
            System.out.print(mTextDetail[i] + " ");
        System.out.print("\n");
        for (int i = 0; i < mSuffixPos; i++) {
            System.out.print(mSuffix[i] + " ");
        }
        System.out.print("\n");

        for (int i = 0; i < mSuffixPos; i++) {
            if (mSuffix[i].equals("+") || mSuffix[i].equals("-")
                    || mSuffix[i].equals("×") || mSuffix[i].equals("÷")) {
                double b = Double.valueOf((String) mTemp.pop()).doubleValue();
                double a = Double.valueOf((String) mTemp.pop()).doubleValue();
                double c = 0;
                System.out.println("a = " + a);
                System.out.println("b = " + b);
                System.out.println("c = " + c);
                if (mSuffix[i].equals("+")) {
                    c = a + b;
                }
                if (mSuffix[i].equals("-")) {
                    c = a - b;
                }
                if (mSuffix[i].equals("×")) {
                    c = a * b;
                }
                if (mSuffix[i].equals("÷")) {
                    if (c == 0.0) {
                        mText = "VALUE ERROR";
                        break;
                    } else
                        c = a / b;

                }

                String str = Double.toString(c);
                mTemp.push(str);
            } else {
                mTemp.push(mSuffix[i]);
                System.out.println("mSuffix[i] =" + mSuffix[i]);


            }
        }
        mAns = (String) mTemp.pop();
        if (mAns.length() > 16)//防止超出屏幕   如果超过了 改成指数形式输出更好
            mAns = mAns.substring(0, 16);
    }
}
