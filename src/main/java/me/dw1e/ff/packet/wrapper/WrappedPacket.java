package me.dw1e.ff.packet.wrapper;

public abstract class WrappedPacket {

    private final long timestamp = System.currentTimeMillis();
    private boolean cancel = false;

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
}
