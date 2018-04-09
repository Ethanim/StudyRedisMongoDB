package site.enoch.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableUtil {

	/**
	 * 序列化方法
	 * @param obj 要序列化的对象
	 * @return 字节数组
	 * @throws IOException
	 */
	public static byte[] toSerializable(Object obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		byte[] bytes = baos.toByteArray();
		oos.close();
		baos.close();
		return bytes;
	}
	
	/**
	 * 反序列化方法
	 * @param bytes 字节数组
	 * @return 对象
	 * @throws Exception
	 */
	public static Object toObject(byte[] bytes) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		Object obj = ois.readObject();
		ois.close();
		bais.close();
		return obj;
	}
}
