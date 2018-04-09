package site.enoch.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializableUtil {

	/**
	 * ���л�����
	 * @param obj Ҫ���л��Ķ���
	 * @return �ֽ�����
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
	 * �����л�����
	 * @param bytes �ֽ�����
	 * @return ����
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
