package com.ilzya.springboot.service;

import com.ilzya.springboot.dao.EmpDao;
import com.ilzya.springboot.entity.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpService {

    @Autowired
    EmpDao empDao;

    /**
     * 查询所有员工信息及部门信息
     * @return
     */
    public List<Emp> queryEmpAndDept(){
        return empDao.queryEmpAndDept();
    }


    /**
     * 删除员工信息
     * @param id
     * @return
     */
    public boolean delete(Long id){
        return empDao.delete(id)>0;
    }

    /**
     * 根据id查询员工信息
     * @param id
     * @return
     */
    public Emp queryById(Long id){
        return empDao.queryById(id);
    }


    /**
     * 修改员工信息
     * @param emp
     * @return
     */
    public boolean changeInfo(Emp emp){
        return empDao.changeInfo(emp)>0;
    }

    /**
     * 根据ename或deptno查询用户 也可以根据ename和deptno同时查询 传入空为全查
     * @param ename
     * @param deptno
     * @return
     */
    public List<Emp> queryByNameAndDeptId(String ename,Long deptno){
        return empDao.queryByNameAndDeptId(ename,deptno);
    }

    /**
     * 新增员工信息
     * @param emp
     * @return
     */
    public boolean addInfo(Emp emp){
        return empDao.addInfo(emp)>0;
    }
}
