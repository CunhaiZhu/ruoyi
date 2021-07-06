package com.ruoyi.cms.controller;

import com.ruoyi.cms.domain.Ad;
import com.ruoyi.cms.domain.AdMaterial;
import com.ruoyi.cms.plus.utils.ExcelUtil;
import com.ruoyi.cms.service.AdService;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 广告位Controller
 *
 * @author wujiyue
 * @date 2019-11-16
 */
@Api(tags = "广告位Controller")
@RestController
@RequestMapping("/cms/ad")
public class AdController extends BaseController
{
    private String prefix = "cms/ad";

    @Autowired
    private AdService adService;

    @RequiresPermissions("cms:ad:view")
    @GetMapping()
    @ApiIgnore()
    public String ad()
    {
        return prefix + "/ad";
    }

    /**
     * 查询广告位列表
     */
    @RequiresPermissions("cms:ad:list")
    @PostMapping("/list")
    @ResponseBody
    @ApiOperation("查询广告位列表")
    public TableDataInfo list(Ad ad)
    {
        startPage();
        List<Ad> list = adService.selectAdList(ad);
        return getDataTable(list);
    }

    /**
     * 导出广告位列表
     */
    @RequiresPermissions("cms:ad:export")
    @PostMapping("/export")
    @ResponseBody
    @ApiOperation("导出广告位列表")
    public AjaxResult export(Ad ad)
    {
        List<Ad> list = adService.selectAdList(ad);
        ExcelUtil<Ad> util = new ExcelUtil<Ad>(Ad.class);
        return util.exportExcel(list, "ad");
    }

    /**
     * 新增广告位
     */
    @GetMapping("/add")
    @ApiIgnore()
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存广告位
     */
    @RequiresPermissions("cms:ad:add")
    @Log(title = "广告位", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    @ApiOperation("新增保存广告位")
    public AjaxResult addSave(Ad ad)
    {
        return toAjax(adService.insertAd(ad));
    }

    /**
     * 修改广告位
     */
    @GetMapping("/edit/{adId}")
    @ApiIgnore()
    public String edit(@PathVariable("adId") Long adId, ModelMap mmap)
    {
        Ad ad = adService.selectAdById(adId);
        mmap.put("ad", ad);
        return prefix + "/edit";
    }

    /**
     * 修改保存广告位
     */
    @RequiresPermissions("cms:ad:edit")
    @Log(title = "广告位", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    @ApiOperation("修改保存广告位")
    public AjaxResult editSave(Ad ad)
    {
        return toAjax(adService.updateAd(ad));
    }

    /**
     * 删除广告位
     */
    @ApiOperation("删除广告位")
    @RequiresPermissions("cms:ad:remove")
    @Log(title = "广告位", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(@ApiParam("广告位ID")String ids)
    {
        return toAjax(adService.deleteAdByIds(ids));
    }

    /*****************************************分割线****************************************************/


    /**
     * 跳转配置广告页面
     */
    @GetMapping("/adMaterial/{adId}")
    @ApiIgnore()
    public String adMaterial(@PathVariable("adId") String adId, ModelMap mmap)
    {
        mmap.put("adId", adId);
        return prefix + "/adMaterial";
    }

    /**
     * 查询广告位绑定的素材列表
     */
    @ApiOperation("查询广告位绑定的素材列表")
    @PostMapping("/adMaterialList")
    @ResponseBody
    public TableDataInfo adMaterialList(AdMaterial adMaterial)
    {
        List<AdMaterial> list = adService.selectAdMaterialList(adMaterial);
        return getDataTable(list);
    }

    /**
     * 跳转新增广告素材页面
     */
    @GetMapping("/addAdMaterial/{adId}")
    @ApiIgnore()
    public String addAdMaterial(@PathVariable("adId") String adId, ModelMap mmap)
    {
        mmap.put("adId", adId);
        return prefix + "/addAdMaterial";
    }
    /**
     * 新增保存广告位
     */
    @ApiOperation("新增保存广告位")
    @PostMapping("/addAdMaterialSave")
    @ResponseBody
    public AjaxResult addAdMaterialSave(AdMaterial adMaterial)
    {
        return toAjax(adService.insertAdMaterial(adMaterial));
    }
    /**
     * 跳转编辑广告素材页面
     */
    @GetMapping("/editAdMaterial/{id}")
    @ApiIgnore()
    public String editAdMaterial(@PathVariable("id") Long id, ModelMap mmap)
    {
        AdMaterial adMaterial = adService.selectAdMaterialById(id);

        mmap.put("adMaterial", adMaterial);
        return prefix + "/editAdMaterial";
    }

    /**
     * 修改保存广告位素材
     */
    @ApiOperation("修改保存广告位素材")
    @PostMapping("/editAdMaterialSave")
    @ResponseBody
    public AjaxResult editAdMaterialSave(AdMaterial adMaterial)
    {
        return toAjax(adService.updateAdMaterial(adMaterial));
    }

    /**
     * 删除广告位素材
     */
    @ApiOperation("删除广告位素材")
    @PostMapping( "/removeAdMaterial")
    @ResponseBody
    public AjaxResult removeAdMaterial(String ids)
    {
        return toAjax(adService.deleteAdMaterialByIds(ids));
    }


    /**
     * 打开广告素材选择界面
     * @return
     */
    @GetMapping("/selectAdMaterial/{adId}")
    @ApiIgnore()
    public String selectAdMaterial(@PathVariable("adId") String adId, ModelMap mmap)
    {
        mmap.put("adId", adId);
        return prefix + "/selectAdMaterial";
    }

    /**
     * 查询未配置的素材
     * @param adMaterial
     * @return
     */
    @ApiOperation("查询未配置的素材")
    @PostMapping("/unMaterialList")
    @ResponseBody
    public TableDataInfo unMaterialList(AdMaterial adMaterial)
    {
        startPage();
        List<AdMaterial> list = adService.selectAdUnMaterialList(adMaterial);
        return getDataTable(list);
    }


}
