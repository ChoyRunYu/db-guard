package cn.com.choy.dbguard.service;

import cn.com.choy.dbguard.entity.dto.DataSourceDTO;
import cn.com.choy.dbguard.entity.dto.DataSourceListDTO;
import cn.com.choy.dbguard.entity.dto.LabelValueDTO;

import java.util.List;

/**
 * 数据源管理接口
 *
 * @author choyrunyu
 * @since 2025/03/18
 */
public interface IDataSourceService {

    /**
     * 获取数据源类型列表
     *
     * @return 返回数据源类型列表
     */
    List<String> listDataSourceType();


    /**
     * 分页获取数据源数据列表
     *
     * @param searchKey 关键词
     * @return 返回分页数据
     */
    List<DataSourceListDTO> listDataSource(String searchKey);

    /**
     * 获取数据源配置
     *
     * @param id id
     * @return 返回数据源配置
     */
    DataSourceDTO getDataSource(String id);

    /**
     * 保存数据源
     *
     * @param dataSourceDto 待保存的数据源数据
     */
    void saveDataSource(DataSourceDTO dataSourceDto);

    /**
     * 删除数据源
     *
     * @param id 数据源id
     */
    void delDataSource(String id);

    /**
     * 获取数据源下拉列表
     *
     * @return 数据源下拉列表
     */
    List<LabelValueDTO> getDataSourceOptions();
}
