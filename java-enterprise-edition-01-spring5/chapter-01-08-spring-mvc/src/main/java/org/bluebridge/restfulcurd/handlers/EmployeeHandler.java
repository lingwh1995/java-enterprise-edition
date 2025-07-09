package org.bluebridge.restfulcurd.handlers;

import java.util.Collection;


import org.bluebridge.restfulcurd.dao.DepartmentDao;
import org.bluebridge.restfulcurd.dao.EmployeeDao;
import org.bluebridge.restfulcurd.eitity.Department;
import org.bluebridge.restfulcurd.eitity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author lingwh
 * @desc   请求URI相同,请求方式不同,如请求URI都是 /emp,一个请求方法是GET,一个请求方法是POST,也可以被识别为不同的方法
 * @date   2019/7/20 14:19
 */
@Controller
public class EmployeeHandler {

	@Autowired
	private EmployeeDao employeeDao;

	@Autowired
	private DepartmentDao departmentDao;

	/**
	 * 跳转到lsit展示页面
	 * 查询:GET请求
	 * @return
	 */
	@RequestMapping(value="/listempsPage",method = RequestMethod.GET)
	public String listempsPage(Model model){
		Collection<Employee> emps = employeeDao.getAll();
		model.addAttribute("emps",emps);
		return "crudlist";
	}

	/**
	 * 跳转到新增页面
	 * @return
	 */
	@RequestMapping(value="/addOrEditempPage")
	public String addOrEditempPage(Model model){
		Collection<Department> departments = departmentDao.getDepartments();
		model.addAttribute("departments",departments);
		model.addAttribute("employee",new Employee());
		return "addOrEditEmp";
	}

	/**
	 * 新增:POST请求
	 * @return
	 */
	@RequestMapping(value="/addOrEdit",method = RequestMethod.POST)
	public String add(@Valid Employee employee,BindingResult bindingResult,Model model){
		System.out.println(bindingResult);
		if(bindingResult.getErrorCount() > 0){
			System.out.println("出错了...");
			for(FieldError error:bindingResult.getFieldErrors()){
				System.out.println(error.getField()+":"+error.getDefaultMessage());
			}
			//获取下拉菜单的数据
			Collection<Department> departments = departmentDao.getDepartments();
			model.addAttribute("departments",departments);
			return "addOrEditEmp";
		}
		System.out.println(employee);
		employeeDao.save(employee);
		return "redirect:/listempsPage";
	}

	/**
	 * 跳转到编辑页面
	 * 注意:这个方法和跳转到新增页面用的方法名是相同的,只不过这个方法传入了一个参数id,跳转到新增页面的方法没有传入id这个参数
	 * @param id
	 * @return
	 */
	@RequestMapping("addOrEditempPage/{id}")
	public String addOrEditempPage(@PathVariable("id") Integer id,Model model){
		//获取下拉菜单的数据
		Collection<Department> departments = departmentDao.getDepartments();
		model.addAttribute("departments",departments);
		//回显表单数据
		Employee employee = employeeDao.get(id);
		model.addAttribute("employee",employee);
		return "addOrEditEmp";
	}

	@ModelAttribute
	public void getEmp(@RequestParam(value="id",required = false) Integer id,Model model){
		if(id != null){
			//模拟查库
			Employee employee = employeeDao.get(id);
			model.addAttribute("employee",employee);
		}
	}

	/**
	 * 编辑:PUT请求
	 * @return
	 */
	@RequestMapping(value="/addOrEdit",method = RequestMethod.PUT)
	public String update(@ModelAttribute("employee") Employee employee){
		employeeDao.save(employee);
		return "redirect:/listempsPage";
	}

	/**
	 * 删除:DELETE请求
	 * @return
	 */
	@RequestMapping(value = "delete/{id}",method = RequestMethod.DELETE)
	public String delete(@PathVariable("id") Integer id){
		employeeDao.delete(id);
		return "redirect:/listempsPage";
	}

	/**
	 * 使用实体接收表单参数的时候，lastName属性不会被封装到实体中
	 */
//	@InitBinder
//	public void  initBinder(WebDataBinder webDataBinder){
//		webDataBinder.setDisallowedFields("lastName");
//	}
}
