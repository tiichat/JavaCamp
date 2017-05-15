package com.tiichat.javacamp;

import com.tiichat.javacamp.chapter09.DomainRule;
import com.tiichat.javacamp.chapter09.Player;

public class Chapter09 {

    public static void main(String[] args) {
        applyAssistPackAllPlayer();
    }

    /**
     * 応援パックを、全プレイヤーに適用する。
     */
    public static void applyAssistPackAllPlayer() {
        FileDao.newInstance(Player::newInstance)
                .ruleSet(DomainRule::assistPack)
                .confirmOverwrite(Console::confirmFileOverwrite)
                .forEach("input.txt", "output.txt");
    }

}
