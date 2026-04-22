package com.jeeplus.monitor.controller;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.common.redis.RedisUtils;
import com.jeeplus.monitor.vo.RedisVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis监控
 */
@Tag(name = "Redis监控")
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("treeData")
    public ResponseEntity data(String pattern) {

        List <RedisVO> list = Lists.newArrayList ( );
        Set <String> keys = redisUtils.keys ( pattern );
        HashSet exist = new HashSet ( );
        keys.forEach ( k -> {
            List <String> list1 = Lists.newArrayList ( k.split ( ":" ) );
            for (int i = 0; i < list1.size ( ); i++) {
                String pre = "";
                for (int j = 0; j < i; j++) {
                    pre = pre + list1.get ( j ) + ":";
                }
                RedisVO redisVO = new RedisVO ( );
                if ( i == list1.size ( ) - 1 ) {
                    redisVO.setId ( k );
                    redisVO.setName ( k );
                    redisVO.setType ( "key" );
                } else {
                    redisVO.setId ( pre + list1.get ( i ) + ":" );
                    redisVO.setName ( list1.get ( i ) );
                    redisVO.setType ( "folder" );
                }
                if ( i == 0 ) {
                    redisVO.setParentId ( "0" );
                } else {
                    redisVO.setParentId ( pre );
                }
                if ( !exist.contains ( pre + list1.get ( i ) + ":" ) ) {
                    list.add ( redisVO );
                    exist.add ( pre + list1.get ( i ) + ":" );
                }

            }

        } );
        if ( pattern.equals ( "*" ) ) {
            RedisVO root = new RedisVO ( );
            root.setId ( "0" );
            root.setName ( "db" );
            root.setType ( "db" );
            return ResponseEntity.ok ( treeData ( root, "0", list ) );
        } else {
            RedisVO root = new RedisVO ( );
            root.setId ( pattern.substring ( 0, pattern.length ( ) - 1 ) );
            root.setType ( "folder" );
            treeData ( root, root.getId ( ), list );
            return ResponseEntity.ok ( root.getChildren ( ) );
        }

    }

    /**
     * 查看
     */
    @GetMapping("queryByKey")
    public ResponseEntity queryByKey(String key) {
        return ResponseEntity.ok ( redisUtils.get ( key ) );
    }

    /**
     * 清空
     */
    @DemoMode
    @DeleteMapping("flushdb")
    public ResponseEntity flushdb() {
        redisUtils.delPattern ( "*" );
        return ResponseEntity.ok ( "清空成功！" );
    }

    /**
     * 删除key
     */
    @DemoMode
    @DeleteMapping("delKey")
    public ResponseEntity delKey(String key) {
        redisUtils.delete ( key );
        return ResponseEntity.ok ( "删除成功！" );
    }

    /**
     * 更新缓存值
     */
    @DemoMode
    @PutMapping("setValue")
    public ResponseEntity setValue(String key, String value) {
        redisUtils.set ( key, value );
        return ResponseEntity.ok ( "更新键值成功！" );
    }


    /**
     * 删除pattern
     */
    @DemoMode
    @DeleteMapping("delPattern")
    public ResponseEntity delPattern(String pattern) {
        redisUtils.delPattern ( pattern );
        return ResponseEntity.ok ( "删除成功！" );
    }


    /**
     * 获取JSON树形数据。
     *
     * @return
     */
    public List <RedisVO> treeData(RedisVO root, String rootId, List <RedisVO> allList) {
        try {

            List <RedisVO> redisVOS = this.formatListRedisVOoRedisVOree ( root, rootId, allList );
            root.getChildren ( ).addAll ( redisVOS );
            List list = Lists.newArrayList ( );
            list.add ( root );
            return list;
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * 以root为根节点, 将allList从线性列表转为树形列
     **/
    public List <RedisVO> formatListRedisVOoRedisVOree(RedisVO root, String rootsId, List <RedisVO> allList) {
        String rootId = root.getId ( );

        // 最终的树形态
        List <RedisVO> trees = Lists.newArrayList ( );

        // 把需要构造树的所有列表, 根据以父id作为key, 整理为列表
        Map <String, List <RedisVO>> treeMap = Maps.newHashMap ( );
        for (RedisVO entity : allList) {
            List <RedisVO> entities = treeMap.get ( entity.getParentId ( ) );
            if ( entities == null ) {
                entities = Lists.newLinkedList ( );
            }

            // 剔除排除项, 构造treeMap, 转递归为线性操作
            entities.add ( entity );
            treeMap.put ( entity.getParentId ( ), entities );
        }

        // 没有给定的子树, 返回空树
        if ( treeMap.get ( rootId ) == null || treeMap.get ( rootId ).isEmpty ( ) ) {
            return trees;
        }

        // 开始递归格式化
        List <RedisVO> children = treeMap.get ( rootId );
        for (RedisVO parent : children) {
            formatFillChildren ( parent, treeMap );
            trees.add ( parent );
        }
        if ( StrUtil.equals ( rootId, rootsId ) ) {
            return children;
        } else {
            root.setChildren ( trees );
            return Lists.newArrayList ( root );
        }
    }

    /**
     * 从treeMap中取出子节点填入parent, 并递归此操作
     **/
    private void formatFillChildren(RedisVO parent, Map <String, List <RedisVO>> treeMap) {
        List <RedisVO> children = treeMap.get ( parent.getId ( ) );
        parent.setChildren ( children );
        if ( children != null && !children.isEmpty ( ) ) {
            for (RedisVO child : children) {
                formatFillChildren ( child, treeMap );
            }
        }
    }

}
