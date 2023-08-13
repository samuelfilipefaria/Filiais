package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;

import models.Filial;

@Stateless
public class FilialService extends GenericService<Filial> {

	public FilialService() {
		super(Filial.class);
	}
	
	public List<Filial> listByName(String name){
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Filial> cQuery = cBuilder.createQuery(Filial.class);
		final Root<Filial> rootFilial = cQuery.from(Filial.class);
		
		final Expression<String> expName = rootFilial.get("name");
		
		cQuery.select(rootFilial);
		cQuery.where(cBuilder.like(expName, "%"+name+"%"));
		cQuery.orderBy(cBuilder.asc(expName));
		
		
		List<Filial> result = getEntityManager().createQuery(cQuery).getResultList();
		
		return result;
	}
}
