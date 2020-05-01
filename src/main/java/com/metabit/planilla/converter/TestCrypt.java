package com.metabit.planilla.converter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.metabit.planilla.entity.Rol;
import com.metabit.planilla.entity.Usuario;
import com.metabit.planilla.repository.UserJpaRepository;

public class TestCrypt {

	public static void main(String[] args) {
		BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
		System.out.println(pe.encode("planilla"));
		
	}

}
