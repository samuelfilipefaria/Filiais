package services;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import models.Employee;

@Stateless
public class EmployeeService extends GenericService<Employee> {
	
	public EmployeeService() {
		super(Employee.class);
	}
	
	public List<Employee> filialEmployers(Long filialId) {
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Employee> cQuery = cBuilder.createQuery(Employee.class);
		final Root<Employee> rootEmployee = cQuery.from(Employee.class);
		
		final Expression<Long> expFilialId = rootEmployee.get("filial").get("id");
		
		cQuery.select(rootEmployee);
		cQuery.where(cBuilder.equal(expFilialId, filialId));
		
		List<Employee> result = getEntityManager().createQuery(cQuery).getResultList();
		
		return result;
	}
	
	public Integer filialEmployersQuantity(Long filialId) {
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Employee> cQuery = cBuilder.createQuery(Employee.class);
		final Root<Employee> rootEmployee = cQuery.from(Employee.class);
		
		final Expression<Long> expFilialId = rootEmployee.get("filial").get("id");
		
		cQuery.select(rootEmployee);
		cQuery.where(cBuilder.equal(expFilialId, filialId));
		
		Integer result = getEntityManager().createQuery(cQuery).getResultList().size();
		
		return result;
	}
	
	public List<Employee> listByName(String name){
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Employee> cQuery = cBuilder.createQuery(Employee.class);
		final Root<Employee> rootEmployee = cQuery.from(Employee.class);
		
		final Expression<String> expName = rootEmployee.get("name");
		
		cQuery.select(rootEmployee);
		cQuery.where(cBuilder.like(expName, "%"+name+"%"));
		cQuery.orderBy(cBuilder.asc(expName));
		
		
		List<Employee> result = getEntityManager().createQuery(cQuery).getResultList();
		
		return result;
	}
	
	public List<Employee> findBySalaryRange(Double start, Double end) {
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Employee> cQuery = cBuilder.createQuery(Employee.class);
		final Root<Employee> rootEmployee = cQuery.from(Employee.class);
		
		final Expression<Double> expSalary = rootEmployee.get("salary");
		
		cQuery.select(rootEmployee);
		cQuery.where(cBuilder.between(expSalary, start, end));
		cQuery.orderBy(cBuilder.desc(expSalary));
		
		
		List<Employee> result = getEntityManager().createQuery(cQuery).getResultList();
		
		return result;
	}
	
	public List<Employee> findBySalaryRangeAndFilial(Long filialId, Double start, Double end) {
		final CriteriaBuilder cBuilder = getEntityManager().getCriteriaBuilder();
		final CriteriaQuery<Employee> cQuery = cBuilder.createQuery(Employee.class);
		final Root<Employee> rootEmployee = cQuery.from(Employee.class);
		
		final Expression<Double> expSalary = rootEmployee.get("salary");
		final Expression<Long> expFilialId = rootEmployee.get("filial").get("id");
		
		cQuery.select(rootEmployee);
		
		Predicate conditions = cBuilder.and(
				cBuilder.between(expSalary, start, end), 
				cBuilder.equal(expFilialId, filialId)
		);
		
		cQuery.where(conditions);
		cQuery.orderBy(cBuilder.desc(expSalary));
		
		
		List<Employee> result = getEntityManager().createQuery(cQuery).getResultList();
		
		return result;
	}
}
