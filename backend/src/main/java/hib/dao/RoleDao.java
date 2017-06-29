package hib.dao;

import hib.model.Role;

public interface RoleDao {

    Role findOneById(final int id);
}
