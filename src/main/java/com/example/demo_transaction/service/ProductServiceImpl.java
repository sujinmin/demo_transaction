package com.example.demo_transaction.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo_transaction.dao.ProductInDao;
import com.example.demo_transaction.dao.ProductOutDao;
import com.example.demo_transaction.dao.ProductRemainDao;
import com.example.demo_transaction.vo.ProductVo;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductInDao  product_in_dao;

	@Autowired
	ProductOutDao  product_out_dao;

	@Autowired
	ProductRemainDao  product_remain_dao;
	
		


	@Override
	public Map<String, List<ProductVo>> selectList() {
		
		Map<String, List<ProductVo>> map = new HashMap<String, List<ProductVo>>();
		
		// List<ProductVo> in_list 	= product_in_dao.selectList();    //입고목록
		// List<ProductVo> out_list 	= product_out_dao.selectList();   //출고목록
		// List<ProductVo> remain_list = product_remain_dao.selectList();//재고목록
		
		map.put("in_list", product_in_dao.selectList());
		map.put("out_list", product_out_dao.selectList());
		map.put("remain_list", product_remain_dao.selectList());
		
		return map;
	}

	
	
	@Override
	public int insert_in(ProductVo vo) throws Exception {
		
		
		//1.입고등록
		int res = product_in_dao.insert(vo);
		
		//2.현재 입고상품정보를 재고테이블에서 구한다
		ProductVo remainVo = product_remain_dao.selectOneFromName(vo.getName());
		
		if(remainVo==null) {//재고목록에 없으면 등록(insert)
			
			res = product_remain_dao.insert(vo);
			
		}else {
			//재고수량 = 재고수량 + 입고수량
			int cnt = remainVo.getCnt() + vo.getCnt();
			remainVo.setCnt(cnt);
			
			res = product_remain_dao.update(remainVo);
		}
		
		//인위적 예외
		//if(true) throw new Exception("일부러");
		
		
		return res;
	}

	
	@Override
    public int insert_out(ProductVo vo) throws Exception {
		
		
		//1.출고등록
		int res = product_out_dao.insert(vo);
		
		//2.재고목록에 출고상품이 있는지 얻어온다
		ProductVo remainVo = product_remain_dao.selectOneFromName(vo.getName());
		
		if(remainVo==null)
		    throw new Exception("remain_not");//재고테이블에 해당상품 없는경우
		
		//출고수량 > 재고수량
		if(vo.getCnt() > remainVo.getCnt()) {
			throw new Exception("remain_lack"); //재고수량이 부족한 경우
		}
		
		// 재고수량 = 재고수량 - 출고수량
		int cnt = remainVo.getCnt() - vo.getCnt();
		remainVo.setCnt(cnt);

		if(cnt == 0){
			res = product_remain_dao.delete(remainVo.getIdx());
		}else{
		res = product_remain_dao.update(remainVo);
		}
		
		return res;
	}


	//입고취소
	@Override
	@Transactional(rollbackFor = Exception.class) // Transaction 자동처리
	public int delete_in(int idx) throws Exception {
		
		//1.입고취소할 상품정보 얻어오기
		ProductVo vo = product_in_dao.selectOneFromIdx(idx);
		
		//2.수정할 재고 테이블 상품정보 얻어오기 == 입고취소한 상품명
		ProductVo remainVo = product_remain_dao.selectOneFromName(vo.getName());
		
		//3.입고정보삭제
		int res = product_in_dao.delete(idx);
		
		//  입고취소수량 > 재고수량
		if(vo.getCnt() > remainVo.getCnt())
			throw new Exception("delete_in_lack");
		
		//4.재고정보 수정
		int cnt = remainVo.getCnt() - vo.getCnt();
		remainVo.setCnt(cnt);
		
		if(cnt == 0){
			res = product_remain_dao.delete(remainVo.getIdx());
		}else{
		res = product_remain_dao.update(remainVo);
		}

		return res;
	}


	//출고취소
	@Override
	@Transactional(rollbackFor = Exception.class) // Transaction 자동처리
	public int delete_out(int idx) throws Exception {
		
		//1.출고취소할 상품정보 얻어오기
		ProductVo vo = product_out_dao.selectOneFromIdx(idx);
		System.out.println(vo+"출고목록");
		
		//2.수정할 재고 테이블 상품정보 얻어오기 == 출고취소한 상품명
		ProductVo remainVo = product_remain_dao.selectOneFromName(vo.getName());
		System.out.println(remainVo+"재고테이블");
		System.out.println(vo.getName());
		
		//3.출고정보삭제
		int res = product_out_dao.delete(idx);
		System.out.println(res+"출고삭제");
		
		if(remainVo==null){
			res = product_remain_dao.insert(vo);
		}else{
		//4.재고정보 수정
		int cnt = remainVo.getCnt() + vo.getCnt();
		remainVo.setCnt(cnt);
		System.out.println(remainVo.getCnt());
		System.out.println(vo.getCnt());
		System.out.println(cnt+"재고수정");
		
		res = product_remain_dao.update(remainVo);
		}
		return res;
	}

}








