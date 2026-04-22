/**
 * Copyright &copy; 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller.app;

import com.jeeplus.sys.controller.OfficeController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构Controller
 *
 * @author jeeplus
 * @version 2021-5-15
 */
@Tag(name = "机构管理-移动端")
@RestController
@RequestMapping("/app/sys/office")
public class AppOfficeController extends OfficeController {


}
