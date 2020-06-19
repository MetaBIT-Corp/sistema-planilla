package com.metabit.planilla.service;

import java.util.List;

import com.metabit.planilla.entity.Usuario;

public interface UsuarioService {
	public abstract List<Usuario> getAdminUsers();
}
