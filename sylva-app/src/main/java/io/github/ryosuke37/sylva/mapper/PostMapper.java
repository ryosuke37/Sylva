package io.github.ryosuke37.sylva.mapper;

import io.github.ryosuke37.sylva.controller.dto.PostDto;
import io.github.ryosuke37.sylva.repository.entity.PostEntity;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Setter
@Mapper(
        uses = UserMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public abstract class PostMapper {

    protected UserMapper userMapper;

    PostMapper(){}

    /*
    Issue: 抽象クラスに定義したuserMapperとは別に、実装クラス側にもuserMapperフィールドが自動生成されてしまい、
    実装クラス側では抽象クラスで持っているuserMapperフィールドを使用してくれない。
    おそらく、MapStructを使用するうえで上記のようなフィールドの二重定義を回避する方法はない。
    抽象クラス内でuserMapperのメソッドを使用するためには、抽象クラス側が持つuserMapperフィールドにUserMapperの注入をする必要がある。
    注入にはコンストラクタインジェクションを使用したいが、実装クラスに自動生成されるコンストラクタでは引数ありの抽象クラスコンストラクタを呼んでくれない。
    そのためセッターインジェクションを使用して、MapperDiConfigクラスから抽象クラスと実装クラスの双方に対してDIを行っている。
    */
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Named("withoutPost")
    @Mapping(target = "rootPost", ignore = true)
    @Mapping(target = "parentPost", ignore = true)
    @Mapping(target = "quotedPost", ignore = true)
    public abstract PostDto withoutPostMappingToDto(PostEntity entity);

    @Named("superficial")
    public PostDto shallowMappingToDto(PostEntity entity) {
        if (entity == null) {
            return null;
        }
        return new PostDto(
                entity.getId(),
                entity.getContent(),
                userMapper.toDto(entity.getUser()),
                withoutPostMappingToDto(entity.getRootPost()),
                withoutPostMappingToDto(entity.getParentPost()),
                withoutPostMappingToDto(entity.getQuotedPost()),
                entity.getCreatedDate(),
                entity.getUpdatedDate()
        );
    }

    @Named("deep")
    @Mapping(target = "rootPost", qualifiedByName = "deep")
    @Mapping(target = "parentPost", qualifiedByName = "deep")
    @Mapping(target = "quotedPost", qualifiedByName = "deep")
    public abstract PostDto deepMappingToDto(PostEntity entity);

    public List<PostDto> shallowMappingToDtos(List<PostEntity> entities) {
        ArrayList<PostDto> postDtos = new ArrayList<>();
        for (PostEntity postEntity : entities) {
            postDtos.add(shallowMappingToDto(postEntity));
        }
        return postDtos;
    }
}
