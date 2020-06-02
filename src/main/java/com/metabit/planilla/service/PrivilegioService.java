package com.metabit.planilla.service;

import com.metabit.planilla.entity.Privilegio;
import java.util.List;

public interface PrivilegioService {
    public abstract List<Privilegio> getPrivilegios();
    public abstract Privilegio getPrivilegio(int idPrivilegio);
    public abstract Privilegio storePrivilegio(Privilegio privilegio);
    public abstract Privilegio updatePrivilegio(Privilegio privilegio);
    public abstract void deletePrivilegio(int idPrivilegio);
}
