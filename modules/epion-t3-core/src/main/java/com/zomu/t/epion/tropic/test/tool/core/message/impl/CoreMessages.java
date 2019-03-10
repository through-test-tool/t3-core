package com.zomu.t.epion.tropic.test.tool.core.message.impl;

import com.zomu.t.epion.tropic.test.tool.core.message.Messages;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * コアメッセージ.
 *
 * @author takashno
 */
@Getter
@AllArgsConstructor
public enum CoreMessages implements Messages {

    CORE_ERR_0001("com.zomu.t.epion.t3.core.err.0001"),
    CORE_ERR_0002("com.zomu.t.epion.t3.core.err.0002"),
    CORE_ERR_0003("com.zomu.t.epion.t3.core.err.0003"),
    CORE_ERR_0004("com.zomu.t.epion.t3.core.err.0004"),
    CORE_ERR_0005("com.zomu.t.epion.t3.core.err.0005"),
    CORE_ERR_0006("com.zomu.t.epion.t3.core.err.0006"),
    CORE_ERR_0007("com.zomu.t.epion.t3.core.err.0007"),
    CORE_ERR_0008("com.zomu.t.epion.t3.core.err.0008"),
    CORE_ERR_0009("com.zomu.t.epion.t3.core.err.0009"),
    CORE_ERR_0010("com.zomu.t.epion.t3.core.err.0010"),
    CORE_ERR_0011("com.zomu.t.epion.t3.core.err.0011"),
    CORE_ERR_0012("com.zomu.t.epion.t3.core.err.0012"),
    CORE_ERR_0013("com.zomu.t.epion.t3.core.err.0013"),
    CORE_ERR_1001("com.zomu.t.epion.t3.core.err.1001"),
    CORE_ERR_1002("com.zomu.t.epion.t3.core.err.1002"),
    CORE_ERR_1003("com.zomu.t.epion.t3.core.err.1003"),
    CORE_WRN_0001("com.zomu.t.epion.t3.core.wrn.0001"),
    CORE_WRN_0002("com.zomu.t.epion.t3.core.wrn.0002"),
    ;

    /**
     * メッセージコード.
     */
    private String messageCode;
}
