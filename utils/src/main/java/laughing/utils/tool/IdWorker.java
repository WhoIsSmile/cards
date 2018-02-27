package laughing.utils.tool;

public class IdWorker {

    /**
     * 纪元 ：时间基数
     */
    private final static long epoch = 1500283037000L;

    /**
     * 序列号占用的位数
     */
    private final static long BIT_SEQUENCE = 10;
    /**
     * 业务标示位数
     */
    private final static long BIT_BIZ = 4;
    /**
     * 机器标示占用位数
     */
    private final static long BIT_MACHINE = 4;

    /**
     * 每一部分向左的偏移位
     */
    private final static long LEFT_BIZ = BIT_SEQUENCE;
    private final static long LEFT_MACHINE = BIT_BIZ + LEFT_BIZ;
    private final static long LEFT_TIMESTMP = BIT_MACHINE + LEFT_MACHINE;


    /**
     * 每一部分的最大值
     */
    private final static long MAX_MACHINE = -1L ^ (-1L << BIT_MACHINE);
    private final static long MAX_BIZ = -1L ^ (-1L << BIT_BIZ);
    private final static long MAX_SEQUENCE = -1L ^ (-1L << BIT_SEQUENCE);

    /**
     * 业务号
     */
    private long bizId;
    /**
     * 机器标识
     */
    private long machineId;
    /**
     * 序列号
     */
    private long sequence = 0L;
    /**
     * 上一次时间戳
     */
    private long lastTimestamp = -1L;


    /**
     * 构造器
     *
     * @param bizId     初始化业务编号 0-15
     * @param machineId 初始化机器标示号 0-15  如果多台机器部署，请给定唯一的机器标识号
     */
    public IdWorker(long bizId, long machineId) {
        checkNumValid(bizId, MAX_BIZ);
        checkNumValid(machineId, MAX_MACHINE);
        this.bizId = bizId;
        this.machineId = machineId;
    }

    /**
     * 产生下一个ID
     *
     * @return
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        if (timestamp < lastTimestamp) {
            long offset = lastTimestamp - timestamp;
            if (offset <= 5) {
                sleepMs(offset << 1);
                timestamp = timeGen();
                if (timestamp < lastTimestamp) {
                    throw new RuntimeException(String.format("The system clock was backed about : %dms, and can not recover with retry, stop service! ", offset));
                }
            } else {
                throw new RuntimeException("Clock moved backwards.  Refusing to generate id");
            }
        }
        if (timestamp == lastTimestamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & MAX_SEQUENCE;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                timestamp = timeGen();
            }
        } else {
            //不同毫秒内，序列号置为0
            sequence = 0L;
        }

        lastTimestamp = timestamp;
        return (timestamp - epoch) << LEFT_TIMESTMP //时间戳部分
                | machineId << LEFT_MACHINE            //机器标识部分
                | bizId << LEFT_BIZ                    //业务标识部分
                | sequence;                            //序列号部分
    }

    /**
     * 从数字最后的位数开始，取其中几位
     *
     * @param number
     * @param lastStartBit
     * @param count
     * @return
     */
    public static long lastSubBit(long number, int lastStartBit, int count) {
        return (-1L ^ (-1L << count)) & (number >> lastStartBit);
    }

    /**
     * 获取账号生成时间（时间戳，毫秒）
     *
     * @param number
     * @return
     */
    public static long getDate(long number) {

        return epoch + (number >> LEFT_TIMESTMP);
    }

    /**
     * 检查数字的有效性
     *
     * @param value
     * @param maxNum
     * @return
     */
    private void checkNumValid(long value, long maxNum) {
        if (value > maxNum || value < 0) {
            throw new IllegalArgumentException("value can't be greater than maxNum or less than 0");
        }
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 线程沉睡
     *
     * @param ms 毫秒
     */
    private void sleepMs(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {

        }
    }

    public static void main(String[] args) {
        IdWorker gos = new IdWorker(15, 15);
        System.out.println();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 2 << 2; i++) {
            System.out.println(gos.nextId());
        }
        long end = System.currentTimeMillis();
        System.out.println(end - start);
        System.out.println(getDate(gos.nextId()));

    }
}
