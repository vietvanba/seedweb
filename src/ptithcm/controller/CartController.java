package ptithcm.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpSession;

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
import ptithcm.entity.TheOrder;
import ptithcm.entity.User;

@Transactional
@Controller
@RequestMapping("/cart/")
public class CartController {
	@Autowired
	SessionFactory factory;
	@Autowired
	Mailer mailer;
	public String getTongGiaVN(Float a) {
		// TODO Auto-generated method stub
		DecimalFormat formatter = new DecimalFormat("###,###,###");

		return formatter.format(a) + " VNĐ";

	}

	@SuppressWarnings("unchecked")
	@RequestMapping("cart")
	public String index(ModelMap model, HttpSession httpSession) {
		if(httpSession.getAttribute("myLogin")==null)
		{
			return "redirect:http://localhost:9999/BanHatGiong/home/login.htm";
		}
		if (httpSession.getAttribute("myOrder") == null) {

		} else {
			List<OrderDetail> orderDetails = (ArrayList<OrderDetail>) httpSession.getAttribute("myOrder");
			model.addAttribute("Cart", orderDetails);
			float tong = 0;
			for (int i = 0; i < orderDetails.size(); i++) {

				tong += orderDetails.get(i).getNumber() * orderDetails.get(i).getPrice();
			}
			model.addAttribute("Tong", getTongGiaVN(tong));
		}
		return "cart/cart";
	}
	@RequestMapping(value="delete/{idSeed}")
	public String delete(ModelMap model,HttpSession httpSession,@PathVariable("idSeed")String idSeed)
	{
		List<OrderDetail> orderDetails = (ArrayList<OrderDetail>) httpSession.getAttribute("myOrder");
		for (int i = 0; i < orderDetails.size(); i++) {

			if(orderDetails.get(i).getSeed().getIdSeed()==Integer.parseInt(idSeed))
			{
				orderDetails.remove(i);
				break;
			}
		}
		if(!orderDetails.isEmpty())
		{
			httpSession.setAttribute("myOrder", orderDetails);
		}else
		{
			httpSession.setAttribute("myOrder", null);
		}
		return "redirect:http://localhost:9999/BanHatGiong/cart/cart.htm";
	}
	@RequestMapping(value="theOrder",method=RequestMethod.GET)
	public String theorder(HttpSession httpSession, ModelMap model)
	{
		if(httpSession.getAttribute("myOrder")==null)
		{
			model.addAttribute("message", "Giỏ hàng rỗng không thể đặt hàng");
			return "cart/cart";
		}
		TheOrder theOrder = new TheOrder();
		User user = (User)httpSession.getAttribute("myLogin");
		theOrder.setUser(user);
		theOrder.setFirstName(user.getFirstName().trim());
		theOrder.setLastName(user.getLastName().trim());
		theOrder.setPhone(user.getPhone().trim());
		theOrder.setAddress(user.getAddress().trim());
		model.addAttribute("theOrder", theOrder);
		return "cart/info_theorder";
	}
	@RequestMapping(value="theOrder",method=RequestMethod.POST)
	public String theorder(HttpSession httpSession,ModelMap model,@Validated @ModelAttribute("theOrder")TheOrder theOrder,BindingResult errors)
	{
		if(httpSession.getAttribute("myOrder")==null)
		{
			model.addAttribute("message", "Giỏ hàng rỗng không thể đặt hàng");
			return "cart/info_theorder";
		}
		
		List<OrderDetail> orderDetails = (ArrayList<OrderDetail>) httpSession.getAttribute("myOrder");
		
		User user =(User) httpSession.getAttribute("myLogin");
		theOrder.setOrderTime(new Date());
		theOrder.setUser((User) httpSession.getAttribute("myLogin"));
		float tong = 0;
		for (int i = 0; i < orderDetails.size(); i++) {
			
			tong += orderDetails.get(i).getNumber() * orderDetails.get(i).getPrice();
		}
		theOrder.setPrice(tong);
		theOrder.setStatusOrder("1");
		
		for (int i = 0; i < orderDetails.size(); i++) {
			
			Seed seed = orderDetails.get(i).getSeed();
			seed.setNumber(seed.getNumber()-orderDetails.get(i).getNumber());
			Session session3 = factory.openSession();
			Transaction t3 = session3.beginTransaction();
			try {
				session3.update(seed);
				t3.commit();
				System.out.println("thành công");
			} catch (Exception e) {
				// TODO: handle exception
				t3.rollback();
				System.out.println("thất bại");
			}finally {
				session3.close();
			}
		}
		if(errors.hasErrors()){
			model.addAttribute("message", "Vui lòng sửa các lỗi sau đây");
			return "cart/info_theorder";
		}
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(theOrder);
			t.commit();
			model.addAttribute("message", "Thêm đơn hàng thành công");
			mailer.send("baviet090@gmail.com", user.getEmail(), "Đặt hàng thành công",
					"Đơn hàng của bạn đã được đặt thành công");
			
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			model.addAttribute("message", "Thêm đơn hàng thất bại");
			
		}finally {
			session.close();
		}
		for (int i = 0; i < orderDetails.size(); i++) {
			
			Session session2 = factory.openSession();
			Transaction t1 = session2.beginTransaction();
			OrderDetail orderDetail = orderDetails.get(i);
			orderDetail.setTheOrder(theOrder);
			try {
				session2.save(orderDetail);
				t1.commit();
			} catch (Exception e) {
				t.rollback();
				
				
				// TODO: handle exception
			}
		}
		
		httpSession.setAttribute("myOrder", null);
		
		return "cart/info_theorder";
	}
}
