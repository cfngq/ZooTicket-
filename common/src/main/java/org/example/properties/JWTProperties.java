package org.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("zoo.jwt")
public class JWTProperties {
    /**
     * 管理端生成jwt令牌相关配置
     */
    private String adminSecretKey;

    private long adminTtl;

    private String adminTokenName;
    /**
     * 员工生成jwt令牌相关配置
     */
    private String employeeSecretKey;

    private long employeeTtl;

    private String employeeTokenName;
    /**
     * 用户生成jwt令牌相关配置
     */
    private String userSecretKey;

    private long userTtl;

    private String userTokenName;
}
