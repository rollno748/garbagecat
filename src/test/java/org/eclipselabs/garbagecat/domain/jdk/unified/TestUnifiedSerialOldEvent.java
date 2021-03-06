/**********************************************************************************************************************
 * garbagecat                                                                                                         *
 *                                                                                                                    *
 * Copyright (c) 2008-2020 Red Hat, Inc.                                                                              *
 *                                                                                                                    * 
 * All rights reserved. This program and the accompanying materials are made available under the terms of the Eclipse *
 * Public License v1.0 which accompanies this distribution, and is available at                                       *
 * http://www.eclipse.org/legal/epl-v10.html.                                                                         *
 *                                                                                                                    *
 * Contributors:                                                                                                      *
 *    Red Hat, Inc. - initial API and implementation                                                                  *
 *********************************************************************************************************************/
package org.eclipselabs.garbagecat.domain.jdk.unified;

import java.util.ArrayList;
import java.util.List;

import org.eclipselabs.garbagecat.util.jdk.JdkRegEx;
import org.eclipselabs.garbagecat.util.jdk.JdkUtil;
import org.eclipselabs.garbagecat.util.jdk.JdkUtil.LogEventType;
import org.eclipselabs.garbagecat.util.jdk.unified.UnifiedUtil;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestUnifiedSerialOldEvent extends TestCase {

    public void testPreprocessed() {
        String logLine = "[0.075s][info][gc,start     ] GC(2) Pause Full (Allocation Failure) DefNew: "
                + "1152K->0K(1152K) Tenured: 458K->929K(960K) Metaspace: 697K->697K(1056768K) 1M->0M(2M) 3.061ms "
                + "User=0.00s Sys=0.00s Real=0.00s";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.UNIFIED_SERIAL_OLD.toString() + ".",
                UnifiedSerialOldEvent.match(logLine));
        UnifiedSerialOldEvent event = new UnifiedSerialOldEvent(logLine);
        Assert.assertEquals("Event name incorrect.", JdkUtil.LogEventType.UNIFIED_SERIAL_OLD.toString(),
                event.getName());
        Assert.assertEquals("Time stamp not parsed correctly.", 75, event.getTimestamp());
        Assert.assertTrue("Trigger not parsed correctly.",
                event.getTrigger().matches(JdkRegEx.TRIGGER_ALLOCATION_FAILURE));
        Assert.assertEquals("Young begin size not parsed correctly.", 1152, event.getYoungOccupancyInit());
        Assert.assertEquals("Young end size not parsed correctly.", 0, event.getYoungOccupancyEnd());
        Assert.assertEquals("Young available size not parsed correctly.", 1152, event.getYoungSpace());
        Assert.assertEquals("Old begin size not parsed correctly.", 458, event.getOldOccupancyInit());
        Assert.assertEquals("Old end size not parsed correctly.", 929, event.getOldOccupancyEnd());
        Assert.assertEquals("Old allocation size not parsed correctly.", 960, event.getOldSpace());
        Assert.assertEquals("Perm gen begin size not parsed correctly.", 697, event.getPermOccupancyInit());
        Assert.assertEquals("Perm gen end size not parsed correctly.", 697, event.getPermOccupancyEnd());
        Assert.assertEquals("Perm gen allocation size not parsed correctly.", 1056768, event.getPermSpace());
        Assert.assertEquals("Duration not parsed correctly.", 3061, event.getDuration());
    }

    public void testIdentityEventType() {
        String logLine = "[0.075s][info][gc,start     ] GC(2) Pause Full (Allocation Failure) DefNew: "
                + "1152K->0K(1152K) Tenured: 458K->929K(960K) Metaspace: 697K->697K(1056768K) 1M->0M(2M) 3.061ms "
                + "User=0.00s Sys=0.00s Real=0.00s";
        Assert.assertEquals(JdkUtil.LogEventType.UNIFIED_SERIAL_OLD + "not identified.",
                JdkUtil.LogEventType.UNIFIED_SERIAL_OLD, JdkUtil.identifyEventType(logLine));
    }

    public void testParseLogLine() {
        String logLine = "[0.075s][info][gc,start     ] GC(2) Pause Full (Allocation Failure) DefNew: "
                + "1152K->0K(1152K) Tenured: 458K->929K(960K) Metaspace: 697K->697K(1056768K) 1M->0M(2M) 3.061ms "
                + "User=0.00s Sys=0.00s Real=0.00s";
        Assert.assertTrue(JdkUtil.LogEventType.UNIFIED_SERIAL_OLD.toString() + " not parsed.",
                JdkUtil.parseLogLine(logLine) instanceof UnifiedSerialOldEvent);
    }

    public void testIsBlocking() {
        String logLine = "[0.075s][info][gc,start     ] GC(2) Pause Full (Allocation Failure) DefNew: "
                + "1152K->0K(1152K) Tenured: 458K->929K(960K) Metaspace: 697K->697K(1056768K) 1M->0M(2M) 3.061ms "
                + "User=0.00s Sys=0.00s Real=0.00s";
        Assert.assertTrue(JdkUtil.LogEventType.UNIFIED_SERIAL_OLD.toString() + " not indentified as blocking.",
                JdkUtil.isBlocking(JdkUtil.identifyEventType(logLine)));
    }

    public void testHydration() {
        LogEventType eventType = JdkUtil.LogEventType.UNIFIED_SERIAL_OLD;
        String logLine = "[0.075s][info][gc,start     ] GC(2) Pause Full (Allocation Failure) DefNew: "
                + "1152K->0K(1152K) Tenured: 458K->929K(960K) Metaspace: 697K->697K(1056768K) 1M->0M(2M) 3.061ms "
                + "User=0.00s Sys=0.00s Real=0.00s";
        long timestamp = 27091;
        int duration = 0;
        Assert.assertTrue(JdkUtil.LogEventType.UNIFIED_SERIAL_OLD.toString() + " not parsed.",
                JdkUtil.hydrateBlockingEvent(eventType, logLine, timestamp, duration) instanceof UnifiedSerialOldEvent);
    }

    public void testReportable() {
        Assert.assertTrue(JdkUtil.LogEventType.UNIFIED_SERIAL_OLD.toString() + " not indentified as reportable.",
                JdkUtil.isReportable(JdkUtil.LogEventType.UNIFIED_SERIAL_OLD));
    }

    public void testUnified() {
        List<LogEventType> eventTypes = new ArrayList<LogEventType>();
        eventTypes.add(LogEventType.UNIFIED_SERIAL_OLD);
        Assert.assertTrue(JdkUtil.LogEventType.UNIFIED_SERIAL_OLD.toString() + " not indentified as unified.",
                UnifiedUtil.isUnifiedLogging(eventTypes));
    }

    public void testLogLineWhitespaceAtEnd() {
        String logLine = "[0.075s][info][gc,start     ] GC(2) Pause Full (Allocation Failure) DefNew: "
                + "1152K->0K(1152K) Tenured: 458K->929K(960K) Metaspace: 697K->697K(1056768K) 1M->0M(2M) 3.061ms "
                + "User=0.00s Sys=0.00s Real=0.00s    ";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.UNIFIED_SERIAL_OLD.toString() + ".",
                UnifiedSerialOldEvent.match(logLine));
    }

    public void testLogLine7SpacesAfterStart() {
        String logLine = "[0.119s][info][gc,start       ] GC(5) Pause Full (Allocation Failure) DefNew: "
                + "1142K->110K(1152K) Tenured: 1044K->1934K(1936K) Metaspace: 1295K->1295K(1056768K) 2M->1M(4M) "
                + "3.178ms User=0.00s Sys=0.00s Real=0.00s";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.UNIFIED_SERIAL_OLD.toString() + ".",
                UnifiedSerialOldEvent.match(logLine));
    }

    public void testPreprocessedTriggerErgonomics() {
        String logLine = "[0.091s][info][gc,start     ] GC(3) Pause Full (Ergonomics) PSYoungGen: 502K->436K(1536K) "
                + "PSOldGen: 460K->511K(2048K) Metaspace: 701K->701K(1056768K) 0M->0M(3M) 1.849ms "
                + "User=0.01s Sys=0.00s Real=0.00s";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.UNIFIED_SERIAL_OLD.toString() + ".",
                UnifiedSerialOldEvent.match(logLine));
        UnifiedSerialOldEvent event = new UnifiedSerialOldEvent(logLine);
        Assert.assertEquals("Event name incorrect.", JdkUtil.LogEventType.UNIFIED_SERIAL_OLD.toString(),
                event.getName());
        Assert.assertEquals("Time stamp not parsed correctly.", 91, event.getTimestamp());
        Assert.assertTrue("Trigger not parsed correctly.", event.getTrigger().matches(JdkRegEx.TRIGGER_ERGONOMICS));
        Assert.assertEquals("Young begin size not parsed correctly.", 502, event.getYoungOccupancyInit());
        Assert.assertEquals("Young end size not parsed correctly.", 436, event.getYoungOccupancyEnd());
        Assert.assertEquals("Young available size not parsed correctly.", 1536, event.getYoungSpace());
        Assert.assertEquals("Old begin size not parsed correctly.", 460, event.getOldOccupancyInit());
        Assert.assertEquals("Old end size not parsed correctly.", 511, event.getOldOccupancyEnd());
        Assert.assertEquals("Old allocation size not parsed correctly.", 2048, event.getOldSpace());
        Assert.assertEquals("Perm gen begin size not parsed correctly.", 701, event.getPermOccupancyInit());
        Assert.assertEquals("Perm gen end size not parsed correctly.", 701, event.getPermOccupancyEnd());
        Assert.assertEquals("Perm gen allocation size not parsed correctly.", 1056768, event.getPermSpace());
        Assert.assertEquals("Duration not parsed correctly.", 1849, event.getDuration());
    }

}
