package io.github.ryosuke37.sylva.config;

import io.github.ryosuke37.sylva.mapper.PostMapper;
import io.github.ryosuke37.sylva.mapper.PostMapperImpl;
import io.github.ryosuke37.sylva.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperDiConfig {
    @Bean
    public PostMapper postMapper(UserMapper userMapper) {
        PostMapper postMapper = new PostMapperImpl(userMapper);
        postMapper.setUserMapper(userMapper);
        return postMapper;
    }
}
