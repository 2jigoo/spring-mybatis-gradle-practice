package org.zerock.controller;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDTO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {

	/*
	// DTO에서 @DateTimeFormat(pattern="yyyy/MM/dd") 어노테이션 이용하면 @InitBinder 필요 없음
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
	}*/
	
	
	// method 구분 없음
	@RequestMapping("")
	public void basic() {
		log.info("basic.....");
	}
	
	// basic -> GET / POST
	@RequestMapping(value = "/basic", method = {RequestMethod.GET, RequestMethod.POST})
	public void basicGet() {
		log.info("basic get");
	}
	
	// basicOnlyGet -> GET만
	@GetMapping("/basicOnlyGet")
	public void basicGet2() {
		log.info("basic get only get...");
	}
	
	// SampleDTO의 필드가 name, age가 있음. 파라미터 자동으로 맵핑됨.
	@GetMapping("/ex01")
	public String ex01(SampleDTO dto) {
		log.info("" + dto);
		// /ex01?name=hi&age=20 -> SampleDTO(name=hi, age=20)
		
		return "ex01";
	}
	
	// DTO 없이
	// RequestParam: 변수의 이름과 전달되는 파라미터의 이름이 다른 경우에 쓸 수 있음
	@GetMapping("/ex02")
	public String ex02(@RequestParam("name") String name, @RequestParam("age") int age) {
		log.info("name: " + name);
		log.info("age: " + age);
		
		return "ex02";
	}
	
	// 동일한 이름의 파라미터가 여러 개 전달되는 경우에는 리스트나 배열 등을 이용할 수 있음
	// List 같은 인터페이스 타입은 X, 실제 클래스로(ArrayList 같은...)
	@GetMapping("/ex02List")
	public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
		log.info("ids: " + ids);
		return "ex02List";
	}
	
	@GetMapping("/ex02Array")
	public String ex02Array(@RequestParam("ids") String[] ids) {
		log.info("array ids: " + Arrays.toString(ids));
		return "ex02Array";
	}
	
	// /sample/ex02Bean?list[0].name=aaa&list[2].name=bbb -> 이렇게 파라미터를 인덱스로 전달 가능
	@GetMapping("/ex02Bean")
	public String ex02Bean(SampleDTOList list) {
		log.info("list dtos: " + list);
		return "ex02Bean";
	}
	
	// /sample/ex03?title=test&dueDate=2021-01-01 -> 이렇게 문자열 날짜로 파라미터 전달 가능
	@GetMapping("/ex03")
	public String ex03(TodoDTO todo) {
		log.info("todo: " + todo);
		return "ex03";
	}
	
	// @ModelAttribute: 강제로 전달받은 파라미터를 Model에 담아서 전달. 타입에 관계없이 무조건 Model에 담아 전달됨
	@GetMapping("/ex04")
	public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
		log.info("dto: " + dto);
		log.info("page: " + page);
		return "/sample/ex04";
	}
	
	// RedirectAttributes: Model과 같이 파라미터로 선언해서 사용.
	// addFlashAttribute(이름, 값) 메서드를 이용해서 화면에 한 번만 사용하고 다음에 사용되지 않는 데이터 전달
	
	
	// String: jsp 파일 리턴
	// void: 호출된 URL과 동일한 jsp를 호출
	// VO, DTO: 주로 JSON 타입의 데이터를 만들어서 반환하는 용도
	// ResponseEntity: response할 때 Http 헤더 정보와 내용을 가공하는 용도로 사용
	// Model, ModelAndView: Model로 데이터를 반환하거나 화면까지 지정하는 경우(최근에는 많이 사용하지 않음)
	// HttpHeaders: 응답에 내용 없에 Http 헤더 메시지만 전달하는 용도로 사용
	
	
	// @ResponseBody: JSON 타입으로 변환해 전달 (jackson-databind)
	@GetMapping("/ex06")
	public @ResponseBody SampleDTO ex06() {
		log.info("/ex06...");
		SampleDTO dto = new SampleDTO();
		dto.setAge(10);
		dto.setName("홍길동");
		
		return dto;
	}
	
	
	// ResponseEntity: HttpHeaders 객체를 같이 전달 (헤더 메시지 가공 가능)
	@GetMapping("/ex07")
	public ResponseEntity<String> ex07() {
		log.info("/ex07...");
		// {"name":"홍길동"}
		String msg = "{\"name\": \"홍길동\"}";
		
		HttpHeaders header = new HttpHeaders();
		header.add("Content-Type", "application/json;charset=UTF-8");
		
		return new ResponseEntity<>(msg, header, HttpStatus.OK); // 200
	}
	
	
	
}
