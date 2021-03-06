package com.ruoyi.cms.mapper;

import com.ruoyi.cms.domain.ArticleTemplate;
import com.ruoyi.cms.domain.Tags;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 文章模板Mapper接口
 *
 * @author markbro
 * @date 2019-12-31
 */
@Mapper
public interface ArticleTemplateMapper
{
    /**
     * 查询文章模板
     *
     * @param id 文章模板ID
     * @return 文章模板
     */
    public ArticleTemplate selectArticleTemplateById(Long id);

    /**
     * 查询文章模板列表
     *
     * @param articleTemplate 文章模板
     * @return 文章模板集合
     */
    public List<ArticleTemplate> selectArticleTemplateList(ArticleTemplate articleTemplate);

    /**
     * 新增文章模板
     *
     * @param articleTemplate 文章模板
     * @return 结果
     */
    public int insertArticleTemplate(ArticleTemplate articleTemplate);

    /**
     * 修改文章模板
     *
     * @param articleTemplate 文章模板
     * @return 结果
     */
    public int updateArticleTemplate(ArticleTemplate articleTemplate);

    /**
     * 删除文章模板
     *
     * @param id 文章模板ID
     * @return 结果
     */
    public int deleteArticleTemplateById(Long id);

    /**
     * 批量删除文章模板
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteArticleTemplateByIds(String[] ids);


    /**
     * 查询文字模板标签
     * @return
     */
    public  List<Tags> selectArticleTemplateTags();
}
