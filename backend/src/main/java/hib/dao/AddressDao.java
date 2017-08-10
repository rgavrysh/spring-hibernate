package hib.dao;

import hib.model.Address;

public interface AddressDao {
    Address create(final Address address);

    Address find(final Address address);
}
