package controllers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
	private Address address = new Address();
	private String searchTerm;
	private String noResultsMessage = "";

	public Boolean isStringInputValid(String input) {
		if(input.trim().isEmpty()) return false;

		return true;
	}

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
		this.address = filial.getAddress();
	}

	public void save() {
		
		if(
		  isStringInputValid(filial.getName()) &&
		  isStringInputValid(address.getStreet()) &&
		  isStringInputValid(address.getNeighborhood()) &&
		  isStringInputValid(address.getCity())
		) {
	        if (filial.getId() != null) {
	          addressService.merge(address);
	          filial.setAddress(address);
	          filialService.merge(filial);
	          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Editado com sucesso!", ""));
	        } else {
	          addressService.create(address);
	          filial.setAddress(addressService.getAll().get(addressService.getAll().size() - 1));
	          filialService.create(filial);
	          FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Criado com sucesso!", ""));
	        }
	
	        filial = new Filial();
	        address = new Address();
	        loadFilials();
	      } else {
	    	  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Atenção! Você possui campos com valores inválidos.", ""));
	      }
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

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getNoResultsMessage() {
		return noResultsMessage;
	}

	public void setNoResultsMessage(String noResultsMessage) {
		this.noResultsMessage = noResultsMessage;
	}
}
