package com.ruoyi.project.fx.comment.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.SqrtFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.nlp.tokenizers.ChineseWordTokenizer;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import com.ruoyi.common.utils.text.Convert;
import com.ruoyi.project.fx.comment.domain.Comment;
import com.ruoyi.project.fx.comment.mapper.CommentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 用户评论 服务层实现
 * 
 * @author fxiao
 * @date 2019-08-13
 */
@Service
public class CommentServiceImpl implements CommentService
{
    private final Logger log= LoggerFactory.getLogger(this.getClass());
	@Autowired
	private CommentMapper commentMapper;

    private RestTemplate restTemplate = new RestTemplate();

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

    @Override
    public void importCommentByUrl(String url) {
	    //先获取总数
        String resultStsr=restTemplate.getForObject(url+"&pageindex=1",String.class);
        resultStsr=resultStsr.substring(resultStsr.indexOf("(")+1,resultStsr.length()-2);
        JSONObject resultObj= JSON.parseObject(resultStsr);
        int pareTotal=resultObj.getJSONObject("data").getInteger("page_total");

        DateFormat df=new SimpleDateFormat("yyyy年MM月dd日");
        String commentContent=null;
        for (int i = 0; i <pareTotal ; i++) {
            int currentPage=i+1;
            resultStsr=restTemplate.getForObject(url+"&pageindex="+currentPage,String.class);
            resultStsr=resultStsr.substring(resultStsr.indexOf("(")+1,resultStsr.length()-2);
            resultObj= JSON.parseObject(resultStsr);
            JSONArray comments = resultObj.getJSONObject("data").getJSONArray("comments");
            for (int j = 0; j < comments.size(); j++) {
                JSONObject commentJsonObj=comments.getJSONObject(j);
                Comment comment=new Comment();
                comment.setCommentContent(commentJsonObj.getString("comment_content"));
                comment.setUserId(commentJsonObj.getInteger("user_id"));
                String dateStr=commentJsonObj.getString("add_time");
                try {
                    comment.setCommentTime(df.parse(dateStr));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                comment.setUserName(commentJsonObj.getString("user_name"));
                log.info("正在保存第{}页的第{}条评论",currentPage,j+1);
                insertComment(comment);
            }
        }
    }

    @Override
    public void downloadWordFrequency(String productCode) throws IOException {
        //创建一个词语解析器,类似于分词
        FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(600);
        frequencyAnalyzer.setMinWordLength(2);
        //这边要注意,引用了中文的解析器
        frequencyAnalyzer.setWordTokenizer(new ChineseWordTokenizer());

        //拿到文档里面分出的词,和词频,建立一个集合存储起来
        Comment comment=new Comment();
        comment.setProductCode(productCode);
        List<Comment>comments=selectCommentList(comment);
        StringBuilder commentCoutents=new StringBuilder();
        for (Comment commentItem : comments) {
            commentCoutents.append(commentItem.getCommentContent());
            commentCoutents.append("\r\n");
        }
        InputStream is=new ByteArrayInputStream(commentCoutents.toString().getBytes());
        List<WordFrequency> wordFrequencies = frequencyAnalyzer.load(is);

        Dimension dimension = new Dimension(800, 800);

        //设置图片相关的属性,这边是大小和形状,更多的形状属性,可以从CollisionMode源码里面查找
        WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(2);

        //这边要注意意思,是设置中文字体的,如果不设置,得到的将会是乱码,
        //这是官方给出的代码没有写的,我这边拓展写一下,字体,大小可以设置
        //具体可以参照Font源码
        java.awt.Font font = new java.awt.Font("STSong-Light", 2, 16);
        wordCloud.setKumoFont(new KumoFont(font));
        wordCloud.setBackgroundColor(new Color(255, 255, 255));
        //因为我这边是生成一个圆形,这边设置圆的半径
        wordCloud.setBackground(new CircleBackground(255));
        //设置词云显示的三种颜色，越靠前设置表示词频越高的词语的颜色
        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN, 30, 30));
        wordCloud.setFontScalar(new SqrtFontScalar(12, 45));
        //将文字写入图片
        wordCloud.build(wordFrequencies);
        //生成图片
        wordCloud.writeToFile("e:temp/chinese_language_circle.png");
    }

}
