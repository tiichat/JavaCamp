package com.tiichat.javacamp.chapter09;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * プレイヤークラス。MVCの、M(Model)部分の役割を担う。
 *
 * @author tiich
 *
 */
public class Player {

    // 本当は private にする。教育用プログラムのため、lombok 入れてないので少しズルする。
    int id;
    String name;
    String job;
    int level;
    int gold;
    String weapon;
    String armor;
    List<String> items;

    /**
     * プレーヤーオブジェクトの、ファクトリメソッド。
     *
     * @param src
     *            １レコード分の、CSV文字列データ。
     * @return
     */
    public static Player newInstance(String src) {
        final Player player = new Player();
        player.mapping(src);
        return player;
    }

    private Player() {
    }

    /**
     * アイテムを交換する。
     *
     * @param give
     *            与える（手放す）アイテム
     * @param take
     *            もらう（入手する）アイテム
     */
    public void tradeItem(String give, String take) {
        for (int i = 0; i < this.items.size(); i++) {
            if (this.items.get(i).equals(give)) {
                this.items.set(i, take);
            }
        }
    }

    /**
     * 文字列（CSV）を返す。
     */
    @Override
    public String toString() {
        final StringJoiner sj = new StringJoiner(CSV_DELIMITER);
        sj.add(Integer.toString(this.id));
        sj.add(this.name);
        sj.add(this.job);
        sj.add(Integer.toString(this.level));
        sj.add(Integer.toString(this.gold));
        sj.add(this.weapon);
        sj.add(this.armor);
        this.items.forEach(sj::add);
        return sj.toString();
    }

    /*
     * CSVデータを、プレイヤーの属性にマッピングする。
     */
    private void mapping(String src) {
        final String[] s = src.split(CSV_DELIMITER);
        this.id = Integer.parseInt(s[0]);
        this.name = s[1];
        this.job = s[2];
        this.level = Integer.parseInt(s[3]);
        this.gold = Integer.parseInt(s[4]);
        this.weapon = s[5];
        this.armor = s[6];
        this.items = new ArrayList<>();
        for (int i = 7; i < s.length; i++) {
            if (s[i] != null && !s[i].isEmpty()) {
                this.items.add(s[i]);
            }
        }
    }

    private static final String CSV_DELIMITER = ",";
}
