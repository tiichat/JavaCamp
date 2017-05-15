package com.tiichat.javacamp;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * ファイル用のDAO(Data Access Object)クラス。
 * DDD(Domain Driven Design)的には、「リポジトリ層」の役割を担う。
 *
 * @author tiich
 *
 * @param <T>
 *            読み込んだファイルの１行（レコード）をマッピングするDTOクラス。
 */
public class FileDao<T> {

    /**
     * FileDaoの、ファクトリメソッド。
     *
     * @param mapper
     * @return
     */
    public static <T> FileDao<T> newInstance(Function<String, T> mapper) {
        final FileDao<T> dao = new FileDao<>();
        dao.mapper = mapper;
        return dao;
    }

    private FileDao() {
    }

    /**
     * ファイルから読み込んだ１行（１レコード）に対する、編集ルールセット関数を呼び出し、
     * エフェクタリストに編集ルールを追加する。
     *
     * @param ruleSet
     *            ルールセット関数
     * @return
     */
    public FileDao<T> ruleSet(Consumer<List<Consumer<T>>> ruleSet) {
        ruleSet.accept(this.effectors);
        return this;
    }

    /**
     * エフェクタリストに編集ルールを追加するための関数をセットする。
     *
     * @param effector
     *            エフェクト関数
     * @return
     */
    public FileDao<T> effect(Consumer<T> effector) {
        this.effectors.add(effector);
        return this;
    }

    /**
     * 出力ファイルが存在したときに、上書きするかどうかを返す関数をセットする。
     *
     * @param confirm
     *            ファイルを上書きするかどうかを確認する関数
     * @return
     */
    public FileDao<T> confirmOverwrite(BooleanSupplier confirm) {
        this.confirmOverwrite = confirm;
        return this;
    }

    /**
     * 入力ファイルの各行に対して登録した編集ルールを適用し、出力ファイルに書き出す。
     *
     * @param inName
     *            入力ファイル
     * @param outName
     *            出力ファイル
     */
    public void forEach(String inName, String outName) {
        final Path inF = Paths.get(inName);
        final Path outF = Paths.get(outName);
        if (!Files.exists(inF) || !doWrite(outF)) {
            return;
        }
        try (BufferedReader br = Files.newBufferedReader(inF, UTF_8);
                BufferedWriter bw = Files.newBufferedWriter(outF, UTF_8)) {
            String str;
            while ((str = br.readLine()) != null) {
                bw.write(effects(this.mapper.apply(str)).toString());
                bw.newLine();
            }
            bw.flush();
        } catch (final IOException e) {
            // TODO　ファイルＩＯに失敗した旨のメッセージを表示する関数を呼び出す。
        }
    }

    /*
     * 登録されている編集ルールを全て実行する。
     */
    private T effects(T src) {
        this.effectors.forEach(ef -> ef.accept(src));
        return src;
    }

    /*
     * 出力ファイルが存在する場合、上書きするかどうかを確認する。
     */
    private boolean doWrite(Path outF) {
        if (!Files.exists(outF)) {
            return true;
        }
        return confirmOverwrite.getAsBoolean();
    }

    private Function<String, T> mapper;

    private BooleanSupplier confirmOverwrite;

    private final List<Consumer<T>> effectors = new ArrayList<>();

}
