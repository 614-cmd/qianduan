/**
 * Copyright &copy; 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller.app;

import com.jeeplus.sys.controller.LoginController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录Controller
 *
 * @author jeeplus
 * @version 2021-5-31
 */
@RestController
@Tag(name = "登录管理-移动端")
@RequestMapping("/app")
public class AppLoginController extends LoginController {


}
