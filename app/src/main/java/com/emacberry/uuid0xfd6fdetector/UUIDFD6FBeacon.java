package com.emacberry.uuid0xfd6fdetector;

import java.util.Date;
import java.util.HashSet;
import java.util.TreeMap;

public class UUIDFD6FBeacon {

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public int mTxPowerLevel;
    public int mTxPower;
    public String addr;
    public long mLastTs;
    public TreeMap<Long, Integer> sigHistory = new TreeMap<>();
    public HashSet<String> data = new HashSet<>();

    public UUIDFD6FBeacon(String addr, long ts) {
        this.addr = addr;
        this.mLastTs = ts;
    }

    public void addRssi(long ts, int rssi, long tsNow) {
        mLastTs = tsNow;
        sigHistory.put(ts, rssi);
    }

    public void addData(byte[] serviceData) {
        data.add(bytesToHex(serviceData));
    }

    private String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    protected String bytesToHexX(byte[] hashInBytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
            sb.append(", ");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append(addr);
        b.append(" [");
        b.append(data.size());
        b.append("] ");
        b.append(sigHistory.size());
        b.append(' ');
        b.append(new Date(mLastTs));
        return b.toString();
    }
}
