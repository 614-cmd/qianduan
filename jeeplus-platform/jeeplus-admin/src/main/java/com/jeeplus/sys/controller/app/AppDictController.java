/**
 * Copyright &copy; 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller.app;

import com.jeeplus.sys.controller.DictController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典Controller
 *
 * @author jeeplus
 * @version 2021-05-16
 */
@Tag(name = "字典管理-移动端")
@RestController
@RequestMapping("/app/sys/dict")
public class AppDictController extends DictController {


}
