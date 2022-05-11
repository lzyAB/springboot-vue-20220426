package com.ilzya.springboot.dao;

import com.ilzya.springboot.entity.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptDao {

    /**
     * 查询所有部门信息
     * @return
     */
    @Select(value = "SELECT * FROM dept")
    List<Dept> queryAll();

    /**
     * 根据id删除部门
     * @param id
     * @return
     */
    @Delete(value = "DELETE FROM dept WHERE id = #{id}")
    int delete(Long id);

    /**
     * 修改部门状态
     * @param id
     * @return
     */
    int changeStatus(Long id);

    /**
     * 修改部门信息
     * @param dept
     * @return
     */
    int changeInfo(Dept dept);

    /**
     * 新增部门信息
     * @param dept
     * @return
     */
    @Insert(value = "INSERT INTO dept VALUES(id,#{dname},#{address},1)")
    int addInfo(Dept dept);

    /**
     * 根据部门名或地址查询信息,也可以通过部门名和地址一起查询 传入空为全查
     * @param dept
     * @return
     */
    List<Dept> queryByNameAndAddress(Dept dept);



}
