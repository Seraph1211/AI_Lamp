package com.coolweather.ai_lamp.dynamic;

public class CommentBean {
    /**
     * commentId : 8
     * articleId : 10
     * content : 测试评论
     * uid : 34
     * creatTime : 2019-02-09T11:35:36.000+0000
     */

    private int commentId;
    private int articleId;
    private String content;
    private int uid;
    private String createTime;

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String creatTime) {
        this.createTime = creatTime;
    }
}
