package com.jeeplus.mail.service.mapstruct;

import com.jeeplus.core.mapstruct.EntityWrapper;
import com.jeeplus.mail.domain.Mail;
import com.jeeplus.mail.service.dto.MailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {})
public interface MailWrapper extends EntityWrapper <MailDTO, Mail> {

    MailWrapper INSTANCE = Mappers.getMapper ( MailWrapper.class );

    @Mappings({
            @Mapping(source = "createBy.id", target = "createById"),
            @Mapping(source = "updateBy.id", target = "updateById")})
    Mail toEntity(MailDTO dto);


    @Mappings({
            @Mapping(source = "createById", target = "createBy.id"),
            @Mapping(source = "updateById", target = "updateBy.id")})
    MailDTO toDTO(Mail entity);
}
