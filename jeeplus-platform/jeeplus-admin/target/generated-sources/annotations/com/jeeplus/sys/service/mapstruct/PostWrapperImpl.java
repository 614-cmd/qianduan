package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.domain.Post;
import com.jeeplus.sys.service.dto.PostDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-07T19:36:24+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.11 (Oracle Corporation)"
)
@Component
public class PostWrapperImpl implements PostWrapper {

    @Override
    public Post toEntity(PostDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Post post = new Post();

        post.setCreateById( dtoCreateById( dto ) );
        post.setUpdateById( dtoUpdateById( dto ) );
        post.setId( dto.getId() );
        post.setCreateTime( dto.getCreateTime() );
        post.setUpdateTime( dto.getUpdateTime() );
        post.setDelFlag( dto.getDelFlag() );
        post.setName( dto.getName() );
        post.setCode( dto.getCode() );
        post.setType( dto.getType() );
        post.setStatus( dto.getStatus() );
        post.setSort( dto.getSort() );
        post.setRemarks( dto.getRemarks() );

        return post;
    }

    @Override
    public PostDTO toDTO(Post entity) {
        if ( entity == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setCreateBy( postToUserDTO( entity ) );
        postDTO.setUpdateBy( postToUserDTO1( entity ) );
        postDTO.setId( entity.getId() );
        postDTO.setCreateTime( entity.getCreateTime() );
        postDTO.setUpdateTime( entity.getUpdateTime() );
        postDTO.setDelFlag( entity.getDelFlag() );
        postDTO.setName( entity.getName() );
        postDTO.setCode( entity.getCode() );
        postDTO.setType( entity.getType() );
        postDTO.setStatus( entity.getStatus() );
        postDTO.setRemarks( entity.getRemarks() );
        postDTO.setSort( entity.getSort() );

        return postDTO;
    }

    @Override
    public List<Post> toEntity(List<PostDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Post> list = new ArrayList<Post>( dtoList.size() );
        for ( PostDTO postDTO : dtoList ) {
            list.add( toEntity( postDTO ) );
        }

        return list;
    }

    @Override
    public List<PostDTO> toDTO(List<Post> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PostDTO> list = new ArrayList<PostDTO>( entityList.size() );
        for ( Post post : entityList ) {
            list.add( toDTO( post ) );
        }

        return list;
    }

    @Override
    public Page<Post> toEntity(Page<PostDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<Post> page1 = new Page<Post>();

        page1.setPages( page.getPages() );
        page1.setRecords( toEntity( page.getRecords() ) );
        page1.setTotal( page.getTotal() );
        page1.setSize( page.getSize() );
        page1.setCurrent( page.getCurrent() );
        List<OrderItem> list1 = page.getOrders();
        if ( list1 != null ) {
            page1.setOrders( new ArrayList<OrderItem>( list1 ) );
        }

        return page1;
    }

    @Override
    public Page<PostDTO> toDTO(Page<Post> page) {
        if ( page == null ) {
            return null;
        }

        Page<PostDTO> page1 = new Page<PostDTO>();

        page1.setPages( page.getPages() );
        page1.setRecords( toDTO( page.getRecords() ) );
        page1.setTotal( page.getTotal() );
        page1.setSize( page.getSize() );
        page1.setCurrent( page.getCurrent() );
        List<OrderItem> list1 = page.getOrders();
        if ( list1 != null ) {
            page1.setOrders( new ArrayList<OrderItem>( list1 ) );
        }

        return page1;
    }

    private String dtoCreateById(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }
        UserDTO createBy = postDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }
        UserDTO updateBy = postDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected UserDTO postToUserDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( post.getCreateById() );

        return userDTO;
    }

    protected UserDTO postToUserDTO1(Post post) {
        if ( post == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( post.getUpdateById() );

        return userDTO;
    }
}
