package com.example.springboot;

import com.example.App;
import com.example.pojo.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = App.class)
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testSet() {
        this.redisTemplate.opsForValue().set("key", "redis study");
    }

    @Test
    public void testGet () {
        String value = (String) this.redisTemplate.opsForValue().get("key");
        System.out.println("==========="+value);
    }

    /**
     * 添加Student对象
     * 采用JdkSerializationRedisSerializer序列化，虽然可以存进去，但是乱码
     */
    @Test
    public void testSetStudent() {
        Student student = new Student();
        student.setId(5);
        student.setAge(18);
        student.setName("蔡");
        student.setAddress("厦门");
        // 重新设置序列化器，此处必须
        this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        this.redisTemplate.opsForValue().set("student1",student);
    }

    /**
     *输出结果 Student{id=null, name='蔡', age=18, address='厦门'}
     */
    @Test
    public void testGetStudent() {
        // 重新设置序列化器，此处必须，而且必须跟set的时候是一致的，不然不会成功
        this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        Student student = (Student) this.redisTemplate.opsForValue().get("student1");
        System.out.println(student);
    }
    /**
     * 基于JSON格式Users对象：相比存实体对象，更省空间
     */
    @Test
    public void testSetStudentJson() {
        Student student = new Student();
        student.setId(5);
        student.setAge(18);
        student.setName("蔡");
        student.setAddress("厦门");

        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Student.class));

        this.redisTemplate.opsForValue().set("student" + student.getId(), student);
    }
    /**
     *输出结果 Student{id=null, name='蔡', age=18, address='厦门'}
     */
    @Test
    public void testGetStudentJson() {
        // 重新设置序列化器，此处必须，而且必须跟set的时候是一致的，不然不会成功
        this.redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(Student.class));
        Student student = (Student) this.redisTemplate.opsForValue().get("student1");
        System.out.println(student);
    }
}