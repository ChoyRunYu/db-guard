package cn.com.choy.dbguard.controller;

import cn.com.choy.dbguard.entity.dto.DataSourceDTO;
import cn.com.choy.dbguard.entity.dto.DataSourceListDTO;
import cn.com.choy.dbguard.entity.dto.LabelValueDTO;
import cn.com.choy.dbguard.entity.dto.Result;
import cn.com.choy.dbguard.service.IDataSourceService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据源管理
 *
 * @author choyrunyu
 * @since 2025/03/20
 */
@RestController
@RequestMapping("/db_guard/datasource")
public class DataSourceController {

    @Resource
    private IDataSourceService dataSourceService;


    /**
     * 获取数据源类型列表
     *
     * @return 返回数据源类型列表
     */
    @GetMapping("/list_type")
    public Result<List<String>> listDataSourceType() {
        return Result.success(dataSourceService.listDataSourceType());
    }


    /**
     * 分页获取数据源数据列表
     *
     * @param searchKey 关键词
     * @return 返回分页数据
     */
    @GetMapping("/list")
    public Result<List<DataSourceListDTO>> listDataSource(String searchKey) {
        return Result.success(dataSourceService.listDataSource(searchKey));
    }

    /**
     * 获取数据源配置
     *
     * @param id id
     * @return 返回数据源配置
     */
    @GetMapping("/one")
    public Result<DataSourceDTO> getDataSource(String id) {
        return Result.success(dataSourceService.getDataSource(id));
    }

    /**
     * 保存数据源
     *
     * @param dataSourceDto 待保存的数据源数据
     * @return 返回保存结果
     */
    @PostMapping("/save")
    public Result<String> saveDataSource(@RequestBody DataSourceDTO dataSourceDto) {
        dataSourceService.saveDataSource(dataSourceDto);
        return Result.success("保存成功");
    }

    /**
     * 删除数据源
     *
     * @param id 数据源id
     * @return 返回删除结果
     */
    @PostMapping("/delete")
    public Result<String> delDataSource(String id) {
        dataSourceService.delDataSource(id);
        return Result.success("删除成功");
    }

    /**
     * 获取数据源下拉列表
     *
     * @return 返回数据源下拉列表
     */
    @GetMapping("/options")
    public Result<List<LabelValueDTO>> getDataSourceOptions() {
        return Result.success(dataSourceService.getDataSourceOptions());
    }

}
