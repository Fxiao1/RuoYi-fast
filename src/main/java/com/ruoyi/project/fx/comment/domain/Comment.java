package com.ruoyi.project.fx.comment.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.framework.web.domain.BaseEntity;
import java.util.Date;

/**
 * 用户评论表 fx_comment
 * 
 * @author fxiao
 * @date 2019-08-13
 */
public class Comment extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	/** id */
	private Integer id;
	/** 商品评论 */
	private String commentContent;
	/** 评论者账号 */
	private String userName;
	/** 评论者id */
	private Integer userId;
	/** 评论时间 */
	private Date commentTime;

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Integer getId() 
	{
		return id;
	}
	public void setCommentContent(String commentContent) 
	{
		this.commentContent = commentContent;
	}

	public String getCommentContent() 
	{
		return commentContent;
	}
	public void setUserName(String userName) 
	{
		this.userName = userName;
	}

	public String getUserName() 
	{
		return userName;
	}
	public void setUserId(Integer userId) 
	{
		this.userId = userId;
	}

	public Integer getUserId() 
	{
		return userId;
	}
	public void setCommentTime(Date commentTime) 
	{
		this.commentTime = commentTime;
	}

	public Date getCommentTime() 
	{
		return commentTime;
	}

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("commentContent", getCommentContent())
            .append("userName", getUserName())
            .append("userId", getUserId())
            .append("commentTime", getCommentTime())
            .toString();
    }
}
