package pr.study.springboot.bean;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class User extends BaseBean {
    private static final long serialVersionUID = -4938773412870002459L;

    private long id;
    private String name;
    private String email;
    @JSONField(serialize=false) // 这个是fastjson的注解，只有使用fastjson时才生效，可以用来检测是否使用了fastjson
    private Date createTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "User[id=" + id + ", name=" + name + ", email=" + email + ", createTime=" + createTime + "]";
    }
}
