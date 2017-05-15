package com.tiichat.javacamp.chapter09;

import java.util.List;
import java.util.function.Consumer;

/**
 * ルールやビジネスロジックなどを記述するクラス。
 * DDD(Domain Driven Design)的には、「ドメイン層」の役割を担う。
 *
 * @author tiich
 *
 */
public class DomainRule {

    /**
     * 応援パック
     *
     * @param dao
     */
    public static void assistPack(final List<Consumer<Player>> rule) {
        rule.add(ruleAssistCheerFlag);
        rule.add(ruleAssistUnder10);
        rule.add(ruleAssistItemUpgrade);
    }

    // ルール：持っているアイテムが３つ以内なら、応援旗を渡す。でなければ、100Gを渡す。
    private static Consumer<Player> ruleAssistCheerFlag = player -> {
        if (player.items.size() <= 3) {
            player.items.add("応援旗");
        } else {
            player.gold += 100;
        }
    };

    // ルール：レベル１０以下のプレイヤーには、100Gを渡す。
    private static Consumer<Player> ruleAssistUnder10 = player -> {
        if (player.level <= 10) {
            player.gold += 100;
        }
    };

    // ルール：アイテム「鋼玉」を持っている場合は、「スペシャルソード」と交換する。
    private static Consumer<Player> ruleAssistItemUpgrade = player -> {
        player.tradeItem("鋼玉", "スペシャルソード");
    };
}
