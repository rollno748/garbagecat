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
package org.eclipselabs.garbagecat.domain.jdk;

import org.eclipselabs.garbagecat.domain.UnknownEvent;
import org.eclipselabs.garbagecat.util.jdk.JdkUtil;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author <a href="mailto:mmillson@redhat.com">Mike Millson</a>
 * 
 */
public class TestFlsStaticsEvent extends TestCase {

    public void testNotBlocking() {
        String logLine = "Max   Chunk Size: 536870912";
        Assert.assertFalse(JdkUtil.LogEventType.FLS_STATISTICS.toString() + " incorrectly indentified as blocking.",
                JdkUtil.isBlocking(JdkUtil.identifyEventType(logLine)));
    }

    public void testNotReportable() {
        String logLine = "Max   Chunk Size: 536870912";
        Assert.assertFalse(JdkUtil.LogEventType.FLS_STATISTICS.toString() + " incorrectly indentified as reportable.",
                JdkUtil.isReportable(JdkUtil.identifyEventType(logLine)));
    }

    public void testJdkUtilParseLogLineDoesNotReturnUnknownEvent() {
        String logLine = "Max   Chunk Size: 536870912";
        Assert.assertFalse("JdkUtil.parseLogLine() returns " + JdkUtil.LogEventType.UNKNOWN.toString() + " event.",
                JdkUtil.parseLogLine(logLine) instanceof UnknownEvent);
    }

    public void testJdkUtilParseLogLineReturnsFlsStatisticsEvent() {
        String logLine = "Max   Chunk Size: 536870912";
        Assert.assertTrue(
                "JdkUtil.parseLogLine() does not return " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + " event.",
                JdkUtil.parseLogLine(logLine) instanceof FlsStatisticsEvent);
    }

    public void testLineStatistics() {
        String logLine = "Statistics for BinaryTreeDictionary:";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLineDivider() {
        String logLine = "------------------------------------";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineTotalFreeSpace() {
        String logLine = "Total Free Space: 536870912";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineTotalFreeSpaceNegative() {
        String logLine = "Total Free Space: -136285693";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineMaxChunkSize() {
        String logLine = "Max   Chunk Size: 536870912";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineMaxChunkSizeNegative() {
        String logLine = "Max   Chunk Size: -136285693";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineNumberOfBlocks() {
        String logLine = "Number of Blocks: 1";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineNumberOfBlocks4Digits() {
        String logLine = "Number of Blocks: 3752";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineNumberOfBlocks5Digits() {
        String logLine = "Number of Blocks: 68082";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineNumberOfBlocks6Digits() {
        String logLine = "Number of Blocks: 218492";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineNumberOfBlocks7Digits() {
        String logLine = "Number of Blocks: 6455862";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineNumberOfBlocks8Digits() {
        String logLine = "Number of Blocks: 64558627";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineAvBlockSize() {
        String logLine = "Av.  Block  Size: 536870912";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineAvBlockSizeNegative() {
        String logLine = "Av.  Block  Size: -328196225";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineTreeHeight() {
        String logLine = "Tree      Height: 1";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineTreeHeight2Digits() {
        String logLine = "Tree      Height: 20";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineTreeHeight3Digits() {
        String logLine = "Tree      Height: 130";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineBeforeGC() {
        String logLine = "Before GC:";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineAfterGC() {
        String logLine = "After GC:";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineLargeBlock() {
        String logLine = "CMS: Large block 0x00002b79ea830000";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineLargeBlockWithProximity() {
        String logLine = "CMS: Large Block: 0x00002b79ea830000; Proximity: 0x0000000000000000 -> 0x00002b79ea82fac8";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineStatisticsForIndexedFreeLists() {
        String logLine = "Statistics for IndexedFreeLists:";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineDivider() {
        String logLine = "--------------------------------";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineFrag() {
        String logLine = " free=1161964910 frag=0.8232";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineSweepSize() {
        String logLine = "size[256] : demand: 0, old_rate: 0.000000, current_rate: 0.000000, new_rate: 0.000000, "
                + "old_desired: 0, new_desired: 0";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineSweepDemand() {
        String logLine = "demand: 1, old_rate: 0.000000, current_rate: 0.000282, new_rate: 0.000282, old_desired: 0, "
                + "new_desired: 2";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineSweepSizeDemand8DigitsRate3Digits() {
        String logLine = "size[3] : demand: 11798284, old_rate: 717.797668, current_rate: 743.942383, new_rate: "
                + "742.511902, old_desired: 15402672, new_desired: 13570211";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }

    public void testLogLineSweepSizeDemand9DigitsRate4Digits() {
        String logLine = "size[4] : demand: 81603741, old_rate: 5028.634277, current_rate: 5145.535156, new_rate: "
                + "5146.810547, old_desired: 107905624, new_desired: 94063552";
        Assert.assertTrue("Log line not recognized as " + JdkUtil.LogEventType.FLS_STATISTICS.toString() + ".",
                FlsStatisticsEvent.match(logLine));
    }
}
