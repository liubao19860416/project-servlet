package com.saick.base;

/*
 * Copyright 2008-2010 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

import static com.sun.btrace.BTraceUtils.println;

import com.sun.btrace.BTraceUtils.Strings;
import com.sun.btrace.BTraceUtils.Sys;
import com.sun.btrace.BTraceUtils.Time;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.OnTimer;

/**
 * Demonstrates multiple timer probes with different periods to fire.
 */
@BTrace
public class Timers {

    // when starting print the target VM version and start time
    static {
        println(Strings.strcat("vm version ", Sys.VM.vmVersion()));
        println(Strings.strcat("vm starttime ",
                Strings.str(Sys.VM.vmStartTime())));
    }

    @OnTimer(1000)
    public static void f() {
        println(Strings.strcat("1000 msec: ", Strings.str(Sys.VM.vmUptime())));
    }

    @OnTimer(3000)
    public static void f1() {
        println(Strings.strcat("3000 msec: ", Strings.str(Time.millis())));
    }

}
