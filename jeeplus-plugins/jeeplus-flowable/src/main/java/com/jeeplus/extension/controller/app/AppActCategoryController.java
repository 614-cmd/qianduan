/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller.app;

import com.jeeplus.extension.controller.ActCategoryController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程分类Controller
 *
 * @author 刘高峰
 * @version 2021-10-09
 */
@Tag(name = "流程分类-移动端")
@RestController
@RequestMapping("/app/extension/actCategory")
public class AppActCategoryController extends ActCategoryController {


}
