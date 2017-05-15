package com.tiichat.javacamp;

import java.util.Scanner;

/**
 * コンソールクラス。MVCの、V(View)部分の役割を担う。
 *
 * @author tiich
 *
 */
public class Console {

    /**
     * コンソールで、Yes か No を確認する。
     *
     * @param confirmMsg
     * @return
     */
    @SuppressWarnings("resource")
    public static boolean confirmYesOrNo(String confirmMsg, String yes, String no) {
        final Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print(confirmMsg);
            final String str = scan.next();
            if (str.equalsIgnoreCase(no)) {
                System.out.println(MSG_INTERRUPT);
                return false;
            } else if (str.equalsIgnoreCase(yes)) {
                return true;
            }
        }
    }

    /**
     * ファイル上書きを確認する。
     *
     * @return
     */
    public static boolean confirmFileOverwrite() {
        return confirmYesOrNo(MSG_CONFIRM, "y", "n");
    }

    // 本当だったら、リソースで管理する。の意味を込めて、コンスタントでメッセージを定義してみる・・。
    private static final String MSG_CONFIRM = "ファイルを上書きしますか? [Yes/No] >";
    private static final String MSG_INTERRUPT = "中断します。";

}
