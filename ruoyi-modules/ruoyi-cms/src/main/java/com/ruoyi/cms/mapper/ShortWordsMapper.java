package com.ruoyi.cms.mapper;

import com.ruoyi.cms.domain.ShortWords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 励志短语Mapper接口
 *
 * @author wujiyue
 * @date 2019-11-22
 */
@Mapper
public interface ShortWordsMapper
{
    /**
     * 查询励志短语
     *
     * @param id 励志短语ID
     * @return 励志短语
     */
    public ShortWords selectShortWordsById(Long id);

    /**
     * 查询励志短语列表
     *
     * @param shortWords 励志短语
     * @return 励志短语集合
     */
    public List<ShortWords> selectShortWordsList(ShortWords shortWords);

    /**
     * 新增励志短语
     *
     * @param shortWords 励志短语
     * @return 结果
     */
    public int insertShortWords(ShortWords shortWords);

    /**
     * 修改励志短语
     *
     * @param shortWords 励志短语
     * @return 结果
     */
    public int updateShortWords(ShortWords shortWords);

    /**
     * 删除励志短语
     *
     * @param id 励志短语ID
     * @return 结果
     */
    public int deleteShortWordsById(Long id);

    /**
     * 批量删除励志短语
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteShortWordsByIds(String[] ids);
}
