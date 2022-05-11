package com.ilzya.springboot.service;

import com.ilzya.springboot.dao.DeptDao;
import com.ilzya.springboot.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptService {

    @Autowired
    DeptDao deptDao;

    /**
     * 查询所有部门信息
     * @return
     */
    public List<Dept> queryAll(){
        return deptDao.queryAll();
    }

    /**
     * 根据id删除部门
     * @param id
     * @return
     */
    public boolean delete(Long id){
        return deptDao.delete(id)>0;
    }


    /**
     * 修改部门状态
     * @param id
     * @return
     */
    public boolean changeStatus(Long id){
        return deptDao.changeStatus(id)>0;
    }


    /**
     * 修改部门信息
     * @param dept
     * @return
     */
    public boolean changeInfo(Dept dept){
        return deptDao.changeInfo(dept)>0;
    }


    /**
     * 新增部门信息
     * @param dept
     * @return
     */
    public boolean addInfo(Dept dept){
        return deptDao.addInfo(dept)>0;
    }


    /**
     * 根据部门名或地址查询信息,也可以通过部门名和地址一起查询
     * @param dept
     * @return
     */
    public List<Dept> queryByNameAndAddress(Dept dept){
        return deptDao.queryByNameAndAddress(dept);
    }

}
