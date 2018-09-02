

/*Hibernate使用的时候，是先建立数据库，然后在左下角的Persistence(持久化)这个按钮上
* 点击，建立对应的持久化类，在建立这个类的同时，会建立好hbm.xlm文件，并加入到cfg.xml里*/

package test;

import entity.StudentEntity;
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

        //开启服务
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
        session.save(studentEntity);
        System.out.println("今天改不完了，明天再改把！");
    }

}
