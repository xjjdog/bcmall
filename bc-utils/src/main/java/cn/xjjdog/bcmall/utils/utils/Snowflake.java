package cn.xjjdog.bcmall.utils.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.management.ManagementFactory;


/**
 * Copyright (c) 2021. All Rights Reserved.
 *
 * @author xjjdog
 */
@Slf4j
public class Snowflake {


    private long workerId;
    private long datacenterId;
    private long sequence = 0L;

    private static final int BYTE_MASK = 0xFF;

    private static final long epoch = 1288834974657L;

    private static final long workerIdBits = 5L;
    private static final long dataCenterIdBits = 5L;
    private static final long maxWorkerId = ~(-1L << workerIdBits);
    private static final long maxDataCenterId = ~(-1L << dataCenterIdBits);
    private static final long sequenceBits = 12L;

    private static final long workerIdShift = sequenceBits;
    private static final long dataCenterIdShift = sequenceBits + workerIdBits;
    private static final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits;
    private static final long sequenceMask = ~(-1L << sequenceBits);

    private long lastTimestamp = -1L;

    public Snowflake(long workerId, long dataCenterId) {
        // sanity check for workerId
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.datacenterId = dataCenterId;
        log.info(String.format("IDGenerator : DataCenterId %d, WorkerId %d", this.datacenterId, this.workerId));
    }

    public static int getPID() {
        String pid;
        String name = ManagementFactory.getRuntimeMXBean().getName();
        pid = name.substring(0, name.indexOf("@"));
        return Integer.valueOf(pid);
    }

    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            log.warn(String.format("clock is moving backwards.  Rejecting requests until %d.", lastTimestamp));
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - epoch) << timestampLeftShift) | (datacenterId << dataCenterIdShift) | (workerId << workerIdShift) | sequence;
    }

    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    private static final long LOCAL_NODE_ID;

    private static final long LOCAL_WORK_ID;

    static {
        byte[] bytes = NetUtil.getLocalAddress().getAddress();
        int tmp = (bytes[0] & BYTE_MASK) << 24 | (bytes[1] & BYTE_MASK) << 16 | (bytes[2] & BYTE_MASK) << 8 | bytes[3] & BYTE_MASK;
        LOCAL_NODE_ID = Math.abs(tmp % maxDataCenterId);
        LOCAL_WORK_ID = getPID() % maxWorkerId;
    }

    private static final Snowflake ID_WORKER = new Snowflake(LOCAL_WORK_ID, LOCAL_NODE_ID);


    public static long createId() {
        return ID_WORKER.nextId();
    }

}

