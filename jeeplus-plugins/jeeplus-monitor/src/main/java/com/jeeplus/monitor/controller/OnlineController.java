package com.jeeplus.monitor.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.common.redis.RedisUtils;
import com.jeeplus.sys.constant.CacheNames;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * 在线用户Controller
 *
 * @author liugf
 * @version 2022-07-14
 */
@Tag(name = "在线用户")
@RestController
@RequestMapping("/online/users")
public class OnlineController {

    @GetMapping("/list")
    public ResponseEntity list(UserDTO userDTO, Page <UserDTO> page) {
        Stream <UserDTO> list = UserUtils.getOnlineUsers ( ).stream ( );
        if ( StrUtil.isNotEmpty ( userDTO.getLoginName ( ) ) ) {
            list = list.filter ( user -> user.getLoginName ( ).contains ( userDTO.getLoginName ( ) ) );

        }
        if ( StrUtil.isNotEmpty ( userDTO.getName ( ) ) ) {
            list = list.filter ( user -> user.getName ( ).contains ( userDTO.getName ( ) ) );
        }
        List <UserDTO> users = list.collect ( Collectors.toList ( ) );
        page.setTotal ( users.size ( ) );
        int start = (int) ((page.getCurrent ( ) - 1) * page.getSize ( ));
        int end = (int) ((page.getCurrent ( )) * page.getSize ( ));

        if ( start > users.size ( ) ) {
            start = users.size ( );
        }
        if ( end > users.size ( ) ) {
            end = users.size ( );
        }

        if ( start == end && start >= page.getSize ( ) ) {
            start = start - (int) page.getSize ( );
        }
        page.setPages ( (users.size ( ) + page.getSize ( ) - 1) / page.getSize ( ) );
        page.setRecords ( users.subList ( start, end ) );

        return ResponseEntity.ok ( page );

    }

    @DemoMode
    @DeleteMapping("kickout")
    public ResponseEntity kickout(String token) {
        RedisUtils.getInstance ( ).delete ( CacheNames.USER_CACHE_TOKEN, token );
        RedisUtils.getInstance ( ).delete ( CacheNames.USER_CACHE_ONLINE_USERS, token );
        return ResponseEntity.ok ( "下线用户成功!" );
    }


}
