package com.example.demo_transaction.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo_transaction.vo.ProductVo;

@Mapper
public interface ProductOutDao {
	
	List<ProductVo> selectList();
	ProductVo selectOneFromIdx(int idx);
	//default ProductVo selectOneFromName(String name) {return null;}
	
	//CRUD에서 예외가 발생되면??  throws Exception; 해준다.
	int insert(ProductVo vo) throws Exception;
	int update(ProductVo vo) throws Exception;
	int delete(int idx) throws Exception;
	
	

}
