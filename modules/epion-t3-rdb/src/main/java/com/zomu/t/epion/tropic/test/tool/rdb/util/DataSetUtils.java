package com.zomu.t.epion.tropic.test.tool.rdb.util;

import com.zomu.t.epion.tropic.test.tool.core.exception.SystemException;
import com.zomu.t.epion.tropic.test.tool.rdb.message.RdbMessages;
import com.zomu.t.epion.tropic.test.tool.rdb.type.DataSetType;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;

public final class DataSetUtils {

    /**
     * シングルトンインスタンス.
     */
    private static final DataSetUtils instance = new DataSetUtils();

    /**
     * プライベートコンストラクタ.
     */
    private DataSetUtils() {
        // Do Nothing...
    }

    /**
     * シングルトンインスタンスを取得.
     *
     * @return シングルトンインスタンス
     */
    public static DataSetUtils getInstance() {
        return instance;
    }

    /**
     * DataSet読込.
     *
     * @param path        パス
     * @param dataSetType DataSet種別
     * @return DataSet
     */
    public IDataSet readDataSet(Path path, DataSetType dataSetType) {

        // データセット種別が解決できなかった場合はエラー
        if (dataSetType == null) {
            throw new SystemException(RdbMessages.RDB_ERR_0007, dataSetType);
        }

        // データセット読み込み
        IDataSet iDataSet = null;

        try (FileInputStream fis = new FileInputStream(path.toFile())) {
            switch (dataSetType) {
                case CSV:
                    // TODO
                    // iDataSet = new CsvDataSet(dataSetPath.toFile());
                    //break;
                    throw new SystemException(RdbMessages.RDB_ERR_0008);
                case XML:
                    FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
                    builder.setColumnSensing(true);
                    iDataSet = builder.build(fis);
                    break;
                case EXCEL:
                    iDataSet = new XlsDataSet(fis);
                    break;
                default:
                    // ありえない
                    throw new SystemException(RdbMessages.RDB_ERR_0007, dataSetType);
            }
            return iDataSet;
        } catch (IOException | DataSetException e) {
            throw new SystemException(RdbMessages.RDB_ERR_0016, path.toString());
        }
    }

}
