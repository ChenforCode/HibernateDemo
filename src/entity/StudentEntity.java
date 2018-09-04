package entity;

import javax.persistence.*;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "student", schema = "hibernate", catalog = "")
public class StudentEntity {
    private int sid;
    private String sname;
    private String gender;
    private Date birthday;
    private String address;
    private Blob picture;

    @Id
    @Column(name = "sid", nullable = false)
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    @Basic
    @Column(name = "sname", nullable = true, length = 20)
    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    @Basic
    @Column(name = "gender", nullable = true, length = 20)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Basic
    @Column(name = "birthday", nullable = false)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "address", nullable = false, length = 20)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "picture", nullable = true)
    public Blob getPicture() {
        return picture;
    }

    public void setPicture(Blob picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEntity that = (StudentEntity) o;
        return sid == that.sid &&
                Objects.equals(sname, that.sname) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(address, that.address) &&
                Objects.equals(picture, that.picture);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sid, sname, gender, birthday, address, picture);
    }


    //带参数的构造方法
    public StudentEntity(int sid, String sname, String gender, Date birthday, String address, Blob picture) {
        this.sid = sid;
        this.sname = sname;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.picture = picture;
    }

    //无参构造方法
    public StudentEntity() {
    }

    public StudentEntity(int sid, String sname, String gender, Date birthday, String address) {
        this.sid = sid;
        this.sname = sname;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "sid=" + sid +
                ", sname='" + sname + '\'' +
                ", gender='" + gender + '\'' +
                ", birthday=" + birthday +
                ", address='" + address + '\'' +
                ", picture=" + picture +
                '}';
    }
}
