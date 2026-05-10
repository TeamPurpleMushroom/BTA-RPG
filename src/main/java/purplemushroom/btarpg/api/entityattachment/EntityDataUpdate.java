package purplemushroom.btarpg.api.entityattachment;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayDeque;

public final class EntityDataUpdate {
	EntityDataUpdate() {} // package-private; should only be called from BTAEntityDataHandler

	private final ArrayDeque<DataVar<?>> dataQueue = new ArrayDeque<>();

	public void writeByte(byte data) {
		dataQueue.add(new DataVar<>(data, (byte) 0));
	}

	public void writeShort(short data) {
		dataQueue.add(new DataVar<>(data, (byte) 1));
	}

	public void writeInt(int data) {
		dataQueue.add(new DataVar<>(data, (byte) 2));
	}

	public void writeLong(long data) {
		dataQueue.add(new DataVar<>(data, (byte) 3));
	}

	public void writeFloat(float data) {
		dataQueue.add(new DataVar<>(data, (byte) 4));
	}

	public void writeDouble(double data) {
		dataQueue.add(new DataVar<>(data, (byte) 5));
	}

	public void writeBool(boolean data) {
		dataQueue.add(new DataVar<>(data, (byte) 6));
	}

	public void writeChar(char data) {
		dataQueue.add(new DataVar<>(data, (byte) 7));
	}

	public void writeString(String data) {
		dataQueue.add(new DataVar<>(data, (byte) 8));
	}

	public byte readByte() {
		return (Byte)dataQueue.pop().data;
	}

	public short readShort() {
		return (Short)dataQueue.pop().data;
	}

	public int readInt() {
		return (Integer)dataQueue.pop().data;
	}

	public long readLong() {
		return (Long)dataQueue.pop().data;
	}

	public float readFloat() {
		return (Float)dataQueue.pop().data;
	}

	public double readDouble() {
		return (Double)dataQueue.pop().data;
	}

	public boolean readBool() {
		return (Boolean)dataQueue.pop().data;
	}

	public char readChar() {
		return (Character)dataQueue.pop().data;
	}

	public String readString() {
		return (String)dataQueue.pop().data;
	}

	void writeToStream(DataOutputStream stream) throws IOException {
		stream.writeInt(dataQueue.size());
		while (!dataQueue.isEmpty()) {
			DataVar<?> data = dataQueue.pop();
			stream.writeByte(data.type);
			switch (data.type) {
				case 0: {
					stream.writeByte((Byte)data.data);
					break;
				}
				case 1: {
					stream.writeShort((Short)data.data);
					break;
				}
				case 2: {
					stream.writeInt((Integer) data.data);
					break;
				}
				case 3: {
					stream.writeLong((Long)data.data);
					break;
				}
				case 4: {
					stream.writeFloat((Float)data.data);
					break;
				}
				case 5: {
					stream.writeDouble((Double)data.data);
					break;
				}
				case 6: {
					stream.writeBoolean((Boolean)data.data);
					break;
				}
				case 7: {
					stream.writeChar((Character)data.data);
					break;
				}
				case 8: {
					stream.writeUTF((String)data.data);
					break;
				}
			}
		}
	}

	void readFromStream(DataInputStream stream) throws IOException {
		int size = stream.readInt();
		for (int i = 0; i < size; i++) {
			byte type = stream.readByte();
			switch (type) {
				case 0: {
					dataQueue.add(new DataVar<>(stream.readByte(), (byte) 0));
					break;
				}
				case 1: {
					dataQueue.add(new DataVar<>(stream.readShort(), (byte) 1));
					break;
				}
				case 2: {
					dataQueue.add(new DataVar<>(stream.readInt(), (byte) 2));
					break;
				}
				case 3: {
					dataQueue.add(new DataVar<>(stream.readLong(), (byte) 3));
					break;
				}
				case 4: {
					dataQueue.add(new DataVar<>(stream.readFloat(), (byte) 4));
					break;
				}
				case 5: {
					dataQueue.add(new DataVar<>(stream.readDouble(), (byte) 5));
					break;
				}
				case 6: {
					dataQueue.add(new DataVar<>(stream.readBoolean(), (byte) 6));
					break;
				}
				case 7: {
					dataQueue.add(new DataVar<>(stream.readChar(), (byte) 7));
					break;
				}
				case 8: {
					dataQueue.add(new DataVar<>(stream.readUTF(), (byte) 8));
					break;
				}
			}
		}
	}

	private static class DataVar<T> {
		private final T data;
		private final byte type;

		private DataVar(T data, byte type) {
			this.data = data;
			this.type = type;
		}
	}
}
