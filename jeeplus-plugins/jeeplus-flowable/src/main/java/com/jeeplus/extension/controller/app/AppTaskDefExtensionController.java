/**
 * Copyright © 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.extension.controller.app;

import com.jeeplus.extension.controller.TaskDefExtensionController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工作流扩展Controller
 *
 * @author liugf
 * @version 2021-09-26
 */
@Tag(name = "工作流扩展-移动端")
@RestController
@RequestMapping("/app/extension/taskDefExtension")
public class AppTaskDefExtensionController extends TaskDefExtensionController {


}
