package com.model2.mvc.web.product;

import java.io.File;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
@RequestMapping("/product/*")
public class ProductController {
	
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	public ProductController() {
		System.out.println(this.getClass());
	}
	
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	
	
	//@RequestMapping("/addProductView.do")
	@RequestMapping(value="addProduct", method=RequestMethod.GET)
	public String addProduct(@ModelAttribute("product") Product product,Model model) throws Exception {
		
		System.out.println("/product/addProduct : GET");
		
		model.addAttribute("prod", product);
		
		return "redirect:/product/addProductView.jsp";
	}
	
	//@RequestMapping("/addProduct.do")
	@RequestMapping("addProduct")
	public String addProduct(HttpServletRequest request,@ModelAttribute("product") Product product, Model model,@RequestParam("file") MultipartFile file) throws Exception {
		
		System.out.println("/addProduct.do");
		String path = "C:\\Users\\bitcamp\\git\\07miniproject\\07.Model2MVCShop(URI,pattern) stu\\src\\main\\webapp\\images\\";
		product.setManuDate(product.getManuDate().replaceAll("-", ""));
		
		String root = path + "NewUploadFiles";
		
		
		File file2 = new File(root);
		
		if(!file2.exists()) file2.mkdirs();
		
		String ogFileName = file.getOriginalFilename();
		String ext = ogFileName.substring(ogFileName.lastIndexOf("."));
		String ranFileName = UUID.randomUUID().toString() + ext;
		File changeFile = new File(file2 + "\\" +ranFileName);
		System.out.println("ogfileanem : "+ogFileName + "\n RandomFilename : "+ ranFileName+"\n ChangeFile name : "+changeFile);
		
		
		if(file.isEmpty()) {
			product.setFileName("");
			
		} else if(!file.isEmpty()){
			product.setFileName(changeFile.getName());
			file.transferTo(changeFile);
		}
		
		productService.addProduct(product);
		
		model.addAttribute("prod", product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	//@RequestMapping("/listProduct.do")
	@RequestMapping(value="listProduct")
	public String listProduct(@ModelAttribute("search") Search search, Model model, HttpServletRequest request) throws Exception{
		System.out.println("/product/listProduct : GET / POST");
		
		if(search.getCurrentPage() ==0 ) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		char c = 'm';
		
		if(c == request.getParameter("menu").charAt(0))
			return "forward:/product/listProduct.jsp";
			else 
			return "forward:/product/listProductSearch.jsp";
	}
	
	//@RequestMapping("/getProduct.do")
	@RequestMapping(value="getProduct")
	@GetMapping
	public String getProduct(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
		
		System.out.println("/getProduct.do");
		
		Product product = productService.findProduct(prodNo);

		model.addAttribute("prod", product);
		
		return "/product/getProduct.jsp";
	}
	
	//@RequestMapping("/updateProductView.do")
	@RequestMapping(value="updateProduct", method=RequestMethod.GET)
	public String updateProduct(@RequestParam("prodNo") int prodNo, Model model) throws Exception{
		
		System.out.println("/product/updateProduct : GET");
		
		Product product = productService.findProduct(prodNo);
		
		model.addAttribute("productVO",product);
		
		return "/product/updateProduct.jsp";
	}
	
	//@RequestMapping("/updateProduct.do")
	@RequestMapping(value="updateProduct", method=RequestMethod.POST)
	public String updateProduct(@ModelAttribute("product") Product product, Model model, @RequestParam("file") MultipartFile file) throws Exception{
		
		String path = "C:\\Users\\bitcamp\\git\\07miniproject\\07.Model2MVCShop(URI,pattern) stu\\src\\main\\webapp\\images\\";
		product.setManuDate(product.getManuDate().replaceAll("-", ""));
		String root = path + "NewUploadFiles";
		
		File file2 = new File(root);
		if(!file2.exists()) file2.mkdirs();
		
		String ogFileName = file.getOriginalFilename();
		String ext = ogFileName.substring(ogFileName.lastIndexOf("."));
		String ranFileName = UUID.randomUUID().toString() + ext;
		File changeFile = new File(file2 + "\\" +ranFileName);
		System.out.println("ogfileanem : "+ogFileName + "\n RandomFilename : "+ ranFileName+"\n ChangeFile name : "+changeFile);
		
		
		if(file.isEmpty()) {
			product.setFileName("");
			
		} else if(!file.isEmpty()){
			product.setFileName(changeFile.getName());
			file.transferTo(changeFile);
		}
		
		productService.updateProduct(product);
		
		model.addAttribute("prod", product);
		
		return "redirect:/product/getProduct?prodNo="+product.getProdNo();
	}
	
}
