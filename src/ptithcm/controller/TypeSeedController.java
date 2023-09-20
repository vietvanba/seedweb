package ptithcm.controller;

import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import ptithcm.entity.Seed;
import ptithcm.entity.TypeSeed;

@Transactional
@Controller
@RequestMapping("/TypeSeed/")
public class TypeSeedController {
	@Autowired
	SessionFactory factory;
	@RequestMapping(value="{idTypeSeed}")
	public String loaiHat(ModelMap model,@PathVariable("idTypeSeed")String idTypeSeed)
	{
		Session session= factory.getCurrentSession();
		String hql = "FROM TypeSeed WHERE idTypeSeed = "+idTypeSeed;
		Query query = session.createQuery(hql);
		if(query.list().size()==0)
			return "404";
		model.addAttribute("loai",((TypeSeed)query.list().get(0)).getTypeSeedName());
		hql = "FROM TypeSeed";
		query = session.createQuery(hql);
		List<TypeSeed> list = query.list();
		model.addAttribute("TypeSeeds",list);
		hql = "FROM Seed WHERE idTypeSeed = "+idTypeSeed;
		query = session.createQuery(hql);
		List<Seed> list2 = query.list();
		model.addAttribute("Seed", list2);
		model.addAttribute(idTypeSeed, idTypeSeed);
		return "TypeSeed/loaiHat";
	}
	
}
