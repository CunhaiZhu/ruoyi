package com.ruoyi.cms.controller;

import com.ruoyi.cms.domain.Filetype;
import com.ruoyi.cms.plus.utils.ExcelUtil;
import com.ruoyi.cms.service.IFiletypeService;
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
 * 文件类型Controller
 *
 * @author wujiyue
 * @date 2019-11-01
 */
@RestController
@RequestMapping("/cms/filetype")
public class FiletypeController extends BaseController
{
    private String prefix = "cms/filetype";

    @Autowired
    private IFiletypeService filetypeService;

    @RequiresPermissions("cms:filetype:view")
    @GetMapping()
    public String filetype()
    {
        return prefix + "/filetype";
    }

    /**
     * 查询文件类型列表
     */
    @RequiresPermissions("cms:filetype:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Filetype filetype)
    {
        startPage();
        List<Filetype> list = filetypeService.selectFiletypeList(filetype);
        return getDataTable(list);
    }

    /**
     * 导出文件类型列表
     */
    @RequiresPermissions("cms:filetype:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Filetype filetype)
    {
        List<Filetype> list = filetypeService.selectFiletypeList(filetype);
        ExcelUtil<Filetype> util = new ExcelUtil<Filetype>(Filetype.class);
        return util.exportExcel(list, "filetype");
    }

    /**
     * 新增文件类型
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存文件类型
     */
    @RequiresPermissions("cms:filetype:add")
    @Log(title = "文件类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Filetype filetype)
    {
        return toAjax(filetypeService.insertFiletype(filetype));
    }

    /**
     * 修改文件类型
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        Filetype filetype = filetypeService.selectFiletypeById(id);
        mmap.put("filetype", filetype);
        return prefix + "/edit";
    }

    /**
     * 修改保存文件类型
     */
    @RequiresPermissions("cms:filetype:edit")
    @Log(title = "文件类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Filetype filetype)
    {
        return toAjax(filetypeService.updateFiletype(filetype));
    }

    /**
     * 删除文件类型
     */
    @RequiresPermissions("cms:filetype:remove")
    @Log(title = "文件类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(filetypeService.deleteFiletypeByIds(ids));
    }

    /*@GetMapping( "/selectDictData")
    @ResponseBody
    public Object selectDictData(){
        return filetypeService.selectDictData();
    }*/
}
