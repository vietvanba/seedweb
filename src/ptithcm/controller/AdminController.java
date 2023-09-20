package ptithcm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import ptithcm.entity.OrderDetail;
import ptithcm.entity.Seed;
import ptithcm.entity.TheOrder;
import ptithcm.entity.TypeSeed;
import ptithcm.entity.User;

@Transactional
@Controller
@RequestMapping("/admin/")
public class AdminController {
	@Autowired
	SessionFactory factory;

	public String getTongGiaVN(Float a) {
		// TODO Auto-generated method stub
		DecimalFormat formatter = new DecimalFormat("###,###,###");

		return formatter.format(a) + " VNĐ";

	}

	@RequestMapping("index")
	public String index(ModelMap model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("myLogin");
		if (user == null || user.getAdmin() == false) {
			return "redirect:http://localhost:9999/BanHatGiong/home/index.htm";
		}
		Session session=factory.getCurrentSession();
		String hql="FROM User";
		Query query=session.createQuery(hql);
		List<User> admin = query.list();
		model.addAttribute("admin",admin);
				
		return "admin/index";

	}

	@RequestMapping("typeseed")
	public String typeseed(ModelMap model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("myLogin");
		if (user == null || user.getAdmin() == false) {
			return "redirect:http://localhost:9999/BanHatGiong/home/index.htm";
		}
		Session session = factory.getCurrentSession();
		String hql = "FROM TypeSeed";
		Query query = session.createQuery(hql);
		List<TypeSeed> list = query.list();
		model.addAttribute("TypeSeeds", list);
		return "admin/typeseed";

	}

	@RequestMapping(value = "inserttypeseed", method = RequestMethod.GET)
	public String inserttypeseed(ModelMap model) {
		TypeSeed typeSeed = new TypeSeed();
		model.addAttribute("typeseed", typeSeed);
		return "admin/typeseedinsert";
	}

