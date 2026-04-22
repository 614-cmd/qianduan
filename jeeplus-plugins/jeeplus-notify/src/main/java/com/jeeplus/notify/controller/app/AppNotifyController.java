/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.notify.controller.app;

import com.jeeplus.notify.controller.NotifyController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通知通告Controller
 *
 * @author jeeplus
 * @version 2021-05-16
 */
@Tag(name = "通知通告")
@RestController
@RequestMapping("/app/notify")
public class AppNotifyController extends NotifyController {

}
