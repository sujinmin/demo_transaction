package com.example.demo_transaction.service;

import java.util.List;
import java.util.Map;

import com.example.demo_transaction.vo.ProductVo;

public interface ProductService {

	Map<String, List<ProductVo>> selectList();	  //전체조회 (입고/출고/재고 조회)
	int insert_in(ProductVo vo) throws Exception; //입고등록
	int insert_out(ProductVo vo) throws Exception; //출고등록
	
	int delete_in(int idx) throws Exception; //입고취소
	//int delete_out(int idx) throws Exception; 
	int delete_out(int idx) throws Exception;//출고취소
	
	
}
