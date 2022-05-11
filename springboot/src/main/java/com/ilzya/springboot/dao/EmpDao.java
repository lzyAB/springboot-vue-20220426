package com.ilzya.springboot.dao;

import com.ilzya.springboot.entity.Emp;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpDao {
    /**
     * 查询所有员工
     * @return
     */
    @Select(value = "SELECT * FROM emp")
    List<Emp> queryAll();

    /**
     * 查询员工及员工部门信息
     * @return
     */
    List<Emp> queryEmpAndDept();


    /**
     * 删除员工信息
     * @param id
     * @return
     */
    @Delete(value = "DELETE FROM emp WHERE id = #{id}")
    int delete(Long id);

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    @Select(value = "SELECT * FROM emp WHERE id = #{id}")
    Emp queryById(Long id);

    /**
     * 修改员工信息
     * @param emp
     * @return
     */
    int changeInfo(Emp emp);

    /**
     * 根据ename或deptno查询用户 也可以根据ename和deptno同时查询 传入空为全查
     * @param ename
     * @param deptno
     * @return
     */
    List<Emp> queryByNameAndDeptId(String ename,Long deptno);

    /**
     * 新增员工信息
     * @param emp
     * @return
     */
    @Insert(value = "INSERT INTO emp VALUES (id, #{ename}, #{phone}, #{job}, #{birdate}, #{sal}, #{carno}, #{deptno})")
    int addInfo(Emp emp);

}
