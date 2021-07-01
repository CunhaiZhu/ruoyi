package com.ruoyi.cms.controller;

import java.util.Date;
import java.util.List;

import com.ruoyi.common.config.Global;
import com.ruoyi.common.config.ServerConfig;
import com.ruoyi.common.utils.Guid;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.framework.util.ShiroUtils;
import com.ruoyi.system.domain.SysUser;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.cms.domain.Attachment;
import com.ruoyi.cms.service.IAttachmentService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * 附件Controller
 * 
 * @author wujiyue
 * @date 2019-11-01
 */
@Controller
@RequestMapping("/cms/attachment")
public class AttachmentController extends BaseController
{
    private String prefix = "cms/attachment";
    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    private IAttachmentService attachmentService;

    @RequiresPermissions("cms:attachment:view")
    @GetMapping()
    public String attachment()
    {
        return prefix + "/attachment";
    }

    /**
     * 查询附件列表
     */
    @RequiresPermissions("cms:attachment:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(Attachment attachment)
    {
        startPage();
        List<Attachment> list = attachmentService.selectAttachmentList(attachment);
        return getDataTable(list);
    }

    /**
     * 导出附件列表
     */
    @RequiresPermissions("cms:attachment:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Attachment attachment)
    {
        List<Attachment> list = attachmentService.selectAttachmentList(attachment);
        ExcelUtil<Attachment> util = new ExcelUtil<Attachment>(Attachment.class);
        return util.exportExcel(list, "attachment");
    }

    /**
     * 新增附件
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存附件
     */
    @RequiresPermissions("cms:attachment:add")
    @Log(title = "附件", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Attachment attachment)
    {
        attachment.setAttachId(Guid.get());
        return toAjax(attachmentService.insertAttachment(attachment));
    }
    @PostMapping("/upload")
    @ResponseBody
    public AjaxResult uploadFile(MultipartFile file,Attachment attachment) throws Exception
    {
        try
        {
            SysUser user= ShiroUtils.getSysUser();
            // 上传文件路径
            String filePath = Global.getUploadPath();
            // 上传并返回新文件名称
            String fileName = FileUploadUtils.upload(filePath, file);
            String url = serverConfig.getUrl() + fileName;
            AjaxResult ajax = AjaxResult.success();
            ajax.put("fileName", fileName);
            ajax.put("url", url);

            attachment.setFileName(fileName);
            attachment.setFilePath(url);
            attachment.setSize(file.getSize());
            attachment.setUserId(user.getUserId().toString());
            attachment.setCreateBy(user.getUserName());
            attachment.setCreateTime(new Date());
            String ext=FileUploadUtils.getExtension(file);
            attachment.setFileType(ext);
            attachmentService.insertAttachment(attachment);
            ajax.put("guid", attachment.getAttachId());
            return ajax;
        }
        catch (Exception e)
        {
            return AjaxResult.error(e.getMessage());
        }
    }
    /**
     * 修改附件
     */
    @GetMapping("/edit/{attachId}")
    public String edit(@PathVariable("attachId") String attachId, ModelMap mmap)
    {
        Attachment attachment = attachmentService.selectAttachmentById(attachId);
        mmap.put("attachment", attachment);
        return prefix + "/edit";
    }

    /**
     * 修改保存附件
     */
    @RequiresPermissions("cms:attachment:edit")
    @Log(title = "附件", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(Attachment attachment)
    {
        return toAjax(attachmentService.updateAttachment(attachment));
    }

    /**
     * 删除附件
     */
    @RequiresPermissions("cms:attachment:remove")
    @Log(title = "附件", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(attachmentService.deleteAttachmentByIds(ids));
    }


    @PostMapping( "/delete")
    @ResponseBody
    public AjaxResult delete(String ids)
    {
        return toAjax(attachmentService.deleteAttachmentByIds(ids));
    }

}
