package com.ruoyi.project.fx.comment.mapper;

import com.ruoyi.project.fx.comment.domain.Comment;
import java.util.List;	

/**
 * 用户评论 数据层
 * 
 * @author fxiao
 * @date 2019-08-13
 */
public interface CommentMapper 
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
     * 删除用户评论
     * 
     * @param id 用户评论ID
     * @return 结果
     */
	public int deleteCommentById(Integer id);
	
	/**
     * 批量删除用户评论
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	public int deleteCommentByIds(String[] ids);
	
}