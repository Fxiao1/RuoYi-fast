package com.ruoyi.project.fx.comment.service;

import com.ruoyi.project.fx.comment.domain.Comment;

import java.io.IOException;
import java.util.List;

/**
 * 用户评论 服务层
 * 
 * @author fxiao
 * @date 2019-08-13
 */
public interface CommentService
{
	/**
     * 查询用户评论信息
     * 
     * @param id 用户评论ID
     * @return 用户评论信息
     */
	public Comment selectCommentById(Integer id);
	
	/**
     * 查询用户评论列表
     * 
     * @param comment 用户评论信息
     * @return 用户评论集合
     */
	public List<Comment> selectCommentList(Comment comment);
	
	/**
     * 新增用户评论
     * 
     * @param comment 用户评论信息
     * @return 结果
     */
	public int insertComment(Comment comment);
	
	/**
     * 修改用户评论
     * 
     * @param comment 用户评论信息
     * @return 结果
     */
	public int updateComment(Comment comment);
		
	/**
     * 删除用户评论信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCommentByIds(String ids);
	/**
	 * 通过url 导入评论到数据库
	 * @Author Fxiao
	 * @Description
	 * @Date 18:25 2019/8/16
	 * @param url
	 * @return void
	 **/
	public void importCommentByUrl(String url);
	/**
	 * 生成下载词云
	 * @Author Fxiao
	 * @Description
	 * @Date 17:52 2019/8/18
	 * @param productCode 产品代号
	 * @return void
	 **/
	public void downloadWordFrequency(String productCode) throws IOException;
	
}
