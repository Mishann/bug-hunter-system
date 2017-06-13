package com.app.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.model.User;
import com.app.service.UserService;

@Component
public class UserEditValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Autowired
	UserService userService;

	@Override
	public void validate(Object obj, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "", "Ім'я не може бути пустим");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userSurname", "", "Прізвище не може бути пустим");

		User u = (User) obj;

			}
}
