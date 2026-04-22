/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.database.datamodel.controller;

import com.jeeplus.database.datamodel.domain.DataMeta;
import com.jeeplus.database.datamodel.service.DataMetaService;
import com.jeeplus.sys.constant.CommonConstants;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据资源Controller
 *
 * @author 刘高峰
 * @version 2021-08-07
 */
@Tag(name = "数据资源")
@RestController
@RequestMapping(value = "/database/datamodel/dataMeta")
public class DataMetaController {

    @Autowired
    private DataMetaService dataMetaService;

    @GetMapping("/queryNeedByDataSetId")
    public ResponseEntity queryNeedByDataSetId(String id) {
        return ResponseEntity.ok ( dataMetaService.lambdaQuery ( ).eq ( DataMeta::getIsNeed, CommonConstants.TRUE ).eq ( DataMeta::getDataSetId, id ).list ( ) );
    }


}
