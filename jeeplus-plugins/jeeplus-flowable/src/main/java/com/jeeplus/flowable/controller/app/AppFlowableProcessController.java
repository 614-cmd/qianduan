/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.flowable.controller.app;

import com.jeeplus.flowable.controller.FlowableProcessController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程定义相关Controller
 *
 * @author jeeplus
 * @version 2021-09-03
 */
@Tag(name = "流程定义")
@RestController
@RequestMapping("/app/flowable/process")
public class AppFlowableProcessController extends FlowableProcessController {


}
