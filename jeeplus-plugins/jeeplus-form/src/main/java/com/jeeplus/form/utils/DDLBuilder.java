package com.jeeplus.form.utils;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.jeeplus.form.constant.TableColumn;
import com.jeeplus.form.service.dto.ColumnDTO;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.ddlutils.Platform;
import org.apache.ddlutils.PlatformFactory;
import org.apache.ddlutils.model.Column;
import org.apache.ddlutils.model.Table;
import org.apache.ddlutils.model.TypeMap;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 表单数据表生成
 */
@Slf4j
@Data
public class DDLBuilder {
    /**
     * 表名
     */
    private String tableName = "";
    /**
     * 当前数据源
     */
    private DataSource dataSource;

    public DDLBuilder(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public DDLBuilder(DataSource dataSource, String tableName) {
        this.dataSource = dataSource;
        this.tableName = tableName;
    }

    /**
     * 删除表
     *
     * @return
     */
    public boolean dropTable(String tableName) {
        if ( StrUtil.isNotBlank ( tableName ) ) {
            try {
                getPlatform ( ).dropTable ( tableName );
            } catch (Exception e) {
                log.error ( "{}", e );
            }
        }
        return true;
    }

    public boolean dropTable() {
        return dropTable ( tableName );
    }

    /**
     * 创建表
     *
     * @return
     */
    public boolean createTable() {
        try {
            Platform platform = getPlatform ( );
            Table eqTable = platform.getTable ( tableName );
            // 如果表已存在就直接返回
            if ( ObjectUtil.isNotNull ( eqTable ) ) {
                return true;
            }
            Table table = new Table ( );
            table.setName ( tableName );

            Column id = new Column ( );
            id.setName ( TableColumn.ID );
            id.setType ( TypeMap.VARCHAR );
            id.setSize ( "64" );
            id.setDescription ( "主键" );
            id.setPrimaryKey ( true );
            id.setRequired ( true );
            table.addColumn ( id );

            Column createById = new Column ( );
            createById.setName ( TableColumn.CREATE_BY_ID );
            createById.setType ( TypeMap.VARCHAR );
            createById.setSize ( "64" );
            createById.setDescription ( "创建人" );
            table.addColumn ( createById );

            Column createTime = new Column ( );
            createTime.setName ( TableColumn.CREATE_TIME );
            createTime.setType ( "DATE" );
            createTime.setDescription ( "创建日期" );
            table.addColumn ( createTime );

            Column updateById = new Column ( );
            updateById.setName ( TableColumn.UPDATE_BY_ID );
            updateById.setType ( TypeMap.VARCHAR );
            updateById.setSize ( "64" );
            updateById.setDescription ( "更新人" );
            table.addColumn ( updateById );

            Column updateTime = new Column ( );
            updateTime.setName ( TableColumn.UPDATE_TIME );
            updateTime.setType ( "DATE" );
            updateTime.setDescription ( "更新日期" );
            table.addColumn ( updateTime );


            Column delFlag = new Column ( );
            delFlag.setName ( TableColumn.DEL_FLAG );
            delFlag.setType ( "INTEGER" );
            delFlag.setDescription ( "删除标记" );
            table.addColumn ( delFlag );

            platform.createTable ( table, false, false );
            return true;
        } catch (Exception e) {
            log.error ( "{}", e );
        }
        return false;
    }

    /**
     * 修改表
     *
     * @param columnList
     */
    public void modifyTable(List <ColumnDTO> columnList) {

        try {
            Platform platform = getPlatform ( );
            Table targetTable = platform.getTable ( tableName );
            HashSet <String> newColumns = new HashSet ( );
            HashMap <String, String> columnTypeMap = new HashMap ( );
            HashMap <String, String> commentMap = new HashMap ( );
            for (ColumnDTO column : columnList) {
                newColumns.add ( column.getModel ( ) );
                columnTypeMap.put ( column.getModel ( ), column.getType ( ) );
                commentMap.put ( column.getModel ( ), column.getName ( ) );
            }

            // 删除旧的列
            for (Column column : targetTable.getColumns ( )) {
                if ( !FormJsonUtils.newInstance ( ).isBasicField ( column.getName ( ) ) ) {
                    targetTable.removeColumn ( column );
                }
            }
            // 添加新列
            for (String newColumn : newColumns) {
                if ( !FormJsonUtils.newInstance ( ).isBasicField ( newColumn ) ) {
                    String type = columnTypeMap.get ( newColumn );
                    Column column = new Column ( );
                    column.setName ( newColumn );
                    if ( type.equals ( "slider" ) || type.equals ( "number" ) || type.equals ( "rate" ) ) {
                        column.setType ( TypeMap.INTEGER );
                    } else if ( type.equals ( "switch" ) ) {
                        column.setType ( TypeMap.BOOLEAN );
                    } else if ( type.equals ( "textarea" ) || type.equals ( "table" ) || type.equals ( "imgupload" ) || type.equals ( "fileupload" ) || type.equals ( "editor" ) ) {
                        column.setType ( TypeMap.LONGVARCHAR );
                    } else {
                        column.setType ( TypeMap.LONGVARCHAR );
                    }
                    column.setDescription ( commentMap.get ( newColumn ) );
                    targetTable.addColumn ( column );
                }
            }

            Table sourceTable = getPlatform ( ).getTable ( tableName );
            platform.alterTable ( sourceTable, targetTable, false );

        } catch (Exception e) {
            log.error ( "{}", e );
        }
    }


    /**
     * 获取数据平台
     *
     * @return
     */
    public Platform getPlatform() {
        Platform platform = PlatformFactory.createNewPlatformInstance ( dataSource );
        return platform;
    }

    /**
     * 获取数据平台
     *
     * @return车
     */
    public String getDataBaseType() {
        Platform platform = PlatformFactory.createNewPlatformInstance ( dataSource );
        return platform.getName ( );
    }


    /**
     * 获取指定表
     *
     * @return
     * @throws SQLException
     */

    public Table getTable(String tableName) throws SQLException {
        return getPlatform ( ).getTable ( tableName );
    }

    /**
     * 获取当前表
     *
     * @return
     * @throws SQLException
     */
    public Table getTable() throws SQLException {
        return getTable ( tableName );
    }

    /**
     * 获取物理数据表列表
     *
     * @return
     */
    public Table[] getTableList() throws SQLException {
        Platform platform = getPlatform ( );
        Table[] tables = platform.getTablesWithoutColumn ( );
        return tables;
    }

    /**
     * 根据表名获取列
     *
     * @param tableName
     * @return
     * @throws SQLException
     */
    public Column[] getTableColumnList(String tableName) throws SQLException {
        return getPlatform ( ).getTableColumnList ( tableName );
    }

    /**
     * 获取当前表的列
     *
     * @return
     * @throws SQLException
     */
    public Column[] getTableColumnList() throws SQLException {
        return getTableColumnList ( tableName );
    }

    /**
     * 同步表格
     *
     * @return
     */
    public boolean syncTable(List <ColumnDTO> columnList) {
        if ( this.createTable ( ) ) {
            this.modifyTable ( columnList );
        }
        return true;
    }


}
