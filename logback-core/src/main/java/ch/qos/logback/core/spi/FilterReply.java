/**
 * Logback: the reliable, generic, fast and flexible logging framework.
 * Copyright (C) 1999-2015, QOS.ch. All rights reserved.
 *
 * This program and the accompanying materials are dual-licensed under
 * either the terms of the Eclipse Public License v1.0 as published by
 * the Eclipse Foundation
 *
 *   or (per the licensee's choosing)
 *
 * under the terms of the GNU Lesser General Public License version 2.1
 * as published by the Free Software Foundation.
 */
package ch.qos.logback.core.spi;

/**
 *
 * This enum represents the possible replies that a filtering component
 * in logback can return. It is used by implementations of both 
 * {@link ch.qos.logback.core.filter.Filter Filter} and
 * ch.qos.logback.classic.turbo.TurboFilter abstract classes.
 *
 * 这个枚举代表着过滤组件可能的答复在logback中，它被以下两个实现类使用
 * 
 * Based on the order that the FilterReply values are declared,
 * FilterReply.ACCEPT.compareTo(FilterReply.DENY) will return 
 * a positive value.
 * 其于顺序，FilterReply的值被定义为如下，
 * accept < deny
 *
 * @author S&eacute;bastien Pennec
 */
public enum FilterReply {
    DENY, NEUTRAL, ACCEPT;
}
