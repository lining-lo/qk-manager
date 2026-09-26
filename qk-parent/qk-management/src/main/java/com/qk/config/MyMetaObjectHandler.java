package com.qk.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     设置新增时填充的逻辑
     * @param metaObject 代表要新增的对象，例如新增活动，metaObject表示Activity对象。如果时新增课程，metaObject表示Course对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("开始插入填充...");
        //方案一：使用官方提供的填充代码
        //this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        //this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        //方案二：使用自定义填充代码
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime",LocalDateTime.now());
    }

    /**
     设置修改时填充的逻辑
     * @param metaObject 代表要修改的对象，例如修改活动，metaObject表示Activity对象。如果时修改课程，metaObject表示Course对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("开始更新填充...");
        //注意：MetaObjectHandler 提供的默认方法策略是：如果属性有值则不覆盖
        //this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        //方案二：使用自定义填充代码
        metaObject.setValue("updateTime",LocalDateTime.now());
    }
}
