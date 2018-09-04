package test;


import entity.StudentEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;


public class SessionTest {

    @Test
    public void testOpenSession(){
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();

        SessionFactory sessionFactory =new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();


        Session session = sessionFactory.openSession();
        Session session1 = sessionFactory.openSession();
        System.out.println(session == session1);  //false

        /*if (session != null){
            System.out.println("session 创建成功！");
        } else {
            System.out.println("session 创建失败！");
        }*/

    }

    @Test
    public void testGetCurrentSession(){
        //创建配置对象
        Configuration configuration = new Configuration().configure();

        //创建服务注册对象
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();

        //创建会话工厂对象
        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        Session session1 = sessionFactory.getCurrentSession();
        System.out.println(session == session1);    //true

        /* if (session != null){
            System.out.println("Session 创建成功");
        } else {
            System.out.println("Session 创建失败");
        }*/
    }

    @Test
    public void testSaveStudentWithOpenSession(){
        Configuration configuration = new Configuration().configure();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();

        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        //创建Session对象
        Session session = sessionFactory.openSession();

        Transaction transaction = session.beginTransaction();

        StudentEntity studentEntity = new StudentEntity(1, "张三", "男",new Date(), "北京");

        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println("connection hashcode : " + connection.hashCode());
            }
        });
        session.save(studentEntity);
        //session.close();
        transaction.commit();

        Session session2 = sessionFactory.openSession();
        transaction = session2.beginTransaction();
        studentEntity = new StudentEntity(2, "二哈", "男", new Date(), "河南");
        session2.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println("connection hashcode : " + connection.hashCode());
            }
        });
        session2.save(studentEntity);
        transaction.commit();
    }

    @Test
    public void testSaveStudentWithCurrentSession (){
        Configuration configuration = new Configuration().configure();

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure().build();

        SessionFactory sessionFactory = new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();

        Session session1 = sessionFactory.getCurrentSession();
        Transaction transaction = session1.beginTransaction();

        //生成一个学生对象
        StudentEntity studentEntity = new StudentEntity(1, "刘琛", "男", new Date(), "河南");
        session1.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println("connection hashcode : " + connection.hashCode());
            }
        });
        session1.save(studentEntity);
        transaction.commit();
        //session1.close();

        Session session2 = sessionFactory.getCurrentSession();
        transaction = session2.beginTransaction();
        studentEntity = new StudentEntity(2, "aa", "nan", new Date(), "beijing");
        session2.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                System.out.println("connection hashcode : " + connection.hashCode());
            }
        });
        session2.save(studentEntity);
        transaction.commit();
    }


}
