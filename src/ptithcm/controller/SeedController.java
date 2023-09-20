package ptithcm.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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

import ptithcm.entity.OrderDetail;
import ptithcm.entity.Seed;
import ptithcm.entity.TheOrder;
import ptithcm.entity.TypeSeed;
import ptithcm.entity.User;

@Transactional
@Controller
@RequestMapping("/seed/")
public class SeedController {

	@Autowired
	SessionFactory factory;

	@RequestMapping(value = "{idSeed}", method = RequestMethod.GET)
	public String hat(ModelMap model, @PathVariable("idSeed") String idSeed, HttpSession httpSession) {

		Session session = factory.getCurrentSession();
		String hql = "FROM TypeSeed";
		Query query = session.createQuery(hql);
		List<TypeSeed> list = query.list();
		model.addAttribute("TypeSeeds", list);
		hql = "FROM Seed WHERE idSeed = '" + idSeed + "'";
		query = session.createQuery(hql);
		if(query.list().size()==0)
			return "404";
		Seed seed = (Seed) query.list().get(0);
		model.addAttribute("Seed", seed);
		hql = "FROM TypeSeed WHERE idTypeSeed=" + seed.getTypeSeed().getIdTypeSeed();
		query = session.createQuery(hql);
		TypeSeed list2 = (TypeSeed) query.list().get(0);
		model.addAttribute("TypeSeedName", list2.getTypeSeedName());
		OrderDetail orderDetail = new OrderDetail();
		model.addAttribute("orderDetail", orderDetail);
		model.addAttribute(idSeed, idSeed);
		return "Seed/hat";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "{idSeed}", method = RequestMethod.POST, params = "btnBuyNow")
	public String hat1(ModelMap model, @ModelAttribute("orderDetail") OrderDetail orderDetail,
			@PathVariable("idSeed") String idSeed, HttpSession httpSession, BindingResult errors) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TypeSeed";
		Query query = session.createQuery(hql);
		List<TypeSeed> list = query.list();
		model.addAttribute("TypeSeeds", list);

		hql = "FROM Seed WHERE idSeed = '" + idSeed + "'";
		query = session.createQuery(hql);
		Seed seed = (Seed) query.list().get(0);
		model.addAttribute("Seed", seed);

		hql = "FROM TypeSeed WHERE idTypeSeed=" + seed.getTypeSeed().getIdTypeSeed();
		query = session.createQuery(hql);
		TypeSeed list2 = (TypeSeed) query.list().get(0);
		model.addAttribute("TypeSeedName", list2.getTypeSeedName());
		boolean checknumber = false;
		if (orderDetail.getNumber() == null) {
			errors.rejectValue("number", "orderDetail", "Vui lòng nhập số lượng");
			checknumber = true;
		} else {
			if (orderDetail.getNumber() < 1) {
				errors.rejectValue("number", "orderDetail", "Số lượng phải lớn hơn 0");
				checknumber = true;
			}
		}
		if (checknumber == true) {
			return "Seed/hat";
		}
		User user = (User) httpSession.getAttribute("myLogin");
		if (user == null) {
			model.addAttribute("message", "loginFalse");
		} else {

			if (httpSession.getAttribute("myOrder") == null) {
				List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
				orderDetail.setSeed(seed);
				if (orderDetail.getNumber() > orderDetail.getSeed().getNumber()) {
					model.addAttribute("message", "max");
					return "Seed/hat";
				}
				orderDetail.setPrice(seed.getPrice());

				orderDetails.add(orderDetail);
				httpSession.setAttribute("myOrder", orderDetails);
				model.addAttribute("message", true);
			} else {
				List<OrderDetail> orderDetails = (ArrayList<OrderDetail>) httpSession.getAttribute("myOrder");
				orderDetail.setSeed(seed);
				orderDetail.setPrice(seed.getPrice());
				boolean check = true;
				float tong = 0;
				for (int i = 0; i < orderDetails.size(); i++) {
					if (orderDetails.get(i).getSeed().getIdSeed().equals(orderDetail.getSeed().getIdSeed())) {
						OrderDetail orderDetailtemp = new OrderDetail();
						orderDetailtemp.setSeed(orderDetail.getSeed());
						orderDetailtemp.setPrice(orderDetail.getPrice());
						orderDetailtemp.setNumber(orderDetail.getNumber() + orderDetails.get(i).getNumber());
						if (orderDetailtemp.getNumber() > orderDetail.getSeed().getNumber()) {
							model.addAttribute("message", "max");
							return "Seed/hat";
						}
						orderDetails.set(i, orderDetailtemp);
						check = false;
					}

				}
				if (check) {
					if (orderDetail.getNumber() > orderDetail.getSeed().getNumber()) {
						model.addAttribute("message", "max");
						return "Seed/hat";
					}
					orderDetails.add(orderDetail);
				}

				httpSession.setAttribute("myOrder", orderDetails);
				model.addAttribute("message", true);
			}

		}

