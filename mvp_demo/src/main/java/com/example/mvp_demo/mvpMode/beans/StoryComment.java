package com.example.mvp_demo.mvpMode.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yangfan on 2017/3/28.
 */

public class StoryComment extends BaseBean implements Serializable {

    private List<Comment> comments;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public static class Comment extends BaseBean implements Serializable{
        String author;
        String content;
        String avatar;
        long time;
        int likes;
        String type; //长评为long；短评为short；
        ReplyTo reply_to;

        public Comment(String type) {
            this.type = type;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getLikes() {
            return likes;
        }

        public void setLikes(int likes) {
            this.likes = likes;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public ReplyTo getReply_to() {
            return reply_to;
        }

        public void setReply_to(ReplyTo reply_to) {
            this.reply_to = reply_to;
        }

        public static class ReplyTo extends BaseBean implements Serializable{
            String content;
            int status;
            String author;
            String err_msg;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public String getErr_msg() {
                return err_msg;
            }

            public void setErr_msg(String err_msg) {
                this.err_msg = err_msg;
            }
        }
    }

}
