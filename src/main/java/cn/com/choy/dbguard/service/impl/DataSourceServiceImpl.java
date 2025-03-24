package cn.com.choy.dbguard.service.impl;

import cn.com.choy.dbguard.dao.DataSourceDao;
import cn.com.choy.dbguard.entity.dto.DataSourceDTO;
import cn.com.choy.dbguard.entity.dto.DataSourceListDTO;
import cn.com.choy.dbguard.entity.dto.LabelValueDTO;
import cn.com.choy.dbguard.entity.po.DataSourcePO;
import cn.com.choy.dbguard.enums.DBTypeEnums;
import cn.com.choy.dbguard.exception.DBGuardException;
import cn.com.choy.dbguard.service.IDataSourceService;
import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * 数据源管理实现
 *
 * @author choyrunyu
 * @since 2025/03/18
 */
@Service
public class DataSourceServiceImpl implements IDataSourceService {

    @Resource
    private DataSourceDao dataSourceDao;

    /**
     * 获取数据源类型列表
     *
     * @return 返回数据源类型列表
     */
    @Override
    public List<String> listDataSourceType() {
        return Arrays.stream(DBTypeEnums.values()).map(DBTypeEnums::name).collect(Collectors.toList());
    }

    /**
     * 分页获取数据源数据列表
     *
     * @param searchKey 关键词
     * @return 返回分页数据
     */
    @Override
    public List<DataSourceListDTO> listDataSource(String searchKey) {
        List<DataSourcePO> dataSources;
        if (StrUtil.isBlank(searchKey)) {
            dataSources = dataSourceDao.findAll();
        } else {
            dataSources = dataSourceDao.findByDataSourceNameLike(searchKey);
        }

        return dataSources.stream().map(item -> {
            DataSourceListDTO dataSourceListDTO = new DataSourceListDTO();
            BeanUtils.copyProperties(item, dataSourceListDTO);
            return dataSourceListDTO;
        }).collect(Collectors.toList());
    }

    /**
     * 获取数据源配置
     *
     * @param id id
     * @return 返回数据源配置
     */
    @Override
    public DataSourceDTO getDataSource(String id) {
        DataSourcePO dataSource = dataSourceDao.findById(id).orElseThrow(() -> new DBGuardException("数据源配置不存在"));
        DataSourceDTO result = new DataSourceDTO();
        BeanUtils.copyProperties(dataSource, result);
        return result;
    }

    /**
     * 保存数据源
     *
     * @param dataSourceDto 待保存的数据源数据
     */
    @Override
    public void saveDataSource(DataSourceDTO dataSourceDto) {
        DataSourcePO dataSource;
        if (StrUtil.isBlank(dataSourceDto.getId())) {
            dataSource = new DataSourcePO();
            BeanUtils.copyProperties(dataSourceDto, dataSource);
            dataSource.setId(UUID.randomUUID().toString());
        } else {
            dataSource = dataSourceDao.findById(dataSourceDto.getId())
                    .orElseThrow(() -> new DBGuardException("数据源配置不存在"));
            BeanUtils.copyProperties(dataSourceDto, dataSource);
        }
        dataSourceDao.save(dataSource);
    }

    /**
     * 删除数据源
     *
     * @param id 数据源id
     */
    @Override
    public void delDataSource(String id) {
        dataSourceDao.deleteById(id);
    }

    /**
     * 获取数据源下拉框选项
     *
     * @return 返回数据源下拉框选项
     */
    @Override
    public List<LabelValueDTO> getDataSourceOptions() {
        return dataSourceDao.findAll().stream().map(item -> {
            LabelValueDTO labelValueDTO = new LabelValueDTO();
            labelValueDTO.setLabel(item.getDataSourceName());
            labelValueDTO.setValue(item.getId());
            return labelValueDTO;
        }).collect(Collectors.toList());
    }
}
