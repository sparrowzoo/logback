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
package ch.qos.logback.core.encoder;

import java.nio.charset.Charset;

import ch.qos.logback.core.Appender;
import ch.qos.logback.core.CoreConstants;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.OutputStreamAppender;

/**
 * 封装layout 的编码器
 * @param <E>
 */
public class LayoutWrappingEncoder<E> extends EncoderBase<E> {

    protected Layout<E> layout;

    /**
     * The charset to use when converting a String into bytes.
     * 用于将字符串转化为byte节流的字符集
     * <p>
     * By default this property has the value {@code null} which corresponds to
     * the system's default charset.
     * 默认为null与系统默认的字符串相同
     */
    private Charset charset;

    Appender<?> parent;
    Boolean immediateFlush = null;

    public Layout<E> getLayout() {
        return layout;
    }

    public void setLayout(Layout<E> layout) {
        this.layout = layout;
    }

    public Charset getCharset() {
        return charset;
    }

    /**
     * Set the charset to use when converting the string returned by the layout
     * into bytes.
     * <p>
     * By default this property has the value {@code null} which corresponds to
     * the system's default charset.
     *
     * @param charset
     */
    public void setCharset(Charset charset) {
        this.charset = charset;
    }

    /**
     * Sets the immediateFlush option. The default value for immediateFlush is 'true'. If set to true,
     * the doEncode() method will immediately flush the underlying OutputStream. Although immediate flushing
     * is safer, it also significantly degrades logging throughput.
     * 设置是否立即刷新选项，这个默认的值是true,如果这个值是true ,那么doencode 方法被调用时会立即刷新潜在的输出流，虽然立即刷新是这全的，
     * 但也是明显地降低日志的吐吞量
     * @since 1.0.3
     */
    public void setImmediateFlush(boolean immediateFlush) {
        addWarn("As of version 1.2.0 \"immediateFlush\" property should be set within the enclosing Appender.");
        addWarn("Please move \"immediateFlush\" property into the enclosing appender.");
        this.immediateFlush = immediateFlush;
    }

    /**
     * 获取layout 的头部字节流
     * @return
     */
    @Override
    public byte[] headerBytes() {
        if (layout == null)
            return null;

        StringBuilder sb = new StringBuilder();
        appendIfNotNull(sb, layout.getFileHeader());
        appendIfNotNull(sb, layout.getPresentationHeader());
        if (sb.length() > 0) {
            // If at least one of file header or presentation header were not
            // null, then append a line separator.
            // This should be useful in most cases and should not hurt.
            sb.append(CoreConstants.LINE_SEPARATOR);
        }
        return convertToBytes(sb.toString());
    }

    /**
     * 获取layout 的尾部字节流
     * @return
     */
    @Override
    public byte[] footerBytes() {
        if (layout == null)
            return null;

        StringBuilder sb = new StringBuilder();
        appendIfNotNull(sb, layout.getPresentationFooter());
        appendIfNotNull(sb, layout.getFileFooter());
        return convertToBytes(sb.toString());
    }

    /**
     * 将字符串转成字节流
     * @param s
     * @return
     */
    private byte[] convertToBytes(String s) {
        if (charset == null) {
            return s.getBytes();
        } else {
            return s.getBytes(charset);
        }
    }

    /**
     * 字事件对象转成layout 字符后再转成字节流
     * @param event
     * @return
     */
    public byte[] encode(E event) {
        String txt = layout.doLayout(event);
        return convertToBytes(txt);
    }

    public boolean isStarted() {
        return false;
    }

    public void start() {
        if (immediateFlush != null) {
            if (parent instanceof OutputStreamAppender) {
                addWarn("Setting the \"immediateFlush\" property of the enclosing appender to " + immediateFlush);
                @SuppressWarnings("unchecked")
                OutputStreamAppender<E> parentOutputStreamAppender = (OutputStreamAppender<E>) parent;
                parentOutputStreamAppender.setImmediateFlush(immediateFlush);
            } else {
                addError("Could not set the \"immediateFlush\" property of the enclosing appender.");
            }
        }
        started = true;
    }

    public void stop() {
        started = false;
    }

    private void appendIfNotNull(StringBuilder sb, String s) {
        if (s != null) {
            sb.append(s);
        }
    }

    /**
     * This method allows RollingPolicy implementations to be aware of their
     * containing appender.
     * 
     * @param parent
     */
    public void setParent(Appender<?> parent) {
        this.parent = parent;
    }
}