	@RequestMapping(value = "inserttypeseed", method = RequestMethod.POST)
	public String inserttypeseed(ModelMap model, @Validated @ModelAttribute("typeseed") TypeSeed typeSeed,
			BindingResult errors) {
		if (errors.hasErrors()) {
			model.addAttribute("message", "Vui lòng sửa các lỗi sau đây");
			return "admin/typeseedinsert";
		}
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.save(typeSeed);
			t.commit();
			model.addAttribute("message", "Thêm thành công");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Thêm thất bại");
			// TODO: handle exception
		} finally {
			session.close();
		}
		Session session3 = factory.getCurrentSession();
		String hql = "FROM TypeSeed";
		Query query = session3.createQuery(hql);
		List<TypeSeed> list = query.list();
		model.addAttribute("TypeSeeds", list);
		return "admin/typeseed";
	}

	@RequestMapping(value = "updatetypeseed/{idTypeSeed}", method = RequestMethod.GET)
	public String updatetypeseed(ModelMap model, @PathVariable("idTypeSeed") String idTypeSeed) {
		Session session2 = factory.getCurrentSession();
		String hql = "FROM TypeSeed WHERE idTypeSeed=" + idTypeSeed;
		Query query = session2.createQuery(hql);
		List<TypeSeed> list = query.list();
		if(query.list().size()==0)
			return "404";
		TypeSeed typeSeed = new TypeSeed();
		typeSeed.setSeeds(list.get(0).getSeeds());
		typeSeed.setIdTypeSeed(list.get(0).getIdTypeSeed());
		typeSeed.setTypeSeedName(list.get(0).getTypeSeedName().trim());
		model.addAttribute("typeseed", typeSeed);
		return "admin/typeseededit";
	}

	@RequestMapping(value = "updatetypeseed/{idTypeSeed}", method = RequestMethod.POST)
	public String updatetypeseed(ModelMap model, @Validated @ModelAttribute("typeseed") TypeSeed typeSeed,
			BindingResult errors) {
		if (errors.hasErrors()) {
			model.addAttribute("message", "Vui lòng sửa các lỗi sau đây");
			return "admin/typeseededit";
		}
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			session.update(typeSeed);
			t.commit();
			model.addAttribute("message", "Sửa thành công");
		} catch (Exception e) {
			t.rollback();
			model.addAttribute("message", "Sửa thất bại");
			// TODO: handle exception
		} finally {
			session.close();
		}
		Session session3 = factory.getCurrentSession();
		String hql = "FROM TypeSeed";
		Query query = session3.createQuery(hql);
		List<TypeSeed> list = query.list();
		model.addAttribute("TypeSeeds", list);
		return "admin/typeseed";
	}

	@RequestMapping("seed")
	public String seed(ModelMap model, HttpSession httpSession) {
		User user = (User) httpSession.getAttribute("myLogin");
		if (user == null || user.getAdmin() == false) {
			return "redirect:http://localhost:9999/BanHatGiong/home/index.htm";
		}
		Session session = factory.getCurrentSession();
		String hql = "FROM Seed";
		Query query = session.createQuery(hql);
		List<TypeSeed> list = query.list();
		model.addAttribute("Seeds", list);
		return "admin/seed";

	}

	@RequestMapping(value = "updateseed/{idSeed}", method = RequestMethod.GET)
	public String updateseed(ModelMap model, @PathVariable("idSeed") String idSeed, HttpSession httpSession) {
		Session session2 = factory.getCurrentSession();
		String hql = "FROM Seed WHERE idSeed=" + idSeed;
		Query query = session2.createQuery(hql);
		List<Seed> list = query.list();
		if(query.list().size()==0)
			return "404";
		Seed seed = (Seed) query.list().get(0);
		seed.setSeedName(list.get(0).getSeedName().trim());
		seed.setImages(list.get(0).getImages().trim());
		httpSession.setAttribute("linkImages", list.get(0).getImages().trim());
		if (seed.getInformation() != null) {
			seed.setInformation(list.get(0).getInformation().trim());
		} else {
			seed.setInformation("");
		}
		model.addAttribute("seed", seed);
		Session session3 = factory.getCurrentSession();
		String hql2 = "FROM TypeSeed";
		Query query2 = session3.createQuery(hql2);
		List<TypeSeed> list2 = query2.list();
		model.addAttribute("TypeSeeds", list2);
		return "admin/seededit";
	}

	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get("C:\\Users\\Bavie\\Desktop\\LapTrinhWeb\\BanHatGiong\\WebContent\\images");

		if (!Files.exists(uploadPath)) {
			System.out.println("tạo path");
			Files.createDirectories(uploadPath);
		}
		System.out.println("tạo thành công");
		try (InputStream inputStream = multipartFile.getInputStream()) {
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioe) {
			throw new IOException("Could not save image file: " + fileName, ioe);
		}
	}

	@RequestMapping(value = "updateseed/{idSeed}", method = RequestMethod.POST)
	public String updateseed(ModelMap model, @ModelAttribute("seed") Seed seed,
			@RequestParam("image") MultipartFile photo, BindingResult errors, HttpSession httpSession) {
		boolean check = false;
		if (seed.getSeedName().equals("")) {

			errors.rejectValue("seedName", "seed", "Vui lòng nhập tên hạt");
			check = true;
		}
		if (seed.getPrice() == null) {
			errors.rejectValue("price", "seed", "Không được để trống giá");
			check = true;
		} else {
			if (seed.getPrice() < 0) {
				errors.rejectValue("price", "seed", "Giá phải lớn hơn 0");
				check = true;
			}
		}
		if (seed.getNumber() == null) {
			errors.rejectValue("number", "seed", "Không được để trống số lượng");
			check = true;
		} else {
			if (seed.getNumber() < 0) {
				errors.rejectValue("number", "seed", "Số lượng phải lớn hơn 0");
				check = true;
			}

		}
		
		if (check == true) {
			Session session3 = factory.getCurrentSession();
			String hql2 = "FROM TypeSeed";
			Query query2 = session3.createQuery(hql2);
			List<TypeSeed> list2 = query2.list();
			model.addAttribute("TypeSeeds", list2);
			return "admin/seededit";
		}

		String typeOfPhoto = photo.getContentType();
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		String strDate = dateFormat.format(date);
		String TryPhoto = "images";
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {

			System.out.println(photo.getOriginalFilename());
			if (photo.getOriginalFilename().trim().equals("")||photo.getOriginalFilename()==null) {
				System.out.println((String) httpSession.getAttribute("linkImages")+"end");
				seed.setImages((String) httpSession.getAttribute("linkImages"));
				System.out.println(seed.getImages());
				httpSession.setAttribute("linkImages", null);
			}else
			{
				saveFile(TryPhoto, strDate + photo.getOriginalFilename(), photo);

				seed.setImages(TryPhoto + "/" + strDate + photo.getOriginalFilename());
			}
			

			session.update(seed);

			t.commit();
			model.addAttribute("message", "Sửa thành công");
		} catch (Exception e) {

			t.rollback();
			model.addAttribute("message", "Sửa thất bại");
		} finally {
			session.close();
		}
		return "admin/seededit";
	}

	@RequestMapping(value = "seedinsert", method = RequestMethod.GET)
	public String insertseed(ModelMap model) {
		Seed seed = new Seed();
		model.addAttribute("seed", seed);
		Session session3 = factory.getCurrentSession();
		String hql2 = "FROM TypeSeed";
		Query query2 = session3.createQuery(hql2);
		List<TypeSeed> list2 = query2.list();
		model.addAttribute("TypeSeeds", list2);
		return "admin/seedinsert";
	}

	@RequestMapping(value = "seedinsert", method = RequestMethod.POST)
	public String insertseed(ModelMap model, @Validated @ModelAttribute("seed") Seed seed,
			@RequestParam("image") MultipartFile photo, BindingResult errors) {
		boolean check = false;
		if (seed.getSeedName().equals("")) {

			errors.rejectValue("seedName", "seed", "Vui lòng nhập tên hạt");
			check = true;
		}
		if (seed.getPrice() == null) {
			errors.rejectValue("price", "seed", "Không được để trống giá");
			check = true;
		} else {
			if (seed.getPrice() < 0) {
				errors.rejectValue("price", "seed", "Giá phải lớn hơn 0");
				check = true;
			}
		}
		if (seed.getNumber() == null) {
			errors.rejectValue("number", "seed", "Không được để trống số lượng");
			check = true;
		} else {
			if (seed.getNumber() < 0) {
				errors.rejectValue("number", "seed", "Số lượng phải lớn hơn 0");
				check = true;
			}

		}
		
		if (check == true) {
			Session session3 = factory.getCurrentSession();
			String hql2 = "FROM TypeSeed";
			Query query2 = session3.createQuery(hql2);
			List<TypeSeed> list2 = query2.list();
			model.addAttribute("TypeSeeds", list2);
			return "admin/seedinsert";
		}
		String typeOfPhoto = photo.getContentType();
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
		String strDate = dateFormat.format(date);
		String TryPhoto = "images";
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {

			System.out.println(photo.getOriginalFilename());
			saveFile(TryPhoto, strDate + photo.getOriginalFilename(), photo);
			
			seed.setImages(TryPhoto + "/" + strDate + photo.getOriginalFilename());
			if (photo.getOriginalFilename() == null) {
				errors.rejectValue("images", "seed", "Phải chọn ảnh");
				return "admin/seedinsert";
			}
			session.save(seed);

			t.commit();
			model.addAttribute("message", "Thêm thành công");
		} catch (Exception e) {

			t.rollback();
			model.addAttribute("message", "Thêm thất bại");
		} finally {
			session.close();
		}
		return "admin/seedinsert";
	}

	@RequestMapping("theorder")
	public String theorder(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TheOrder WHERE StatusOrder='1'";
		Query query = session.createQuery(hql);
		List<TheOrder> list = query.list();
		model.addAttribute("TheOrderNew", list);
		return "admin/theorder";
	}

	@RequestMapping("theorderCommit")
	public String theOrderCommit(ModelMap model) {
		Session session = factory.getCurrentSession();
		String hql = "FROM TheOrder WHERE StatusOrder='2' OR StatusOrder='0'";
		Query query = session.createQuery(hql);
		List<TheOrder> list = query.list();
		model.addAttribute("TheOrderNew", list);
		return "admin/theOrderCommit";
	}

	@RequestMapping(value = "receive/{idOrder}")
	public String receive(ModelMap model, @PathVariable("idOrder") String idOrder) {
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		try {
			String hql = "FROM TheOrder WHERE idOrder=" + idOrder;
			Query query = session.createQuery(hql);
			List<TheOrder> list = query.list();
			TheOrder temp = list.get(0);
			temp.setStatusOrder("2");
			session.update(temp);
			t.commit();
			model.addAttribute("message", "Tiếp nhận thành công");
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			model.addAttribute("message", "Tiếp nhận không thành công");
		} finally {
			session.close();
		}
		Session session2 = factory.getCurrentSession();
		String hql2 = "FROM TheOrder WHERE StatusOrder='1'";
		Query query2 = session2.createQuery(hql2);
		List<TheOrder> list2 = query2.list();
		model.addAttribute("TheOrderNew", list2);
		return "admin/theorder";
	}

	@RequestMapping(value = "showDetail/{idOrder}")
	public String showDetail(ModelMap model, HttpSession httpSession, @PathVariable("idOrder") String idOrder) {
		Session session = factory.getCurrentSession();
		String hql = "FROM OrderDetail WHERE idOrder = " + idOrder;
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
		return "admin/ShowDetail";
	}
	@RequestMapping(value="addAdmin/{idUser}")
	public String addAdmin(ModelMap model,@PathVariable("idUser")String idUser)
	{
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		String hql="FROM User where idUser="+idUser;
		Query query=session.createQuery(hql);
		if(query.list().size()==0)
			return "404";
		User admin =(User) query.list().get(0);
		admin.setAdmin(true);
		try {
			session.update(admin);
			t.commit();
			model.addAttribute("message", "Thêm quyền admin thành công");
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			model.addAttribute("message", "Thêm quyền admin thất bại");
		}
		hql="FROM User";
		query=session.createQuery(hql);
		List<User> admin2 = query.list();
		model.addAttribute("admin",admin2);
		session.close();
		return "admin/index";
	}
	@RequestMapping(value="cancelAdmin/{idUser}")
	public String cancelAdmin(ModelMap model,@PathVariable("idUser")String idUser)
	{
		Session session=factory.openSession();
		Transaction t = session.beginTransaction();
		String hql="FROM User where idUser="+idUser;
		Query query=session.createQuery(hql);
		if(query.list().size()==0)
			return "404";
		User admin =(User) query.list().get(0);
		admin.setAdmin(false);
		try {
			session.update(admin);
			t.commit();
			model.addAttribute("message", "Huỷ quyền admin thành công");
		} catch (Exception e) {
			// TODO: handle exception
			t.rollback();
			model.addAttribute("message", "Huỷ quyền admin thất bại");
		}
		hql="FROM User";
		query=session.createQuery(hql);
		List<User> admin2 = query.list();
		model.addAttribute("admin",admin2);
		session.close();
		return "admin/index";
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
			return "admin/ShowDetail";
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
		return "admin/ShowDetail";
	}
	@RequestMapping(value="deleteseed/{idSeed}", method = RequestMethod.GET)
	public String deleteseed(ModelMap model,@PathVariable("idSeed")String idSeed)
	{
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		String hql ="FROM OrderDetail WHERE idSeed="+idSeed;
		Query query=session.createQuery(hql);
		
		if(query.list().size()!=0)
		{
			t.commit();
			session.close();
			model.addAttribute("message", "Hạt giống đã có trong dữ liệu đơn hàng. Không được xoá!");
			return "admin/seeddelete";
		}else
		{
			hql ="FROM Seed WHERE idSeed="+idSeed;
			query=session.createQuery(hql);
			try {
				session.delete(query.list().get(0));
				t.commit();
				model.addAttribute("message", "Xoá thành công");
			} catch (Exception e) {
				// TODO: handle exception
				t.rollback();
				model.addAttribute("message", "Xoá thất bại");
				
			}finally {
				session.close();
			}
		}
		return "admin/seeddelete";
	}
	@RequestMapping(value="deletetypeseed/{idTypeSeed}", method = RequestMethod.GET)
	public String deletetypeseed(ModelMap model,@PathVariable("idTypeSeed")String idTypeSeed)
	{
		Session session = factory.openSession();
		Transaction t = session.beginTransaction();
		String hql ="FROM Seed WHERE idTypeSeed="+idTypeSeed;
		Query query=session.createQuery(hql);
		
		if(query.list().size()!=0)
		{
			t.commit();
			session.close();
			model.addAttribute("message", "Loại hạt đã có hạt giống. Không được xoá!");
			return "admin/seeddelete";
		}else
		{
			hql ="FROM TypeSeed WHERE idTypeSeed="+idTypeSeed;
			query=session.createQuery(hql);
			try {
				session.delete(query.list().get(0));
				t.commit();
				model.addAttribute("message", "Xoá thành công");
			} catch (Exception e) {
				// TODO: handle exception
				t.rollback();
				model.addAttribute("message", "Xoá thất bại");
				
			}finally {
				session.close();
			}
		}
		return "admin/seeddelete";
	}
}