		return "redirect:http://localhost:9999/BanHatGiong/cart/cart.htm";
	}

	@RequestMapping(value = "{idSeed}", method = RequestMethod.POST, params = "btnCart")
	public String hat(ModelMap model, @ModelAttribute("orderDetail") OrderDetail orderDetail,
			@PathVariable("idSeed") String idSeed, HttpSession httpSession, BindingResult errors) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TypeSeed";
		Query query = session.createQuery(hql);
		List<TypeSeed> list = query.list();
		model.addAttribute("TypeSeeds", list);

		hql = "FROM Seed WHERE idSeed = '" + idSeed + "'";
		query = session.createQuery(hql);
		Seed seed = (Seed) query.list().get(0);
		model.addAttribute("Seed", seed);

		hql = "FROM TypeSeed WHERE idTypeSeed=" + seed.getTypeSeed().getIdTypeSeed();
		query = session.createQuery(hql);
		TypeSeed list2 = (TypeSeed) query.list().get(0);
		model.addAttribute("TypeSeedName", list2.getTypeSeedName());
		boolean checknumber = false;
		if (orderDetail.getNumber() == null) {
			errors.rejectValue("number", "orderDetail", "Vui lòng nhập số lượng");
			checknumber = true;
		} else {
			if (orderDetail.getNumber() < 1) {
				errors.rejectValue("number", "orderDetail", "Số lượng phải lớn hơn 0");
				checknumber = true;
			}
		}
		if (checknumber == true) {
			return "Seed/hat";
		}
		User user = (User) httpSession.getAttribute("myLogin");
		if (user == null) {
			model.addAttribute("message", "loginFalse");
		} else {

			if (httpSession.getAttribute("myOrder") == null) {
				List<OrderDetail> orderDetails = new ArrayList<OrderDetail>();
				orderDetail.setSeed(seed);
				if (orderDetail.getNumber() > orderDetail.getSeed().getNumber()) {
					model.addAttribute("message", "max");
					return "Seed/hat";
				}
				orderDetail.setPrice(seed.getPrice());

				orderDetails.add(orderDetail);
				httpSession.setAttribute("myOrder", orderDetails);
				model.addAttribute("message", true);
			} else {
				List<OrderDetail> orderDetails = (ArrayList<OrderDetail>) httpSession.getAttribute("myOrder");
				orderDetail.setSeed(seed);
				orderDetail.setPrice(seed.getPrice());
				boolean check = true;
				float tong = 0;
				for (int i = 0; i < orderDetails.size(); i++) {
					if (orderDetails.get(i).getSeed().getIdSeed().equals(orderDetail.getSeed().getIdSeed())) {
						OrderDetail orderDetailtemp = new OrderDetail();
						orderDetailtemp.setSeed(orderDetail.getSeed());
						orderDetailtemp.setPrice(orderDetail.getPrice());
						orderDetailtemp.setNumber(orderDetail.getNumber() + orderDetails.get(i).getNumber());
						if (orderDetailtemp.getNumber() > orderDetail.getSeed().getNumber()) {
							model.addAttribute("message", "max");
							return "Seed/hat";
						}
						orderDetails.set(i, orderDetailtemp);
						check = false;
					}

				}
				if (check) {
					if (orderDetail.getNumber() > orderDetail.getSeed().getNumber()) {
						model.addAttribute("message", "max");
						return "Seed/hat";
					}
					orderDetails.add(orderDetail);
				}

				httpSession.setAttribute("myOrder", orderDetails);
				model.addAttribute("message", true);
			}

		}

		return "Seed/hat";
	}
}
