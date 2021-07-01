package com.ruoyi.cms.service.impl;

import com.ruoyi.cms.domain.Article;
import com.ruoyi.cms.domain.Category;
import com.ruoyi.cms.mapper.CategoryMapper;
import com.ruoyi.cms.service.ICategoryService;
import com.ruoyi.common.core.domain.Ztree;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 栏目分类Service业务层处理
 * 
 * @author wujiyue
 * @date 2019-10-28
 */
@Service
public class CategoryServiceImpl implements ICategoryService
{
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 查询栏目分类
     * 
     * @param categoryId 栏目分类ID
     * @return 栏目分类
     */
    @Override
    public Category selectCategoryById(Long categoryId)
    {
        return categoryMapper.selectCategoryById(categoryId);
    }

    /**
     * 查询栏目分类列表
     * 
     * @param category 栏目分类
     * @return 栏目分类
     */
    @Override
    public List<Category> selectCategoryList(Category category)
    {
        return categoryMapper.selectCategoryList(category);
    }

    /**
     * 新增栏目分类
     * 
     * @param category 栏目分类
     * @return 结果
     */
    @Override
    @Transactional
    public int insertCategory(Category category)
    {
        category.setCreateTime(DateUtils.getNowDate());
        SysUser user= ShiroUtils.getSysUser();
        category.setCreateBy(user.getUserId().toString());
        int n=categoryMapper.insertCategory(category);
        //更新parentids
        Long pid=category.getParentId();
        Category p=null;
        if(pid==null){
            p=categoryMapper.selectCategoryById(pid);
        }
        String ancestors="";
        if(p!=null){
            ancestors = p.getAncestors();
            ancestors += category.getCategoryId()+",";
        }else{
            ancestors=category.getCategoryId()+",";
        }
        int sort=0;
        if(pid!=null){
            sort=categoryMapper.selectMaxSort(pid.toString());
        }else{
            sort=categoryMapper.selectMaxSortRoot();
        }
        sort++;
        category.setSort(sort);
        category.setAncestors(ancestors);
        n=categoryMapper.updateCategory(category);
        return n;
    }

    /**
     * 修改栏目分类
     * 
     * @param category 栏目分类
     * @return 结果
     */
    @Override
    public int updateCategory(Category category)
    {
        category.setUpdateTime(DateUtils.getNowDate());
        SysUser user= ShiroUtils.getSysUser();
        category.setUpdateBy(user.getUserId().toString());
        return categoryMapper.updateCategory(category);
    }

    /**
     * 删除栏目分类对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteCategoryByIds(String ids)
    {
        return categoryMapper.deleteCategoryByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除栏目分类信息
     * 
     * @param categoryId 栏目分类ID
     * @return 结果
     */
    @Override
    public int deleteCategoryById(String categoryId)
    {
        return categoryMapper.deleteCategoryById(categoryId);
    }

    /**
     * 查询栏目分类树列表
     * 
     * @return 所有栏目分类信息
     */
    @Override
    public List<Ztree> selectCategoryTree()
    {
        List<Category> categoryList = categoryMapper.selectCategoryList(new Category());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (Category category : categoryList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(category.getCategoryId());
            ztree.setpId(category.getParentId());
            ztree.setName(category.getCategoryName());
            ztree.setTitle(category.getCategoryName());
            ztrees.add(ztree);
        }
        return ztrees;
    }

    @Override
    public List<Category> selectNavCategories(Category category) {
        return categoryMapper.selectNavCategories(category);
    }

    @Override
    public List<Ztree> selectCategoryTreeWithArticle(String rootCategoryId) {
        List<Category> categoryList =null;
        //List<Ztree> ztrees = new ArrayList<Ztree>();
        LinkedList<Ztree> ztrees=new LinkedList();
        if(StringUtils.isEmpty(rootCategoryId)){
            categoryList = categoryMapper.selectCategoryList(new Category());
            Ztree ztree = new Ztree();
            ztree.setId(0L);
            ztree.setpId(null);
            ztree.setName("root");
            ztree.setTitle("root");
            ztree.setType("root");
            ztrees.add(ztree);
        }else{
            Category rootCategory=categoryMapper.selectCategoryById(Long.valueOf(rootCategoryId));
            categoryList = categoryMapper.selectCategoryListByAncestors(rootCategory.getCategoryId().toString());
        }
        for (Category category : categoryList) {
            Ztree ztree = new Ztree();
            ztree.setId(category.getCategoryId());
            if(category.getParentId()==null){
                ztree.setpId(0L);
            }else{
                ztree.setpId(category.getParentId());
            }
            ztree.setName(category.getCategoryName());
            ztree.setTitle(category.getCategoryName());
            ztree.setType("category");
            ztrees.add(ztree);
        }
            for(Category category : categoryList){
                List<Article> list = categoryMapper.selectArticlesByCategoryId(category.getCategoryId().toString());
                for(Article article : list) {
                    Ztree ztree = new Ztree();
                    ztree.setId(Long.valueOf(article.getId()));
                    ztree.setpId(Long.valueOf(article.getCategoryId()));
                    ztree.setName(article.getTitle());
                    ztree.setTitle(article.getTitle());
                    ztree.setType("article");
                    ztrees.addFirst(ztree);
                }
            }

        return ztrees;
    }

    @Override
    public List<Category> selectNavCategoriesTop(Category category) {
        return categoryMapper.selectNavCategoriesTop(category);
    }

    @Override
    public int selectChildrenCount(String categoryId) {
        return categoryMapper.selectChildrenCount(categoryId);
    }
}
