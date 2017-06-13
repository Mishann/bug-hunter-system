package com.app.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.app.model.User;
import com.app.service.UserService;

@Component
public class UserRegisterValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.equals(clazz);
	}

	@Autowired
	UserService userService;

	@Override
	public void validate(Object obj, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "login", "", "Логін не може бути пустим");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "", "Пароль не може бути пустим");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", "", "Ім'я не може бути пустим");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userSurname", "", "Прізвище не може бути пустим");

		User u = (User) obj;

		if (u.getLogin().length() < 5)
			errors.rejectValue("login", "", "Логін повинен містити мінімум 5 символів");

		if (u.getPassword().length() < 5)
			errors.rejectValue("password", "", "Пароль повинен містити мінімум 5 символів");

		// якщо такий користувач вже є в системі(логін)
		if (userService.findByLogin(u.getLogin()) != null)
			errors.rejectValue("login", "", "Вже зареєстрований користувач із даним логіном");

		// якщо такий користувач вже є в системі(пароль)
		if (userService.findByEmail(u.getEmail()) != null)
			errors.rejectValue("email", "", "Вже зареєстрований користувач із даним емайлом");
	}
}
