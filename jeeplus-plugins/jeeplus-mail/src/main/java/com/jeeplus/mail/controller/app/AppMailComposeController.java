/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.mail.controller.app;

import com.jeeplus.mail.controller.MailComposeController;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 发件箱Controller
 *
 * @author jeeplus
 * @version 2021-06-12
 */
@Tag(name = "发件箱")
@RestController
@RequestMapping("/app/mail/compose")
public class AppMailComposeController extends MailComposeController {


}
