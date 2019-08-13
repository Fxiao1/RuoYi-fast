package com.ruoyi.project.fx.comment.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.project.fx.comment.mapper.CommentMapper;
import com.ruoyi.project.fx.comment.domain.Comment;
import com.ruoyi.common.utils.text.Convert;

/**
 * 用户评论 服务层实现
 * 
 * @author fxiao
 * @date 2019-08-13
 */
@Service
public class CommentServiceImpl implements CommentService
{
	@Autowired
	private CommentMapper commentMapper;

	/**
     * 查询用户评论信息
     * 
     * @param id 用户评论ID
     * @return 用户评论信息
     */
    @Override
	public Comment selectCommentById(Integer id)
	{
	    return commentMapper.selectCommentById(id);
	}
	
	/**
     * 查询用户评论列表
     * 
     * @param comment 用户评论信息
     * @return 用户评论集合
     */
	@Override
	public List<Comment> selectCommentList(Comment comment)
	{
	    return commentMapper.selectCommentList(comment);
	}
	
    /**
     * 新增用户评论
     * 
     * @param comment 用户评论信息
     * @return 结果
     */
	@Override
	public int insertComment(Comment comment)
	{
	    return commentMapper.insertComment(comment);
	}
	
	/**
     * 修改用户评论
     * 
     * @param comment 用户评论信息
     * @return 结果
     */
	@Override
	public int updateComment(Comment comment)
	{
	    return commentMapper.updateComment(comment);
	}

	/**
     * 删除用户评论对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
	@Override
	public int deleteCommentByIds(String ids)
	{
		return commentMapper.deleteCommentByIds(Convert.toStrArray(ids));
	}
	
}
