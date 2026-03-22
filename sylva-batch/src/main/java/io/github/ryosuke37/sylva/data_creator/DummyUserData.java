package io.github.ryosuke37.sylva.data_creator;

import io.github.ryosuke37.sylva.controller.form.SignupForm;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DummyUserData {

    private final String[][] DUMMY_USER_DATA = {
            /* handle, nickname, email, raw password */
            {"TestUserHandle00001", "NickName00001", "test.00001@gmail.com", "Pass.00001"},
            {"TestUserHandle00002", "NickName00002", "test.00002@gmail.com", "Pass.00002"},
            {"TestUserHandle00003", "NickName00003", "test.00003@gmail.com", "Pass.00003"},
            {"TestUserHandle00004", "NickName00004", "test.00004@gmail.com", "Pass.00004"},
            {"TestUserHandle00005", "NickName00005", "test.00005@gmail.com", "Pass.00005"},
            {"TestUserHandle00006", "NickName00006", "test.00006@gmail.com", "Pass.00006"},
            {"TestUserHandle00007", "NickName00007", "test.00007@gmail.com", "Pass.00007"},
            {"TestUserHandle00008", "NickName00008", "test.00008@gmail.com", "Pass.00008"},
            {"TestUserHandle00009", "NickName00009", "test.00009@gmail.com", "Pass.00009"},
            {"TestUserHandle00010", "NickName00010", "test.00010@gmail.com", "Pass.00010"},
    };

    public List<SignupForm> getDummySignupForms() {
        List<SignupForm> signupForms = new ArrayList<>();
        for (String[] userData : DUMMY_USER_DATA) {
            final int HANDLE = 0;
            final int NICKNAME = 1;
            final int EMAIL = 2;
            final int RAW_PASSWORD = 3;

            SignupForm signupForm = new SignupForm();

            signupForm.setHandle(userData[HANDLE]);
            signupForm.setNickname(userData[NICKNAME]);
            signupForm.setEmailAddress(userData[EMAIL]);
            signupForm.setRawPassword(userData[RAW_PASSWORD]);
            signupForms.add(signupForm);
        }
        return signupForms;
    }
}
