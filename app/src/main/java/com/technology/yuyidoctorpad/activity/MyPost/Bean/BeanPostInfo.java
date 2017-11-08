package com.technology.yuyidoctorpad.activity.MyPost.Bean;

import java.util.List;

/**
 * Created by wanyu on 2017/11/7.
 */

public class BeanPostInfo {

    /**
     * result : {"commentList":[{"createTimeString":"2017-11-07 15:49:10","updateTimeString":"","isLike":false,"contentId":null,"pid":null,"avatar":"/static/image/N3.jpeg","content":"解决","likeNum":null,"trueName":"刘海东","physicianId":null,"commentType":null,"id":367}],"createTimeString":"2017-11-07 15:48:35","updateTimeString":"","isLike":true,"avatar":"/static/image/avatar.jpeg","title":"滚滚滚","shareNum":null,"content":"我们的事","picture":"/static/image/2017117/abe28b45301940c4888933b4e83255a0.jpg;/static/image/2017117/b5ef63715de74b5da63e6e8cae791150.jpg;/static/image/2017117/a79b19a122bb4c81b2a0f6b10dae53c8.jpg;/static/image/2017117/bfe710bcb58c4ba69a2a9bb680ffee94.jpg;/static/image/2017117/502745d393ca4a8f9745365784564bea.jpg;/static/image/2017117/a827d2e39be24171a86f280645ba364f.jpg;","likeNum":1,"commentNum":1,"trueName":"张医生","physicianId":35,"id":133}
     * code : 0
     */

    private ResultBean result;
    private String code;

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static class ResultBean {
        /**
         * commentList : [{"createTimeString":"2017-11-07 15:49:10","updateTimeString":"","isLike":false,"contentId":null,"pid":null,"avatar":"/static/image/N3.jpeg","content":"解决","likeNum":null,"trueName":"刘海东","physicianId":null,"commentType":null,"id":367}]
         * createTimeString : 2017-11-07 15:48:35
         * updateTimeString :
         * isLike : true
         * avatar : /static/image/avatar.jpeg
         * title : 滚滚滚
         * shareNum : null
         * content : 我们的事
         * picture : /static/image/2017117/abe28b45301940c4888933b4e83255a0.jpg;/static/image/2017117/b5ef63715de74b5da63e6e8cae791150.jpg;/static/image/2017117/a79b19a122bb4c81b2a0f6b10dae53c8.jpg;/static/image/2017117/bfe710bcb58c4ba69a2a9bb680ffee94.jpg;/static/image/2017117/502745d393ca4a8f9745365784564bea.jpg;/static/image/2017117/a827d2e39be24171a86f280645ba364f.jpg;
         * likeNum : 1
         * commentNum : 1
         * trueName : 张医生
         * physicianId : 35
         * id : 133
         */

        private String createTimeString;
        private String updateTimeString;
        private boolean isLike;
        private String avatar;
        private String title;
        private Object shareNum;
        private String content;
        private String picture;
        private int likeNum;
        private int commentNum;
        private String trueName;
        private int physicianId;
        private int id;
        private List<CommentListBean> commentList;

        public String getCreateTimeString() {
            return createTimeString;
        }

        public void setCreateTimeString(String createTimeString) {
            this.createTimeString = createTimeString;
        }

        public String getUpdateTimeString() {
            return updateTimeString;
        }

        public void setUpdateTimeString(String updateTimeString) {
            this.updateTimeString = updateTimeString;
        }

        public boolean isIsLike() {
            return isLike;
        }

        public void setIsLike(boolean isLike) {
            this.isLike = isLike;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Object getShareNum() {
            return shareNum;
        }

        public void setShareNum(Object shareNum) {
            this.shareNum = shareNum;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getCommentNum() {
            return commentNum;
        }

        public void setCommentNum(int commentNum) {
            this.commentNum = commentNum;
        }

        public String getTrueName() {
            return trueName;
        }

        public void setTrueName(String trueName) {
            this.trueName = trueName;
        }

        public int getPhysicianId() {
            return physicianId;
        }

        public void setPhysicianId(int physicianId) {
            this.physicianId = physicianId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<CommentListBean> getCommentList() {
            return commentList;
        }

        public void setCommentList(List<CommentListBean> commentList) {
            this.commentList = commentList;
        }

        public static class CommentListBean {
            /**
             * createTimeString : 2017-11-07 15:49:10
             * updateTimeString :
             * isLike : false
             * contentId : null
             * pid : null
             * avatar : /static/image/N3.jpeg
             * content : 解决
             * likeNum : null
             * trueName : 刘海东
             * physicianId : null
             * commentType : null
             * id : 367
             */

            private String createTimeString;
            private String updateTimeString;
            private boolean isLike;
            private Object contentId;
            private Object pid;
            private String avatar;
            private String content;
            private String likeNum;
            private String trueName;
            private Object physicianId;
            private Object commentType;
            private int id;

            public String getCreateTimeString() {
                return createTimeString;
            }

            public void setCreateTimeString(String createTimeString) {
                this.createTimeString = createTimeString;
            }

            public String getUpdateTimeString() {
                return updateTimeString;
            }

            public void setUpdateTimeString(String updateTimeString) {
                this.updateTimeString = updateTimeString;
            }

            public boolean isIsLike() {
                return isLike;
            }

            public void setIsLike(boolean isLike) {
                this.isLike = isLike;
            }

            public Object getContentId() {
                return contentId;
            }

            public void setContentId(Object contentId) {
                this.contentId = contentId;
            }

            public Object getPid() {
                return pid;
            }

            public void setPid(Object pid) {
                this.pid = pid;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getLikeNum() {
                return likeNum;
            }

            public void setLikeNum(String likeNum) {
                this.likeNum = likeNum;
            }

            public String getTrueName() {
                return trueName;
            }

            public void setTrueName(String trueName) {
                this.trueName = trueName;
            }

            public Object getPhysicianId() {
                return physicianId;
            }

            public void setPhysicianId(Object physicianId) {
                this.physicianId = physicianId;
            }

            public Object getCommentType() {
                return commentType;
            }

            public void setCommentType(Object commentType) {
                this.commentType = commentType;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
