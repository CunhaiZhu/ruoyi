package com.ruoyi.cms.mapper;

import com.ruoyi.cms.domain.SiteMsg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 站内消息Mapper接口
 *
 * @author wujiyue
 * @date 2019-11-17
 */
@Mapper
public interface SiteMsgMapper
{
    /**
     * 查询站内消息
     *
     * @param id 站内消息ID
     * @return 站内消息
     */
    public SiteMsg selectSiteMsgById(Long id);

    /**
     * 查询站内消息列表
     *
     * @param siteMsg 站内消息
     * @return 站内消息集合
     */
    public List<SiteMsg> selectSiteMsgList(SiteMsg siteMsg);

    /**
     * 新增站内消息
     *
     * @param siteMsg 站内消息
     * @return 结果
     */
    public int insertSiteMsg(SiteMsg siteMsg);

    /**
     * 修改站内消息
     *
     * @param siteMsg 站内消息
     * @return 结果
     */
    public int updateSiteMsg(SiteMsg siteMsg);

    /**
     * 删除站内消息
     *
     * @param id 站内消息ID
     * @return 结果
     */
    public int deleteSiteMsgById(Long id);

    /**
     * 批量删除站内消息
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSiteMsgByIds(String[] ids);
}
