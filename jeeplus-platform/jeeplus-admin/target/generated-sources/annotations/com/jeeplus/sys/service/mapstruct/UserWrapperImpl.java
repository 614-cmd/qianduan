package com.jeeplus.sys.service.mapstruct;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.service.dto.OfficeDTO;
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
public class UserWrapperImpl implements UserWrapper {

    @Override
    public List<User> toEntity(List<UserDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( dtoList.size() );
        for ( UserDTO userDTO : dtoList ) {
            list.add( toEntity( userDTO ) );
        }

        return list;
    }

    @Override
    public List<UserDTO> toDTO(List<User> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( entityList.size() );
        for ( User user : entityList ) {
            list.add( toDTO( user ) );
        }

        return list;
    }

    @Override
    public Page<User> toEntity(Page<UserDTO> page) {
        if ( page == null ) {
            return null;
        }

        Page<User> page1 = new Page<User>();

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
    public Page<UserDTO> toDTO(Page<User> page) {
        if ( page == null ) {
            return null;
        }

        Page<UserDTO> page1 = new Page<UserDTO>();

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

    @Override
    public User toEntity(UserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        user.setCompanyId( dtoCompanyDTOId( dto ) );
        user.setOfficeId( dtoOfficeDTOId( dto ) );
        user.setCreateById( dtoCreateById( dto ) );
        user.setUpdateById( dtoUpdateById( dto ) );
        user.setId( dto.getId() );
        user.setCreateTime( dto.getCreateTime() );
        user.setUpdateTime( dto.getUpdateTime() );
        user.setDelFlag( dto.getDelFlag() );
        user.setLoginName( dto.getLoginName() );
        user.setPassword( dto.getPassword() );
        user.setNo( dto.getNo() );
        user.setName( dto.getName() );
        user.setEmail( dto.getEmail() );
        user.setPhone( dto.getPhone() );
        user.setMobile( dto.getMobile() );
        user.setLoginIp( dto.getLoginIp() );
        user.setLoginDate( dto.getLoginDate() );
        user.setLoginFlag( dto.getLoginFlag() );
        user.setPhoto( dto.getPhoto() );
        user.setQrCode( dto.getQrCode() );
        user.setSign( dto.getSign() );
        user.setRemarks( dto.getRemarks() );

        return user;
    }

    @Override
    public UserDTO toDTO(User entity) {
        if ( entity == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setCompanyDTO( userToOfficeDTO( entity ) );
        userDTO.setOfficeDTO( userToOfficeDTO1( entity ) );
        userDTO.setCreateBy( userToUserDTO( entity ) );
        userDTO.setUpdateBy( userToUserDTO1( entity ) );
        userDTO.setId( entity.getId() );
        userDTO.setCreateTime( entity.getCreateTime() );
        userDTO.setUpdateTime( entity.getUpdateTime() );
        userDTO.setDelFlag( entity.getDelFlag() );
        userDTO.setLoginName( entity.getLoginName() );
        userDTO.setPassword( entity.getPassword() );
        userDTO.setName( entity.getName() );
        userDTO.setNo( entity.getNo() );
        userDTO.setEmail( entity.getEmail() );
        userDTO.setPhone( entity.getPhone() );
        userDTO.setMobile( entity.getMobile() );
        userDTO.setLoginIp( entity.getLoginIp() );
        userDTO.setLoginDate( entity.getLoginDate() );
        userDTO.setLoginFlag( entity.getLoginFlag() );
        userDTO.setPhoto( entity.getPhoto() );
        userDTO.setQrCode( entity.getQrCode() );
        userDTO.setSign( entity.getSign() );
        userDTO.setRemarks( entity.getRemarks() );

        return userDTO;
    }

    private String dtoCompanyDTOId(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }
        OfficeDTO companyDTO = userDTO.getCompanyDTO();
        if ( companyDTO == null ) {
            return null;
        }
        String id = companyDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoOfficeDTOId(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }
        OfficeDTO officeDTO = userDTO.getOfficeDTO();
        if ( officeDTO == null ) {
            return null;
        }
        String id = officeDTO.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoCreateById(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }
        UserDTO createBy = userDTO.getCreateBy();
        if ( createBy == null ) {
            return null;
        }
        String id = createBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String dtoUpdateById(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }
        UserDTO updateBy = userDTO.getUpdateBy();
        if ( updateBy == null ) {
            return null;
        }
        String id = updateBy.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected OfficeDTO userToOfficeDTO(User user) {
        if ( user == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setId( user.getCompanyId() );

        return officeDTO;
    }

    protected OfficeDTO userToOfficeDTO1(User user) {
        if ( user == null ) {
            return null;
        }

        OfficeDTO officeDTO = new OfficeDTO();

        officeDTO.setId( user.getOfficeId() );

        return officeDTO;
    }

    protected UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getCreateById() );

        return userDTO;
    }

    protected UserDTO userToUserDTO1(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setId( user.getUpdateById() );

        return userDTO;
    }
}
