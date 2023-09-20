package ptithcm.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ptithcm.entity.OrderDetail;
import ptithcm.entity.TheOrder;
import ptithcm.entity.User;

@Transactional
@Controller
@RequestMapping("/order/")
public class OrderController {
	@Autowired
	SessionFactory factory;
	public String getTongGiaVN(Float a) {
		// TODO Auto-generated method stub
		DecimalFormat formatter = new DecimalFormat("###,###,###");

		return formatter.format(a) + " VNĐ";

	}
	@RequestMapping("show")
	public String show(ModelMap model,HttpSession httpSession)
	{
		Session session = factory.getCurrentSession();
		String hql="FROM TheOrder WHERE idUser="+String.valueOf(((User)httpSession.getAttribute("myLogin")).getIdUser());
		Query query = session.createQuery(hql);
		List<TheOrder> list = query.list();
		model.addAttribute("listOrder",list);
		
		
		return "theOrder/ShowTheOrder";
	}
	@RequestMapping(value="showDetail/{idOrder}")
	public String showDetail(ModelMap model, HttpSession httpSession,@PathVariable("idOrder")String idOrder)
	{
		Session session = factory.getCurrentSession();
		String hql = "FROM OrderDetail WHERE idOrder = "+idOrder;
		Query query = session.createQuery(hql);
		if(query.list().size()==0)
			return "404";
		List<OrderDetail> list = query.list();
		model.addAttribute("orderDetail", list);
		float tong = 0;
		for (int i = 0; i < list.size(); i++) {

			tong += list.get(i).getNumber() * list.get(i).getPrice();
		}
		model.addAttribute("Tong", getTongGiaVN(tong));
		hql="FROM TheOrder Where idOrder="+idOrder;
		query = session.createQuery(hql);
		model.addAttribute("status", ((TheOrder)query.list().get(0)).getStatusOrder().trim());
		model.addAttribute("idOrder", idOrder);
		return "theOrder/ShowDetail";
	}
	@RequestMapping(value="cancelOrder/{idOrder}")
	public String cancelOrder(ModelMap model,@PathVariable("idOrder")String idOrder)
	{
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		String hql = "FROM TheOrder where idOrder="+idOrder;
		Query query= session.createQuery(hql);
		if(query.list().size()==0)
			return "404";
		if(!((TheOrder)query.list().get(0)).getStatusOrder().trim().equals("1"))
		{
			System.out.println(((TheOrder)query.list().get(0)).getStatusOrder().trim());
			model.addAttribute("message", "Không thể huỷ đơn");
			return "theOrder/ShowDetail";
		}
		TheOrder theOrder = (TheOrder)query.list().get(0);
		theOrder.setStatusOrder("0");
		
		try {
			session.update(theOrder);
			t.commit();
			model.addAttribute("message", "Huỷ đơn hàng thành công");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			t.rollback();
			model.addAttribute("message", "Không thể huỷ đơn hàng");
			
		}finally {
			session.close();
		}
		return "theOrder/ShowDetail";
	}
}
