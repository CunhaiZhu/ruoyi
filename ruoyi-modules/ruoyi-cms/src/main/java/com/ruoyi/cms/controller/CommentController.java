package com.ruoyi.cms.controller;

import java.util.List;

import com.ruoyi.common.core.utils.excel.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ruoyi.cms.domain.Comment;
import com.ruoyi.cms.service.ICommentService;


/**
 * 评论Controller
 *
 * @author wujiyue
 * @date 2019-11-19
 */
@Controller
@RequestMapping("/cms/comment")
public class CommentController extends BaseController
{
    private String prefix = "cms/comment";

    @Autowired
    private ICommentService commentService;

    @RequiresPermissions("cms:comment:view")
    @GetMapping()
    public String comment()
    {
        return prefix + "/comment";
    }

    /**
     * 查询评论列表
     */
    @RequiresPermissions("cms:comment:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Comment comment)
    {
        startPage();
        List<Comment> list = commentService.selectCommentList(comment);
        return getDataTable(list);
    }

    /**
     * 导出评论列表
     */
    @RequiresPermissions("cms:comment:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Comment comment)
    {
        List<Comment> list = commentService.selectCommentList(comment);
        ExcelUtil<Comment> util = new ExcelUtil<Comment>(Comment.class);
        return util.exportExcel(list, "comment");
    }

    /**
     * 新增评论
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存评论
     */
    @RequiresPermissions("cms:comment:add")
    @Log(title = "评论", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Comment comment)
    {
        return toAjax(commentService.insertComment(comment));
    }

    /**
     * 修改评论
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Comment comment = commentService.selectCommentById(id);
        mmap.put("comment", comment);
        return prefix + "/edit";
    }

    /**
     * 修改保存评论
     */
    @RequiresPermissions("cms:comment:edit")
    @Log(title = "评论", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Comment comment)
    {
        return toAjax(commentService.updateComment(comment));
    }

    /**
     * 删除评论
     */
    @RequiresPermissions("cms:comment:remove")
    @Log(title = "评论", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(commentService.deleteCommentByIds(ids));
    }
}
