package services;

import javax.ejb.Stateless;

import models.Address;

@Stateless
public class AddressService extends GenericService<Address> {

	public AddressService() {
		super(Address.class);
	}
	
}
