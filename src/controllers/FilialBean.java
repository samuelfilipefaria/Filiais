package controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import models.Address;
import models.Employee;
import models.Filial;
import services.AddressService;
import services.EmployeeService;
import services.FilialService;

@ViewScoped
@ManagedBean
public class FilialBean {

	@EJB
	private FilialService filialService;
	@EJB
	private AddressService addressService;
	@EJB
	private EmployeeService employeeService;
	
	private Filial filial = new Filial();
	private List<Filial> filials = new ArrayList<Filial>();
	private Address filialAddress = new Address();
	private String searchTerm;
	private String noResultsMessage = "";
	
	public Integer employersQuantity(Filial filial) {
		return employeeService.filialEmployersQuantity(filial.getId());
	}
	
	public void searchFilial() {
		filials = filialService.listByName(searchTerm);
		
		searchTerm = "";
		
		if(filials.size() == 0) {
			noResultsMessage = "Nenhuma filial encontrada";
		}
	}
	
	public void deleteFilial(Filial filial) {
		for(Employee employee : employeeService.filialEmployers(filial.getId())) {
			employeeService.remove(employee);
		}
		
		filialService.remove(filial);
		loadFilials();
	}
	
	public void loadFilialData(Filial filial) {
		this.filial = filial;
		this.filialAddress = filial.getAddress();
	}
	
	public void save() {		
		if (filial.getId() != null) {
			addressService.merge(filialAddress);
			filial.setAddress(filialAddress);
			filialService.merge(filial);
		} else {
			addressService.create(filialAddress);
			filial.setAddress(addressService.getAll().get(addressService.getAll().size() - 1));
			filialService.create(filial);
		}
						
		filial = new Filial();
		filialAddress = new Address();
		loadFilials();
	}
	
	@PostConstruct
	public void initialize() {
		loadFilials();
	}
	
	public void loadFilials() {
		filials = filialService.listByName("");
	}
	
	public FilialService getFilialService() {
		return filialService;
	}

	public void setFilialService(FilialService filialService) {
		this.filialService = filialService;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public List<Filial> getFilials() {
		return filials;
	}

	public void setFilials(List<Filial> filials) {
		this.filials = filials;
	}

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public Address getFilialAddress() {
		return filialAddress;
	}

	public void setFilialAddress(Address filialAddress) {
		this.filialAddress = filialAddress;
	}

	public String getNoResultsMessage() {
		return noResultsMessage;
	}

	public void setNoResultsMessage(String noResultsMessage) {
		this.noResultsMessage = noResultsMessage;
	}
}
