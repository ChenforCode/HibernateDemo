

/*Hibernate使用的时候，是先建立数据库，然后在左下角的Persistence(持久化)这个按钮上
* 点击，建立对应的持久化类，在建立这个类的同时，会建立好hbm.xlm文件，并加入到cfg.xml里*/

package test;

import entity.StudentEntity;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

//啊哈哈注意这个地方，util包下面的Date是不能强制转换成sql包下的Date的
import java.io.*;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

public class StudentTest {
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @Before
    public void init(){

        //创建配置对象
        Configuration configuration = new Configuration().configure();

        //创建服务对象
        //注意这句话，在Hibernate 5.x之前是可以使用的，但是后来有了新的写法
        //ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();

        //创建会话工厂,这一句话也有相应的改动
        //sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        sessionFactory =  new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        //创建会话对象
        session = sessionFactory.openSession();

        //开启事务
        transaction = session.beginTransaction();
    }

    @After
    public void destory(){
        transaction.commit();   //提交事务
        session.close();        //关闭会话
        sessionFactory.close(); //关闭会话工厂
    }

    @Test
    public void testSaveStudent(){
        StudentEntity studentEntity = new StudentEntity(1, "刘琛", "男", new Date(), "河南");
        //保存对象进入数据库
        session.save(studentEntity);

        //System.out.println("今天改不完了，明天再改把！");

        /**/
    }

    @Test
    public void testSaveStudent2 (){
        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setSname("111");
        studentEntity.setGender("2");
        studentEntity.setBirthday(new Date());
        studentEntity.setAddress("333");
        session.save(studentEntity);
    }

    //这个Test方法暂时先没有完成
    /*
    * 用bolb存储图片失败
    *
    * ....
    *
    * 居然bolb通不过，，用long_bolb通过了
    * */
    @Test
    public void testWriteBlob() throws IOException {
        StudentEntity s = new StudentEntity(1,"1","1",new Date(), "1");

        //先获得照片文件
        File file = new File("E:\\LiuChen\\IntelliJ Java\\HibernateDemo\\1.jpg");
        //获得照片的输入流
        InputStream inputStream = new FileInputStream(file);

        Blob image = Hibernate.getLobCreator(session).createBlob(inputStream, inputStream.available());

        //设置照片属性
        s.setPicture(image);

        //保存学生
        session.save(s);
    }

    @Test
    public void testReadBlob() throws SQLException, IOException {
        StudentEntity s = session.get(StudentEntity.class, 1);

        //获得Blob
        Blob image = s.getPicture();

        //获得输入流
        InputStream input = image.getBinaryStream();

        //创建输出流
        File f = new File("E:\\LiuChen\\IntelliJ Java\\HibernateDemo\\2.jpg");

        //获得输出流
        OutputStream output = new FileOutputStream(f);

        //创建缓冲区
        byte[] buffer = new byte[input.available()];
        input.read(buffer);
        output.write(buffer);
        input.close();
        output.close();

    }


    @Test
    //Get方法，类似于select查询语句
    public void testGetStudents (){
        StudentEntity s = session.get(StudentEntity.class, 1);
        System.out.println(s);
    }

    @Test
    //Load方法，类似于select查询语句
    public void testLoadStudents (){
        StudentEntity s = session.load(StudentEntity.class, 1);
        System.out.println(s);
    }

    @Test
    //Update方法，类似于update语句，先使用get方法得到要修改的对象，然后修改，在
    //update
    public void testUpdateStudents (){
        StudentEntity s = session.get(StudentEntity.class, 1);
        s.setGender("123");
        session.update(s);

        //System.out.println(s);
    }

    @Test
    public void testDeleteStudents (){
        StudentEntity s = session.get(StudentEntity.class, 1);
        session.delete(s);
    }

}
