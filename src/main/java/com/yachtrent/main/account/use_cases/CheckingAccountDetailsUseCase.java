package com.yachtrent.main.account.use_cases;

import org.springframework.stereotype.Service;

@Service
public class CheckingAccountDetailsUseCase {

    public boolean passwordMask(String password){
        boolean onlyEn = true;
        boolean number = false;
        if(password.length() >= 8)
        {
            for (int i = 0 ; i < password.length(); i++){
                if (password.charAt(i) >= 'А' && password.charAt(i) <= 'Я') onlyEn = false;
                if (Character.isDigit(password.charAt(i))) number = true;
            }
            if(onlyEn && number)
                return true;
        }
        return false;
    }

    public boolean passwordCompare(String password, String passwordConfirm){
        return password == passwordConfirm ? false : true;
    }

    public boolean emailMask(String email){
        return true;
    }
}
