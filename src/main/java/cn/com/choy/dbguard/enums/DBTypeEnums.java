package cn.com.choy.dbguard.enums;


import cn.com.choy.dbguard.exception.DBGuardException;
import lombok.Getter;

/**
 * 数据库类型枚举
 *
 * @author choyrunyu
 * @since 2025/03/16
 */
@Getter
public enum DBTypeEnums {



    MYSQL("MYSQL", "com.mysql.cj.jdbc.Driver"),
    ORACLE("ORACLE", "oracle.jdbc.OracleDriver");

    private final String name;
    private final String className;

    DBTypeEnums(String name, String className) {
        this.name = name;
        this.className = className;
    }

    public static DBTypeEnums getDBTypeEnums(String name) {
        for (DBTypeEnums dbTypeEnums : DBTypeEnums.values()) {
            if (dbTypeEnums.getName().equals(name)) {
                return dbTypeEnums;
            }
        }
        throw new DBGuardException("不支持的数据库类型");
    }

}
