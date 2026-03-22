package io.github.ryosuke37.sylva.loader;

import io.github.ryosuke37.sylva.controller.form.SignupForm;
import io.github.ryosuke37.sylva.data_creator.DummyUserData;
import io.github.ryosuke37.sylva.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DummyDataLoader implements ApplicationRunner {

    private final AuthService authService;
    private final DummyUserData dummyUserData;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("start DummyDataLoader.run");

        List<String> argsList = args.getNonOptionArgs();
        for(String arg :argsList) {
            if (arg.equals("create_dummy_users")) {
                createDummyUsers();
            }
        }

        System.out.println("finish DummyDataLoader.run");
    }

    private void createDummyUsers(){
        List<SignupForm> signupForms = dummyUserData.getDummySignupForms();
        for (SignupForm signupForm : signupForms) {
            authService.signUp(signupForm);
        }
    }
}
