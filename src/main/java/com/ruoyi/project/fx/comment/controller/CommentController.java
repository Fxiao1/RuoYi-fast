package com.ruoyi.project.fx.comment.controller;

import java.util.List;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.framework.aspectj.lang.annotation.Log;
import com.ruoyi.framework.aspectj.lang.enums.BusinessType;
import com.ruoyi.project.fx.comment.domain.Comment;
import com.ruoyi.project.fx.comment.service.CommentService;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 用户评论信息操作处理
 * 
 * @author fxiao
 * @date 2019-08-13
 */
@Controller
@RequestMapping("/fx/comment")
public class CommentController extends BaseController
{
    private String prefix = "fx/comment";
	
	@Autowired
	private CommentService commentService;
	
	@RequiresPermissions("fx:comment:view")
	@GetMapping()
	public String comment()
	{
	    return prefix + "/comment";
	}
	
	/**
	 * 查询用户评论列表
	 */
	@RequiresPermissions("fx:comment:list")
	@PostMapping("/list")
	@ResponseBody
	public TableDataInfo list(Comment comment)
	{
		startPage();
        List<Comment> list = commentService.selectCommentList(comment);
		return getDataTable(list);
	}
	
	
	/**
	 * 导出用户评论列表
	 */
	@RequiresPermissions("fx:comment:export")
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(Comment comment)
    {
    	List<Comment> list = commentService.selectCommentList(comment);
        ExcelUtil<Comment> util = new ExcelUtil<Comment>(Comment.class);
        return util.exportExcel(list, "comment");
    }
	
	/**
	 * 新增用户评论
	 */
	@GetMapping("/add")
	public String add()
	{
	    return prefix + "/add";
	}
	
	/**
	 * 新增保存用户评论
	 */
	@RequiresPermissions("fx:comment:add")
	@Log(title = "用户评论", businessType = BusinessType.INSERT)
	@PostMapping("/add")
	@ResponseBody
	public AjaxResult addSave(Comment comment)
	{		
		return toAjax(commentService.insertComment(comment));
	}

	/**
	 * 修改用户评论
	 */
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable("id") Integer id, ModelMap mmap)
	{
		Comment comment = commentService.selectCommentById(id);
		mmap.put("comment", comment);
	    return prefix + "/edit";
	}
	
	/**
	 * 修改保存用户评论
	 */
	@RequiresPermissions("fx:comment:edit")
	@Log(title = "用户评论", businessType = BusinessType.UPDATE)
	@PostMapping("/edit")
	@ResponseBody
	public AjaxResult editSave(Comment comment)
	{		
		return toAjax(commentService.updateComment(comment));
	}
	
	/**
	 * 删除用户评论
	 */
	@RequiresPermissions("fx:comment:remove")
	@Log(title = "用户评论", businessType = BusinessType.DELETE)
	@PostMapping( "/remove")
	@ResponseBody
	public AjaxResult remove(String ids)
	{		
		return toAjax(commentService.deleteCommentByIds(ids));
	}
	
}
