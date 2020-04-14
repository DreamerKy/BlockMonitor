package com.ng8.mobile.blockmonitor;

/**
 * @author tuzhao
 */
public final class JvmStackBean {

    private StackTraceElement[] stack;

    public StackTraceElement[] getStack() {
        return stack;
    }

    public void setStack(StackTraceElement[] stack) {
        this.stack = stack;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (null != stack && stack.length > 0) {
            for (int index = 0; index < stack.length; index++) {
                if (index > 0) {
                    builder.append("\n");
                }
                builder.append(stack[index]);
            }
        } else {
            builder.append("{}");
        }
        return builder.toString();
    }
}
