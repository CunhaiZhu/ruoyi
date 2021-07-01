package com.ruoyi.cms.controller;

import com.ruoyi.cms.domain.Material;
import com.ruoyi.cms.domain.MaterialUse;
import com.ruoyi.cms.plus.utils.ExcelUtil;
import com.ruoyi.cms.plus.utils.StringUtils;
import com.ruoyi.cms.service.IMaterialService;
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

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 素材Controller
 *
 * @author wujiyue
 * @date 2019-11-05
 */
@Controller
@RequestMapping("/cms/material")
public class MaterialController extends BaseController
{
    private String prefix = "cms/material";

    @Autowired
    private IMaterialService materialService;

    @RequiresPermissions("cms:material:view")
    @GetMapping()
    public String material()
    {
        return prefix + "/material";
    }

    /**
     * 查询素材列表
     */
    @RequiresPermissions("cms:material:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Material material)
    {
        startPage();
        List<Material> list = materialService.selectMaterialList(material);
        return getDataTable(list);
    }

    /**
     * 导出素材列表
     */
    @RequiresPermissions("cms:material:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Material material)
    {
        List<Material> list = materialService.selectMaterialList(material);
        ExcelUtil<Material> util = new ExcelUtil<Material>(Material.class);
        return util.exportExcel(list, "material");
    }

    /**
     * 新增素材
     */
    @GetMapping("/add")
    public String add(ModelMap mmap)
    {
        Material material=new Material();
        material.setGroupId("1");
        material.setGroupName("默认分组");
        mmap.put("material", material);
        return prefix + "/add";
    }

    /**
     * 新增保存素材
     */
    @RequiresPermissions("cms:material:add")
    @Log(title = "素材", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Material material)
    {
        return toAjax(materialService.insertMaterial(material));
    }

    /**
     * 修改素材
     */
    @GetMapping("/edit/{materialId}")
    public String edit(@PathVariable("materialId") String materialId, ModelMap mmap)
    {
        Material material = materialService.selectMaterialById(materialId);
        mmap.put("material", material);
        return prefix + "/edit";
    }

    /**
     * 修改保存素材
     */
    @RequiresPermissions("cms:material:edit")
    @Log(title = "素材", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Material material)
    {
        return toAjax(materialService.updateMaterial(material));
    }

    /**
     * 删除素材
     */
    @RequiresPermissions("cms:material:remove")
    @Log(title = "素材", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        int n=materialService.deleteMaterialByIds(ids);
        if(n==-1){
            return error("当前素材有正在使用中的,无法删除!");
        }
        return success();
    }


    /**
     * 删除素材
     */
    @RequiresPermissions("cms:material:audit")
    @Log(title = "素材审核", businessType = BusinessType.OTHER)
    @PostMapping( "/audit")
    @ResponseBody
    public AjaxResult audit(String ids)
    {
        return toAjax(materialService.auditMaterialByIds(ids));
    }



    /**
     * 跳转使用记录页面
     */
    @RequiresPermissions("cms:material:materialUse")
    @GetMapping("/toUseList/{materialId}")
    public String toUseList(@PathVariable("materialId")String materialId, ModelMap mmap)
    {
        mmap.put("materialId", materialId);
        return prefix + "/materialUse";
    }
    /**
     * 查询使用记录列表
     */
    @PostMapping("/selectMaterialUseList")
    @ResponseBody
    public TableDataInfo selectMaterialUseList(MaterialUse materialUse)
    {
        startPage();
        List<MaterialUse> list = materialService.selectMaterialUseList(materialUse);
        return getDataTable(list);
    }

    /**
     * 删除素材使用记录
     * @param materialUse
     * @return
     */
    @PostMapping( "/deleteMaterialUseBatch")
    @ResponseBody
    public AjaxResult deleteMaterialUseBatch(MaterialUse materialUse)
    {
        materialService.deleteMaterialUseBatch(materialUse);
        return success();
    }

    /**
     * 打开素材选择界面
     * @return
     */
    @GetMapping("/selectMaterialWithGroup")
    public String selectMaterialWithGroup(HttpServletRequest request, ModelMap mmap)
    {
        String materialId=request.getParameter("materialId");
        String materialName=request.getParameter("materialName");
        String materialPath=request.getParameter("materialPath");
        mmap.put("materialId", StringUtils.isNotEmpty(materialId)?materialId:"");
        mmap.put("materialName",StringUtils.isNotEmpty(materialName)?materialName:"");
        mmap.put("materialPath",StringUtils.isNotEmpty(materialPath)?materialPath:"");
        return prefix + "/selectMaterialWithGroup";
    }

}
