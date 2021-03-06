package com.ruoyi.cms.controller;

import com.ruoyi.cms.domain.FriendLink;
import com.ruoyi.cms.plus.utils.ExcelUtil;
import com.ruoyi.cms.service.IFriendLinkService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 友情链接Controller
 *
 * @author wujiyue
 * @date 2019-11-16
 */
@RestController
@RequestMapping("/cms/friendLink")
public class FriendLinkController extends BaseController
{
    private String prefix = "cms/friendLink";

    @Autowired
    private IFriendLinkService friendLinkService;

    @RequiresPermissions("cms:friendLink:view")
    @GetMapping()
    public String friendLink()
    {
        return prefix + "/friendLink";
    }

    /**
     * 查询友情链接列表
     */
    @RequiresPermissions("cms:friendLink:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(FriendLink friendLink)
    {
        startPage();
        List<FriendLink> list = friendLinkService.selectFriendLinkList(friendLink);
        return getDataTable(list);
    }

    /**
     * 导出友情链接列表
     */
    @RequiresPermissions("cms:friendLink:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(FriendLink friendLink)
    {
        List<FriendLink> list = friendLinkService.selectFriendLinkList(friendLink);
        ExcelUtil<FriendLink> util = new ExcelUtil<FriendLink>(FriendLink.class);
        return util.exportExcel(list, "friendLink");
    }

    /**
     * 新增友情链接
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存友情链接
     */
    @RequiresPermissions("cms:friendLink:add")
    @Log(title = "友情链接", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(FriendLink friendLink)
    {
        return toAjax(friendLinkService.insertFriendLink(friendLink));
    }

    /**
     * 修改友情链接
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        FriendLink friendLink = friendLinkService.selectFriendLinkById(id);
        mmap.put("friendLink", friendLink);
        return prefix + "/edit";
    }

    /**
     * 修改保存友情链接
     */
    @RequiresPermissions("cms:friendLink:edit")
    @Log(title = "友情链接", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(FriendLink friendLink)
    {
        return toAjax(friendLinkService.updateFriendLink(friendLink));
    }

    /**
     * 删除友情链接
     */
    @RequiresPermissions("cms:friendLink:remove")
    @Log(title = "友情链接", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(friendLinkService.deleteFriendLinkByIds(ids));
    }
}
