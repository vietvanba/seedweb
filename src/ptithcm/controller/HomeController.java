package ptithcm.controller;

import java.util.List;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ptithcm.bean.Mailer;
import ptithcm.entity.OrderDetail;
import ptithcm.entity.Seed;
import ptithcm.entity.TypeSeed;
import ptithcm.entity.User;

@Transactional
@Controller
@RequestMapping("/home/")
public class HomeController {
	@Autowired
	SessionFactory factory;
	@Autowired
	Mailer mailer;

	@RequestMapping("index")
	public String home(ModelMap model, HttpSession httpSession) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TypeSeed";
		Query query = session.createQuery(hql);
		List<TypeSeed> list = query.list();
		model.addAttribute("TypeSeeds", list);
		httpSession.setAttribute("TypeSeeds", list);
		User user = (User) httpSession.getAttribute("myLogin");
		if (user == null) {
			model.addAttribute("dangnhap", "Đăng nhập");
			model.addAttribute("dangki", "Đăng kí");
		} else {
			model.addAttribute("dangnhap", "Xin chào ");
			model.addAttribute("dangki", user.getFirstName());
		}
		Session session2 = factory.getCurrentSession();
		String hql2 = "FROM Seed";
		Query query2 = session.createQuery(hql2);
		List<Seed> list2 = query2.list();
		model.addAttribute("Seed", list2);

		return "home/index";
	}

	@RequestMapping(value = "login", method = RequestMethod.GET)
	public String login(ModelMap model, HttpSession httpSession) {

		model.addAttribute("user", new ptithcm.bean.User());
		User user = (User) httpSession.getAttribute("myLogin");
		if (user != null) {
			return "redirect:index.htm";
		}
		Session session = factory.getCurrentSession();
		String hql = "FROM TypeSeed";
		Query query = session.createQuery(hql);
		List<TypeSeed> list = query.list();
		model.addAttribute("TypeSeeds", list);
		return "login/login";
	}

	@RequestMapping(value = "login", method = RequestMethod.POST)
	public String login(ModelMap model, @ModelAttribute("user") ptithcm.bean.User user, HttpSession httpSession,
			BindingResult errors) {
		boolean checkuser = false;
		if (user.getUsername().equals("")) {
			errors.rejectValue("username", "user", "Vui lòng nhập Username");
			checkuser = true;
		}
		if (user.getPassword().equals("")) {
			errors.rejectValue("password", "user", "Vui lòng nhập Password");
			checkuser = true;
		}
		if (checkuser == true) {
			return "login/login";
		}
		Session session = factory.getCurrentSession();
		String hql = "FROM User WHERE username = '" + user.getUsername() + "' AND password = '" + user.getPassword()
				+ "'";

		Query query = session.createQuery(hql);

		if (query.list().isEmpty()) {
			session = factory.getCurrentSession();
			hql = "FROM TypeSeed";
			query = session.createQuery(hql);
			List<TypeSeed> list = query.list();
			model.addAttribute("TypeSeeds", list);
			model.addAttribute("message", "Tài khoản hoặc mật khẩu không chính xác");
		} else {

			if (((User) (query.list().get(0))).getAdmin()) {
				httpSession.setAttribute("myLogin", query.list().get(0));
				return "redirect:http://localhost:9999/BanHatGiong/admin/index.htm";
			}
			httpSession.setAttribute("myLogin", query.list().get(0));
			return "redirect:index.htm";
		}
		return "login/login";
	}

	@RequestMapping(value = "register", method = RequestMethod.GET)
	public String register(ModelMap model, HttpSession httpSession) {

		model.addAttribute("user", new ptithcm.bean.User());
		User user = (User) httpSession.getAttribute("myLogin");
		if (user != null) {
			return "redirect:index.htm";
		}
		Session session = factory.getCurrentSession();
		String hql = "FROM TypeSeed";
		Query query = session.createQuery(hql);
		List<TypeSeed> list = query.list();
		model.addAttribute("TypeSeeds", list);
		model.addAttribute("user", new User());
		return "login/register";
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public String register(ModelMap model, @Validated @ModelAttribute("user") User user, BindingResult errors) {
		if (errors.hasErrors()) {
			model.addAttribute("message", "Vui lòng sửa các lỗi sau đây");
			
			return "login/register";
		}
		boolean check1=false;
		if(!user.getPhone().matches("0\\d{9}"))
		{
			errors.rejectValue("phone", "user","Vui lòng nhập đúng số điện thoại");
			check1=true;
		}
		if(!user.getPassword().matches("^.*(?=.{8,})(?=..*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$")) {
			errors.rejectValue("password", "user", "Mật khẩu phải 8 kí tự chứ số, chữ hoa, chữ thường và kí tự đặc biệt!");
			check1=true;
		}
		if(!user.getEmail().matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"))
		{
			errors.rejectValue("email", "user", "Định dạng mail không hợp lệ!");
			check1=true;
		}
		if(check1==true)
		{
			return "login/register";
		}
		user.setAdmin(false);
		boolean check=false;
		Session session2 = factory.getCurrentSession();
		String hql2 = "FROM TypeSeed";
		Query query2 = session2.createQuery(hql2);
		List<TypeSeed> list2 = query2.list();
		model.addAttribute("TypeSeeds", list2);
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			System.out.println(user.getIdUser());
			session.save(user);
			t.commit();
			model.addAttribute("message", "Tạo tài khoản thành công");
			check=true;
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			model.addAttribute("message", "Tạo tài khoản thất bại");
		} finally {
			session.close();
			if(check==true)
			{
				mailer.send("baviet090@gmail.com", user.getEmail(), "Đăng kí tài khoản thành công",
						"Chào mừng bạn đến VietSeed.\nUsername: "+user.getUsername()+"Password: "+user.getPassword());
			}
		}
		
		return "login/register";
	}

	@RequestMapping("logout")
	public String logout(HttpSession httpSession) {
		httpSession.setAttribute("myLogin", null);
		httpSession.setAttribute("myOrder", null);
		return "redirect:index.htm";
	}

	@RequestMapping("about")
	public String about(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TypeSeed";
		Query query = session.createQuery(hql);
		List<TypeSeed> list = query.list();
		model.addAttribute("TypeSeeds", list);
		return "home/about";
	}

}
