package com.example.demo_transaction.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo_transaction.service.ProductService;
import com.example.demo_transaction.vo.ProductVo;

@Controller
public class ProductController {

	@Autowired
	ProductService product_service;
	
	
	@RequestMapping("/product/list.do")
	public String list(Model model) {
		
		Map<String,List<ProductVo>> map = product_service.selectList();
		
		//request binding
		model.addAttribute("map", map);
		
		return "product/product_list";
	}
	
	
	//   /product/insert_in.do?name=TV&cnt=100
	@RequestMapping("/product/insert_in.do")
	public String insert_in(ProductVo vo) {
		
		try {
			
			//입고등록해라
			product_service.insert_in(vo);
			
		} catch (Exception e) {
			
		}
		
		return "redirect:list.do";
	}
	
	//   /product/insert_out.do?name=TV&cnt=100
	@RequestMapping("/product/insert_out.do")
	public String insert_out(ProductVo vo, RedirectAttributes redirect_attr ) {
		
		try {
			
			//출고등록해라
			product_service.insert_out(vo);
			
		} catch (Exception e) {
			
			String message = e.getMessage();
			
			redirect_attr.addAttribute("error", message);
		}
		
		return "redirect:list.do";
	}
	
	
	//입고취소

	@RequestMapping("/product/delete_in.do")
	public String delete_in(int idx,RedirectAttributes redirect_attr) {
		
		try {
			product_service.delete_in(idx);
			
		} catch (Exception e) {
			
			String message = e.getMessage();
			
			redirect_attr.addAttribute("error", message);
		}
		
		return "redirect:list.do";
	}
	
	//출고취소
	@RequestMapping("/product/delete_out.do")
	public String delete_out(int idx) {
		
		try {
			product_service.delete_out(idx);
			
		} catch (Exception e) {
			
		}
		
		return "redirect:list.do";
	}
	
	
	
	
}
